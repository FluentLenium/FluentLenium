package org.fluentlenium.core.events;

import org.fluentlenium.core.events.annotations.AfterChangeValueOf;
import org.fluentlenium.core.events.annotations.AfterClickOn;
import org.fluentlenium.core.events.annotations.AfterFindBy;
import org.fluentlenium.core.events.annotations.AfterNavigate;
import org.fluentlenium.core.events.annotations.AfterNavigateBack;
import org.fluentlenium.core.events.annotations.AfterNavigateForward;
import org.fluentlenium.core.events.annotations.AfterNavigateRefresh;
import org.fluentlenium.core.events.annotations.AfterNavigateTo;
import org.fluentlenium.core.events.annotations.AfterScript;
import org.fluentlenium.core.events.annotations.BeforeChangeValueOf;
import org.fluentlenium.core.events.annotations.BeforeClickOn;
import org.fluentlenium.core.events.annotations.BeforeFindBy;
import org.fluentlenium.core.events.annotations.BeforeNavigate;
import org.fluentlenium.core.events.annotations.BeforeNavigateBack;
import org.fluentlenium.core.events.annotations.BeforeNavigateForward;
import org.fluentlenium.core.events.annotations.BeforeNavigateRefresh;
import org.fluentlenium.core.events.annotations.BeforeNavigateTo;
import org.fluentlenium.core.events.annotations.BeforeScript;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;

/**
 * Register event annotations from a container in the events registry.
 */
public class ContainerAnnotationsEventsRegistry {
    private final EventsRegistry registry;
    private final Object container;
    private int listenerCount;

    /**
     * Creates a new container annotations events registry.
     *
     * @param registry  events registry
     * @param container container to register
     */
    public ContainerAnnotationsEventsRegistry(EventsRegistry registry, Object container) {
        this(registry, container, null);
    }

    /**
     * Creates a new container annotations events registry.
     *
     * @param registry      events registry
     * @param container     container to register
     * @param targetElement target element
     */
    @SuppressWarnings({"PMD.StdCyclomaticComplexity", "PMD.CyclomaticComplexity", "PMD.ModifiedCyclomaticComplexity"})
    public ContainerAnnotationsEventsRegistry(EventsRegistry registry, Object container, WebElement targetElement) {
        this.registry = registry;
        this.container = container;

        listenerCount = 0;
        for (Class<?> current = this.container.getClass(); current != null; current = current.getSuperclass()) {
            for (Method method : current.getDeclaredMethods()) {
                if (method.getAnnotation(BeforeClickOn.class) != null) {
                    registry.beforeClickOn(new AnnotationElementListener(method, container, BeforeClickOn.class.getSimpleName(),
                            method.getAnnotation(BeforeClickOn.class).value(), targetElement));
                    listenerCount++;
                }
                if (method.getAnnotation(AfterClickOn.class) != null) {
                    registry.afterClickOn(new AnnotationElementListener(method, container, AfterClickOn.class.getSimpleName(),
                            method.getAnnotation(AfterClickOn.class).value(), targetElement));
                    listenerCount++;
                }
                if (method.getAnnotation(BeforeChangeValueOf.class) != null) {
                    registry.beforeChangeValueOf(
                            new AnnotationElementListener(method, container, BeforeChangeValueOf.class.getSimpleName(),
                                    method.getAnnotation(BeforeChangeValueOf.class).value(), targetElement));
                    listenerCount++;
                }
                if (method.getAnnotation(AfterChangeValueOf.class) != null) {
                    registry.afterChangeValueOf(
                            new AnnotationElementListener(method, container, AfterChangeValueOf.class.getSimpleName(),
                                    method.getAnnotation(AfterChangeValueOf.class).value(), targetElement));
                    listenerCount++;
                }
                if (method.getAnnotation(BeforeFindBy.class) != null) {
                    registry.beforeFindBy(new AnnotationFindByListener(method, container, BeforeFindBy.class.getSimpleName(),
                            method.getAnnotation(BeforeFindBy.class).value(), targetElement));
                    listenerCount++;
                }
                if (method.getAnnotation(AfterFindBy.class) != null) {
                    registry.afterFindBy(new AnnotationFindByListener(method, container, AfterFindBy.class.getSimpleName(),
                            method.getAnnotation(AfterFindBy.class).value(), targetElement));
                    listenerCount++;
                }
                if (method.getAnnotation(BeforeNavigateBack.class) != null) {
                    registry.beforeNavigateBack(
                            new AnnotationNavigateListener(method, container, BeforeNavigateBack.class.getSimpleName(),
                                    method.getAnnotation(BeforeNavigateBack.class).value()));
                    listenerCount++;
                }
                if (method.getAnnotation(AfterNavigateBack.class) != null) {
                    registry.afterNavigateBack(
                            new AnnotationNavigateListener(method, container, AfterNavigateBack.class.getSimpleName(),
                                    method.getAnnotation(AfterNavigateBack.class).value()));
                    listenerCount++;
                }
                if (method.getAnnotation(BeforeNavigateForward.class) != null) {
                    registry.beforeNavigateForward(
                            new AnnotationNavigateListener(method, container, BeforeNavigateForward.class.getSimpleName(),
                                    method.getAnnotation(BeforeNavigateForward.class).value()));
                    listenerCount++;
                }
                if (method.getAnnotation(AfterNavigateForward.class) != null) {
                    registry.afterNavigateForward(
                            new AnnotationNavigateListener(method, container, AfterNavigateForward.class.getSimpleName(),
                                    method.getAnnotation(AfterNavigateForward.class).value()));
                    listenerCount++;
                }
                if (method.getAnnotation(BeforeNavigateTo.class) != null) {
                    registry.beforeNavigateTo(
                            new AnnotationNavigateToListener(method, container, BeforeNavigateTo.class.getSimpleName(),
                                    method.getAnnotation(BeforeNavigateTo.class).value()));
                    listenerCount++;
                }
                if (method.getAnnotation(AfterNavigateTo.class) != null) {
                    registry.afterNavigateTo(
                            new AnnotationNavigateToListener(method, container, AfterNavigateTo.class.getSimpleName(),
                                    method.getAnnotation(AfterNavigateTo.class).value()));
                    listenerCount++;
                }
                if (method.getAnnotation(BeforeNavigate.class) != null) {
                    registry.beforeNavigate(
                            new AnnotationNavigateAllListener(method, container, BeforeNavigate.class.getSimpleName(),
                                    method.getAnnotation(BeforeNavigate.class).value()));
                    listenerCount++;
                }
                if (method.getAnnotation(AfterNavigate.class) != null) {
                    registry.afterNavigate(
                            new AnnotationNavigateAllListener(method, container, AfterNavigate.class.getSimpleName(),
                                    method.getAnnotation(AfterNavigate.class).value()));
                    listenerCount++;
                }
                if (method.getAnnotation(BeforeNavigateRefresh.class) != null) {
                    registry.beforeNavigateRefresh(
                            new AnnotationNavigateListener(method, container, BeforeNavigateRefresh.class.getSimpleName(),
                                    method.getAnnotation(BeforeNavigateRefresh.class).value()));
                    listenerCount++;
                }
                if (method.getAnnotation(AfterNavigateRefresh.class) != null) {
                    registry.afterNavigateRefresh(
                            new AnnotationNavigateListener(method, container, AfterNavigateRefresh.class.getSimpleName(),
                                    method.getAnnotation(AfterNavigateRefresh.class).value()));
                    listenerCount++;
                }
                if (method.getAnnotation(BeforeScript.class) != null) {
                    registry.beforeScript(new AnnotationScriptListener(method, container, BeforeScript.class.getSimpleName(),
                            method.getAnnotation(BeforeScript.class).value()));
                    listenerCount++;
                }
                if (method.getAnnotation(AfterScript.class) != null) {
                    registry.afterScript(new AnnotationScriptListener(method, container, AfterScript.class.getSimpleName(),
                            method.getAnnotation(AfterScript.class).value()));
                    listenerCount++;
                }
            }
        }

        registry.sortListeners();
    }

    /**
     * Number of listener associated with the underlying container.
     *
     * @return number of listener
     */
    public int getListenerCount() {
        return listenerCount;
    }

    /**
     * Release resources associated with this component event registrations.
     */
    public void close() {
        registry.unregisterContainer(container);
    }
}
