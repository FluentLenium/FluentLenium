package org.fluentlenium.core.events;

import com.google.common.base.Function;
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

public class AnnotationsComponentListener implements WebDriverEventListener {
    private ComponentsAccessor componentsAccessor;

    public AnnotationsComponentListener(ComponentsAccessor componentsAccessor) {
        this.componentsAccessor = componentsAccessor;
    }

    protected void findByHandler(Class<? extends Annotation> annotation, final By by, WebElement element, WebDriver driver) {
        if (element == null) {
            return;
        }
        Set<Object> components = this.componentsAccessor.getComponents(element);
        if (components == null) {
            return;
        }
        for (Object component : components) {
            for (Method method : ReflectionUtils.getDeclaredMethodsWithAnnotation(component, annotation)) {
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

    protected void defaultHandler(Class<? extends Annotation> annotation, WebElement element, WebDriver driver) {
        if (element == null) {
            return;
        }
        Set<Object> components = this.componentsAccessor.getComponents(element);
        if (components == null) {
            return;
        }
        for (Object component : components) {
            for (Method method : ReflectionUtils.getDeclaredMethodsWithAnnotation(component, annotation)) {
                Class<?>[] parameterTypes = method.getParameterTypes();

                Object[] args = ReflectionUtils.toArgs(new Function<Class<?>, Object>() {
                    @Override
                    public Object apply(Class<?> input) {
                        return null;
                    }
                }, parameterTypes);

                try {
                    ReflectionUtils.invoke(method, component, args);
                } catch (IllegalAccessException e) {
                    throw new EventAnnotationsException("An error has occured in @" + annotation.getSimpleName() + " " + method,
                            e);
                } catch (InvocationTargetException e) {
                    if (e.getTargetException() instanceof RuntimeException) {
                        throw (RuntimeException) e.getTargetException();
                    } else if (e.getTargetException() instanceof Error) {
                        throw (Error) e.getTargetException();
                    }
                    throw new EventAnnotationsException("An error has occured in @" + annotation.getSimpleName() + " " + method,
                            e);
                }
            }
        }
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        defaultHandler(BeforeClickOn.class, element, driver);
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        defaultHandler(AfterClickOn.class, element, driver);
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver) {
        defaultHandler(BeforeChangeValueOf.class, element, driver);
    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver) {
        defaultHandler(AfterChangeValueOf.class, element, driver);
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {
    }

    @Override
    public void afterNavigateBack(WebDriver driver) {
    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {
    }

    @Override
    public void afterNavigateForward(WebDriver driver) {
    }

    @Override
    public void beforeNavigateRefresh(WebDriver driver) {
    }

    @Override
    public void afterNavigateRefresh(WebDriver driver) {
    }

    @Override
    public void beforeScript(String script, WebDriver driver) {
    }

    @Override
    public void afterScript(String script, WebDriver driver) {
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
    }
}
