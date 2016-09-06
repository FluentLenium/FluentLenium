package org.fluentlenium.core.events;

import org.fluentlenium.core.FluentControl;
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

import java.lang.reflect.Method;

public class ContainerAnnotationsEventsRegistry extends EventsRegistry {
    private final Object container;

    public ContainerAnnotationsEventsRegistry(FluentControl fluentControl, Object container) {
        super(fluentControl);
        this.container = container;

        //TODO: Ordre non determiné.
        for (final Method method : this.container.getClass().getDeclaredMethods()) {
            if (method.getAnnotation(BeforeClickOn.class) != null) {
                beforeClickOn(new AnnotationElementListener(method, container, BeforeClickOn.class.getSimpleName()));
            }
            if (method.getAnnotation(AfterClickOn.class) != null) {
                afterClickOn(new AnnotationElementListener(method, container, AfterClickOn.class.getSimpleName()));
            }
            if (method.getAnnotation(BeforeChangeValueOf.class) != null) {
                beforeChangeValueOf(new AnnotationElementListener(method, container, BeforeChangeValueOf.class.getSimpleName()));
            }
            if (method.getAnnotation(AfterChangeValueOf.class) != null) {
                afterChangeValueOf(new AnnotationElementListener(method, container, AfterChangeValueOf.class.getSimpleName()));
            }
            if (method.getAnnotation(BeforeFindBy.class) != null) {
                beforeFindBy(new AnnotationFindByListener(method, container, BeforeFindBy.class.getSimpleName()));
            }
            if (method.getAnnotation(AfterFindBy.class) != null) {
                afterFindBy(new AnnotationFindByListener(method, container, AfterFindBy.class.getSimpleName()));
            }
            if (method.getAnnotation(BeforeNavigateBack.class) != null) {
                beforeNavigateBack(new AnnotationNavigateListener(method, container, BeforeNavigateBack.class.getSimpleName()));
            }
            if (method.getAnnotation(AfterNavigateBack.class) != null) {
                afterNavigateBack(new AnnotationNavigateListener(method, container, AfterNavigateBack.class.getSimpleName()));
            }
            if (method.getAnnotation(BeforeNavigateForward.class) != null) {
                beforeNavigateForward(new AnnotationNavigateListener(method, container, BeforeNavigateForward.class.getSimpleName()));
            }
            if (method.getAnnotation(AfterNavigateForward.class) != null) {
                afterNavigateForward(new AnnotationNavigateListener(method, container, AfterNavigateForward.class.getSimpleName()));
            }
            if (method.getAnnotation(BeforeNavigateTo.class) != null) {
                beforeNavigateTo(new AnnotationNavigateToListener(method, container, BeforeNavigateTo.class.getSimpleName()));
            }
            if (method.getAnnotation(AfterNavigateTo.class) != null) {
                beforeNavigateTo(new AnnotationNavigateToListener(method, container, AfterNavigateTo.class.getSimpleName()));
            }
            if (method.getAnnotation(BeforeNavigate.class) != null) {
                beforeNavigate(new AnnotationNavigateAllListener(method, container, BeforeNavigate.class.getSimpleName()));
            }
            if (method.getAnnotation(AfterNavigate.class) != null) {
                afterNavigate(new AnnotationNavigateAllListener(method, container, AfterNavigate.class.getSimpleName()));
            }
            if (method.getAnnotation(BeforeNavigateRefresh.class) != null) {
                beforeNavigateRefresh(new AnnotationNavigateListener(method, container, BeforeNavigateRefresh.class.getSimpleName()));
            }
            if (method.getAnnotation(AfterNavigateRefresh.class) != null) {
                beforeNavigateRefresh(new AnnotationNavigateListener(method, container, AfterNavigateRefresh.class.getSimpleName()));
            }
            if (method.getAnnotation(BeforeScript.class) != null) {
                beforeScript(new AnnotationScriptListener(method, container, BeforeScript.class.getSimpleName()));
            }
            if (method.getAnnotation(AfterScript.class) != null) {
                afterScript(new AnnotationScriptListener(method, container, AfterScript.class.getSimpleName()));
            }
        }
    }

}
