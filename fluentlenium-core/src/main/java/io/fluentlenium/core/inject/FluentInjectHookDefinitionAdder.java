package io.fluentlenium.core.inject;

import io.fluentlenium.core.hook.*;
import io.fluentlenium.utils.ReflectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import static io.fluentlenium.core.inject.InjectionAnnotationSupport.isAnnotationTypeHook;
import static io.fluentlenium.core.inject.InjectionAnnotationSupport.isAnnotationTypeHookOptions;

/**
 * Collects {@link HookDefinition}s based on {@link Hook} and {@link HookOptions} annotations applied on fields and classes.
 */
final class FluentInjectHookDefinitionAdder {

    /**
     * Collects hook definitions in the argument {@code hookDefinitions} based on annotations applied on a class or field.
     *
     * @param annotations     the annotations on a candidate field or class for injection
     * @param hookDefinitions container list for hook definitions to add them to
     */
    void addHookDefinitions(Annotation[] annotations, List<HookDefinition<?>> hookDefinitions) {
        Hook currentHookAnnotation = null;
        HookOptions currentHookOptionAnnotation = null;

        Annotation currentAnnotation = null;
        for (Annotation annotation : annotations) {
            applyNoHook(hookDefinitions, annotation);

            Hook hookAnnotation = getHookAnnotation(annotation);
            if (hookAnnotation != null) {
                currentAnnotation = annotation;
                if (currentHookAnnotation != null) {
                    hookDefinitions
                            .add(buildHookDefinition(currentHookAnnotation, currentHookOptionAnnotation, currentAnnotation));
                    currentHookOptionAnnotation = null;
                }
                currentHookAnnotation = hookAnnotation;
            }
            currentHookOptionAnnotation = validateHookOptionsAnnotation(currentHookOptionAnnotation, annotation);
        }

        if (currentHookAnnotation != null) {
            hookDefinitions.add(buildHookDefinition(currentHookAnnotation, currentHookOptionAnnotation, currentAnnotation));
        }
    }

    private HookOptions validateHookOptionsAnnotation(HookOptions currentHookOptionAnnotation, Annotation annotation) {
        HookOptions hookOptionsAnnotation = getHookOptionsAnnotation(annotation);
        if (hookOptionsAnnotation != null) {
            if (currentHookOptionAnnotation != null) {
                throw new FluentInjectException("Unexpected @HookOptions annotation. @Hook is missing.");
            }
            currentHookOptionAnnotation = hookOptionsAnnotation;
        }
        return currentHookOptionAnnotation;
    }

    private Hook getHookAnnotation(Annotation annotation) {
        if (annotation instanceof Hook) {
            return (Hook) annotation;
        } else if (isAnnotationTypeHook(annotation)) {
            return annotation.annotationType().getAnnotation(Hook.class);
        }
        return null;
    }

    private HookOptions getHookOptionsAnnotation(Annotation annotation) {
        if (annotation instanceof HookOptions) {
            return (HookOptions) annotation;
        } else if (isAnnotationTypeHookOptions(annotation)) {
            return annotation.annotationType().getAnnotation(HookOptions.class);
        }
        return null;
    }

    private void applyNoHook(List<HookDefinition<?>> hookDefinitions, Annotation annotation) {
        if (annotation instanceof NoHook) {
            Hook[] value = ((NoHook) annotation).value();
            if (ArrayUtils.isEmpty(value)) {
                hookDefinitions.clear();
            } else {
                HookControlImpl
                        .removeHooksFromDefinitions(hookDefinitions, Arrays.stream(value).map(Hook::value).toArray(Class[]::new));
            }
        }
    }

    private <T> HookDefinition<T> buildHookDefinition(Hook hookAnnotation, HookOptions hookOptionsAnnotation,
                                                      Annotation currentAnnotation) {
        Class<? extends T> hookOptionsClass =
                hookOptionsAnnotation == null ? null : (Class<? extends T>) hookOptionsAnnotation.value();
        T fluentHookOptions = null;
        if (hookOptionsClass != null) {
            try {
                fluentHookOptions = ReflectionUtils.newInstanceOptionalArgs(hookOptionsClass, currentAnnotation);
            } catch (NoSuchMethodException e) {
                throw new FluentInjectException("@HookOption class has no valid constructor", e);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new FluentInjectException("Can't create @HookOption class instance", e);
            }
        }
        Class<? extends FluentHook<T>> hookClass = (Class<? extends FluentHook<T>>) hookAnnotation.value();
        return fluentHookOptions == null ? new HookDefinition<>(hookClass) : new HookDefinition<>(hookClass, fluentHookOptions);
    }
}
