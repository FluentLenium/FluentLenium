package org.fluentlenium.core.events;

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
import java.util.function.Function;

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
    public AnnotationsComponentListener(ComponentsAccessor componentsAccessor) {
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
    protected void findByHandler(Class<? extends Annotation> annotation, By by, WebElement element, WebDriver driver) {
        if (element == null) {
            return;
        }
        Set<Object> components = componentsAccessor.getComponents(element);
        if (components == null) {
            return;
        }
        for (Object component : components) {
            for (Method method : ReflectionUtils.getDeclaredMethodsWithAnnotation(component, annotation)) {
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
    protected void findByHandlerComponentMethod(Object component, Method method, Class<? extends Annotation> annotation, By by) {
        Class<?>[] parameterTypes = method.getParameterTypes();

        Object[] args = ReflectionUtils.toArgs(new Function<Class<?>, Object>() {
            @Override
            public Object apply(Class<?> input) {
                if (input.isAssignableFrom(By.class)) {
                    return by;
                }
                return null;
            }
        }, parameterTypes);

        try {
            ReflectionUtils.invoke(method, component, args);
        } catch (IllegalAccessException e) {
            throw new EventAnnotationsException("An error has occured in @BeforeFindBy " + method, e);
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof RuntimeException) {
                throw (RuntimeException) e.getTargetException();
            } else if (e.getTargetException() instanceof Error) {
                throw (Error) e.getTargetException();
            }
            throw new EventAnnotationsException("An error has occured in @BeforeFindBy " + method, e);
        }
    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        findByHandler(BeforeFindBy.class, by, element, driver);
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        findByHandler(AfterFindBy.class, by, element, driver);
    }

    /**
     * Handle default events.
     *
     * @param annotation   event annotation
     * @param element      selenium event
     * @param charSequence new value of the element (null if not revelant)
     */
    protected void defaultHandler(Class<? extends Annotation> annotation, WebElement element, CharSequence[] charSequence) {
        if (element == null) {
            return;
        }
        Set<Object> components = componentsAccessor.getComponents(element);
        if (components == null) {
            return;
        }
        for (Object component : components) {
            for (Method method : ReflectionUtils.getDeclaredMethodsWithAnnotation(component, annotation)) {
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
    protected void defaultHandlerComponentMethod(Object component, Method method, Class<? extends Annotation> annotation,
            CharSequence[] charSequence) {
        Class<?>[] parameterTypes = method.getParameterTypes();

        Object[] args = ReflectionUtils.toArgs(new Function<Class<?>, Object>() {
            @Override
            public Object apply(Class<?> input) {
                if (CharSequence.class.isAssignableFrom(input)) {
                    return charSequence;
                }
                return null;
            }
        }, parameterTypes);

        try {
            ReflectionUtils.invoke(method, component, args);
        } catch (IllegalAccessException e) {
            throw new EventAnnotationsException("An error has occured in @" + annotation.getSimpleName() + " " + method, e);
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof RuntimeException) {
                throw (RuntimeException) e.getTargetException();
            } else if (e.getTargetException() instanceof Error) {
                throw (Error) e.getTargetException();
            }
            throw new EventAnnotationsException("An error has occured in @" + annotation.getSimpleName() + " " + method, e);
        }
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        defaultHandler(BeforeClickOn.class, element, null);
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        defaultHandler(AfterClickOn.class, element, null);
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] charSequence) {
        defaultHandler(BeforeChangeValueOf.class, element, charSequence);
    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] charSequence) {
        defaultHandler(AfterChangeValueOf.class, element, charSequence);
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
        //Do nothing.
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        //Do nothing.
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {
        //Do nothing.
    }

    @Override
    public void afterNavigateBack(WebDriver driver) {
        //Do nothing.
    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {
        //Do nothing.
    }

    @Override
    public void afterNavigateForward(WebDriver driver) {
        //Do nothing.
    }

    @Override
    public void beforeNavigateRefresh(WebDriver driver) {
        //Do nothing.
    }

    @Override
    public void afterNavigateRefresh(WebDriver driver) {
        //Do nothing.
    }

    @Override
    public void beforeScript(String script, WebDriver driver) {
        //Do nothing.
    }

    @Override
    public void afterScript(String script, WebDriver driver) {
        //Do nothing.
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
        //Do nothing.
    }
}
