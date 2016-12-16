package org.fluentlenium.core.events;

import java.util.function.Function;
import org.fluentlenium.core.components.ComponentsAccessor;
import org.fluentlenium.core.events.annotations.AfterChangeValueOf;
import org.fluentlenium.core.events.annotations.AfterClickOn;
import org.fluentlenium.core.events.annotations.AfterFindBy;
import org.fluentlenium.core.events.annotations.BeforeChangeValueOf;
import org.fluentlenium.core.events.annotations.BeforeClickOn;
import org.fluentlenium.core.events.annotations.BeforeFindBy;
import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Listen to WebDriver events and delegates to methods with annotations registered on FluentLenium components.
 */
public class AnnotationsComponentListener implements WebDriverEventListener {
    private final ComponentsAccessor componentsAccessor;

    /**
     * Creates a new annotations component listener.
     *
     * @param componentsAccessor components accessor
     */
    public AnnotationsComponentListener(final ComponentsAccessor componentsAccessor) {
        this.componentsAccessor = componentsAccessor;
    }

    /**
     * Handle find by events.
     *
     * @param annotation event annotation
     * @param by         selenium locator
     * @param element    selenium element
     * @param driver     selenium driver
     */
    protected void findByHandler(final Class<? extends Annotation> annotation, final By by, final WebElement element,
            final WebDriver driver) {
        if (element == null) {
            return;
        }
        final Set<Object> components = this.componentsAccessor.getComponents(element);
        if (components == null) {
            return;
        }
        for (final Object component : components) {
            for (final Method method : ReflectionUtils.getDeclaredMethodsWithAnnotation(component, annotation)) {
                findByHandlerComponentMethod(component, method, annotation, by);
            }
        }
    }

    /**
     * Handle find by events for a given component method.
     *
     * @param component  component instance
     * @param method     component method
     * @param annotation event annotation
     * @param by         selenium locator
     */
    protected void findByHandlerComponentMethod(final Object component, final Method method,
            final Class<? extends Annotation> annotation, final By by) {
        final Class<?>[] parameterTypes = method.getParameterTypes();

        final Object[] args = ReflectionUtils.toArgs(new Function<Class<?>, Object>() {
            @Override
            public Object apply(final Class<?> input) {
                if (input.isAssignableFrom(By.class)) {
                    return by;
                }
                return null;
            }
        }, parameterTypes);

        try {
            ReflectionUtils.invoke(method, component, args);
        } catch (final IllegalAccessException e) {
            throw new EventAnnotationsException("An error has occured in @BeforeFindBy " + method, e);
        } catch (final InvocationTargetException e) {
            if (e.getTargetException() instanceof RuntimeException) {
                throw (RuntimeException) e.getTargetException();
            } else if (e.getTargetException() instanceof Error) {
                throw (Error) e.getTargetException();
            }
            throw new EventAnnotationsException("An error has occured in @BeforeFindBy " + method, e);
        }
    }

    @Override
    public void beforeFindBy(final By by, final WebElement element, final WebDriver driver) {
        findByHandler(BeforeFindBy.class, by, element, driver);
    }

    @Override
    public void afterFindBy(final By by, final WebElement element, final WebDriver driver) {
        findByHandler(AfterFindBy.class, by, element, driver);
    }

    /**
     * Handle default events.
     *
     * @param annotation   event annotation
     * @param element      selenium event
     * @param charSequence new value of the element (null if not revelant)
     */
    protected void defaultHandler(final Class<? extends Annotation> annotation, final WebElement element,
            final CharSequence[] charSequence) {
        if (element == null) {
            return;
        }
        final Set<Object> components = this.componentsAccessor.getComponents(element);
        if (components == null) {
            return;
        }
        for (final Object component : components) {
            for (final Method method : ReflectionUtils.getDeclaredMethodsWithAnnotation(component, annotation)) {
                defaultHandlerComponentMethod(component, method, annotation, charSequence);
            }
        }
    }

    /**
     * Handle default events for a given component method.
     *
     * @param component    component instance
     * @param method       component method
     * @param annotation   event annotation
     * @param charSequence new value of the element (null if not revelant)
     */
    protected void defaultHandlerComponentMethod(final Object component, final Method method,
            final Class<? extends Annotation> annotation, final CharSequence[] charSequence) {
        final Class<?>[] parameterTypes = method.getParameterTypes();

        final Object[] args = ReflectionUtils.toArgs(new Function<Class<?>, Object>() {
            @Override
            public Object apply(final Class<?> input) {
                if (CharSequence.class.isAssignableFrom(input)) {
                    return charSequence;
                }
                return null;
            }
        }, parameterTypes);

        try {
            ReflectionUtils.invoke(method, component, args);
        } catch (final IllegalAccessException e) {
            throw new EventAnnotationsException("An error has occured in @" + annotation.getSimpleName() + " " + method, e);
        } catch (final InvocationTargetException e) {
            if (e.getTargetException() instanceof RuntimeException) {
                throw (RuntimeException) e.getTargetException();
            } else if (e.getTargetException() instanceof Error) {
                throw (Error) e.getTargetException();
            }
            throw new EventAnnotationsException("An error has occured in @" + annotation.getSimpleName() + " " + method, e);
        }
    }

    @Override
    public void beforeClickOn(final WebElement element, final WebDriver driver) {
        defaultHandler(BeforeClickOn.class, element, null);
    }

    @Override
    public void afterClickOn(final WebElement element, final WebDriver driver) {
        defaultHandler(AfterClickOn.class, element, null);
    }

    @Override
    public void beforeChangeValueOf(final WebElement element, final WebDriver driver, final CharSequence[] charSequence) {
        defaultHandler(BeforeChangeValueOf.class, element, charSequence);
    }

    @Override
    public void afterChangeValueOf(final WebElement element, final WebDriver driver, final CharSequence[] charSequence) {
        defaultHandler(AfterChangeValueOf.class, element, charSequence);
    }

    @Override
    public void beforeNavigateTo(final String url, final WebDriver driver) {
        //Do nothing.
    }

    @Override
    public void afterNavigateTo(final String url, final WebDriver driver) {
        //Do nothing.
    }

    @Override
    public void beforeNavigateBack(final WebDriver driver) {
        //Do nothing.
    }

    @Override
    public void afterNavigateBack(final WebDriver driver) {
        //Do nothing.
    }

    @Override
    public void beforeNavigateForward(final WebDriver driver) {
        //Do nothing.
    }

    @Override
    public void afterNavigateForward(final WebDriver driver) {
        //Do nothing.
    }

    @Override
    public void beforeNavigateRefresh(final WebDriver driver) {
        //Do nothing.
    }

    @Override
    public void afterNavigateRefresh(final WebDriver driver) {
        //Do nothing.
    }

    @Override
    public void beforeScript(final String script, final WebDriver driver) {
        //Do nothing.
    }

    @Override
    public void afterScript(final String script, final WebDriver driver) {
        //Do nothing.
    }

    @Override
    public void onException(final Throwable throwable, final WebDriver driver) {
        //Do nothing.
    }
}
