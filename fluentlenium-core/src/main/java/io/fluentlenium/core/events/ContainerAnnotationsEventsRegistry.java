package io.fluentlenium.core.events;

import io.fluentlenium.core.events.annotations.*;
import org.openqa.selenium.WebElement;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Register event annotations from a container in the events registry.
 */
public class ContainerAnnotationsEventsRegistry {

    private static final Map<Class<? extends Annotation>, PriorityRetriever> ANNOTATION_TO_PRIORITY;
    private static final Map<Class<? extends Annotation>, ListenerRegister> ANNOTATION_TO_REGISTER;

    private final EventsRegistry registry;
    private final Object container;
    private int listenerCount;

    static {
        final Map<Class<? extends Annotation>, PriorityRetriever> priorities = new HashMap<>();
        final Map<Class<? extends Annotation>, ListenerRegister> registers = new HashMap<>();

        //------------ Priorities ------------

        priorities.put(BeforeClickOn.class, method -> method.getAnnotation(BeforeClickOn.class).value());
        priorities.put(AfterClickOn.class, method -> method.getAnnotation(AfterClickOn.class).value());
        priorities.put(BeforeGetText.class, method -> method.getAnnotation(BeforeGetText.class).value());
        priorities.put(AfterGetText.class, method -> method.getAnnotation(AfterGetText.class).value());
        priorities.put(BeforeChangeValueOf.class, method -> method.getAnnotation(BeforeChangeValueOf.class).value());
        priorities.put(AfterChangeValueOf.class, method -> method.getAnnotation(AfterChangeValueOf.class).value());
        priorities.put(BeforeFindBy.class, method -> method.getAnnotation(BeforeFindBy.class).value());
        priorities.put(AfterFindBy.class, method -> method.getAnnotation(AfterFindBy.class).value());
        priorities.put(BeforeNavigateBack.class, method -> method.getAnnotation(BeforeNavigateBack.class).value());
        priorities.put(AfterNavigateBack.class, method -> method.getAnnotation(AfterNavigateBack.class).value());
        priorities.put(BeforeNavigateForward.class, method -> method.getAnnotation(BeforeNavigateForward.class).value());
        priorities.put(AfterNavigateForward.class, method -> method.getAnnotation(AfterNavigateForward.class).value());
        priorities.put(BeforeNavigateTo.class, method -> method.getAnnotation(BeforeNavigateTo.class).value());
        priorities.put(AfterNavigateTo.class, method -> method.getAnnotation(AfterNavigateTo.class).value());
        priorities.put(BeforeNavigate.class, method -> method.getAnnotation(BeforeNavigate.class).value());
        priorities.put(AfterNavigate.class, method -> method.getAnnotation(AfterNavigate.class).value());
        priorities.put(BeforeNavigateRefresh.class, method -> method.getAnnotation(BeforeNavigateRefresh.class).value());
        priorities.put(AfterNavigateRefresh.class, method -> method.getAnnotation(AfterNavigateRefresh.class).value());
        priorities.put(BeforeScript.class, method -> method.getAnnotation(BeforeScript.class).value());
        priorities.put(AfterScript.class, method -> method.getAnnotation(AfterScript.class).value());
        priorities.put(BeforeAlertAccept.class, method -> method.getAnnotation(BeforeAlertAccept.class).value());
        priorities.put(AfterAlertAccept.class, method -> method.getAnnotation(AfterAlertAccept.class).value());
        priorities.put(BeforeAlertDismiss.class, method -> method.getAnnotation(BeforeAlertDismiss.class).value());
        priorities.put(AfterAlertDismiss.class, method -> method.getAnnotation(AfterAlertDismiss.class).value());
        priorities.put(BeforeSwitchToWindow.class, method -> method.getAnnotation(BeforeSwitchToWindow.class).value());
        priorities.put(AfterSwitchToWindow.class, method -> method.getAnnotation(AfterSwitchToWindow.class).value());
        priorities.put(BeforeGetScreenshotAs.class, method -> method.getAnnotation(BeforeGetScreenshotAs.class).value());
        priorities.put(AfterGetScreenshotAs.class, method -> method.getAnnotation(AfterGetScreenshotAs.class).value());

        //------------ Registers ------------

        registers.put(BeforeClickOn.class, (reg, ctx) -> reg.beforeClickOn(new AnnotationElementListener(ctx)));
        registers.put(AfterClickOn.class, (reg, ctx) -> reg.afterClickOn(new AnnotationElementListener(ctx)));
        registers.put(BeforeGetText.class, (reg, ctx) -> reg.beforeGetText(new AnnotationElementListener(ctx)));
        registers.put(AfterGetText.class, (reg, ctx) -> reg.afterGetText(new AnnotationElementListener(ctx)));
        registers.put(BeforeChangeValueOf.class, (reg, ctx) -> reg.beforeChangeValueOf(new AnnotationElementListener(ctx)));
        registers.put(AfterChangeValueOf.class, (reg, ctx) -> reg.afterChangeValueOf(new AnnotationElementListener(ctx)));
        registers.put(BeforeFindBy.class, (reg, ctx) -> reg.beforeFindBy(new AnnotationFindByListener(ctx)));
        registers.put(AfterFindBy.class, (reg, ctx) -> reg.afterFindBy(new AnnotationFindByListener(ctx)));
        registers.put(BeforeNavigateBack.class, (reg, ctx) -> reg.beforeNavigateBack(new AnnotationNavigateListener(ctx)));
        registers.put(AfterNavigateBack.class, (reg, ctx) -> reg.afterNavigateBack(new AnnotationNavigateListener(ctx)));
        registers.put(BeforeNavigateForward.class, (reg, ctx) -> reg.beforeNavigateForward(new AnnotationNavigateListener(ctx)));
        registers.put(AfterNavigateForward.class, (reg, ctx) -> reg.afterNavigateForward(new AnnotationNavigateListener(ctx)));
        registers.put(BeforeNavigateTo.class, (reg, ctx) -> reg.beforeNavigateTo(new AnnotationNavigateToListener(ctx)));
        registers.put(AfterNavigateTo.class, (reg, ctx) -> reg.afterNavigateTo(new AnnotationNavigateToListener(ctx)));
        registers.put(BeforeNavigate.class, (reg, ctx) -> reg.beforeNavigate(new AnnotationNavigateAllListener(ctx)));
        registers.put(AfterNavigate.class, (reg, ctx) -> reg.afterNavigate(new AnnotationNavigateAllListener(ctx)));
        registers.put(BeforeNavigateRefresh.class, (reg, ctx) -> reg.beforeNavigateRefresh(new AnnotationNavigateListener(ctx)));
        registers.put(AfterNavigateRefresh.class, (reg, ctx) -> reg.afterNavigateRefresh(new AnnotationNavigateListener(ctx)));
        registers.put(BeforeScript.class, (reg, ctx) -> reg.beforeScript(new AnnotationScriptListener(ctx)));
        registers.put(AfterScript.class, (reg, ctx) -> reg.afterScript(new AnnotationScriptListener(ctx)));
        registers.put(BeforeAlertAccept.class, (reg, ctx) -> reg.beforeAlertAccept(new AnnotationAlertListener(ctx)));
        registers.put(AfterAlertAccept.class, (reg, ctx) -> reg.afterAlertAccept(new AnnotationAlertListener(ctx)));
        registers.put(BeforeAlertDismiss.class, (reg, ctx) -> reg.beforeAlertDismiss(new AnnotationAlertListener(ctx)));
        registers.put(AfterAlertDismiss.class, (reg, ctx) -> reg.afterAlertDismiss(new AnnotationAlertListener(ctx)));
        registers.put(BeforeSwitchToWindow.class,
                (reg, ctx) -> reg.beforeSwitchToWindow(new AnnotationSwitchToWindowListener(ctx)));
        registers
                .put(AfterSwitchToWindow.class, (reg, ctx) -> reg.afterSwitchToWindow(new AnnotationSwitchToWindowListener(ctx)));
        registers.put(BeforeGetScreenshotAs.class,
                (reg, ctx) -> reg.beforeGetScreenshotAs(new AnnotationGetScreenshotAsListener(ctx)));
        registers.put(AfterGetScreenshotAs.class,
                (reg, ctx) -> reg.afterGetScreenshotAs(new AnnotationGetScreenshotAsListener(ctx)));

        ANNOTATION_TO_PRIORITY = Collections.unmodifiableMap(priorities);
        ANNOTATION_TO_REGISTER = Collections.unmodifiableMap(registers);
    }

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
    public ContainerAnnotationsEventsRegistry(EventsRegistry registry, Object container, WebElement targetElement) {
        this.registry = registry;
        this.container = container;
        setupListenersInEventsRegistry(targetElement);
    }

    private void setupListenersInEventsRegistry(WebElement targetElement) {
        listenerCount = 0;
        for (Class<?> current = container.getClass(); current != null; current = current.getSuperclass()) {
            for (Method method : current.getDeclaredMethods()) {
                ListenerContext listenerContext = new ListenerContext(method, container, targetElement);
                ANNOTATION_TO_REGISTER.forEach((annotation, listenerRegister) -> {
                    if (method.getAnnotation(annotation) != null) {
                        listenerContext.setAnnotationName(annotation.getSimpleName());
                        listenerContext.setPriority(ANNOTATION_TO_PRIORITY.get(annotation).apply(method));
                        listenerRegister.apply(registry, listenerContext);
                        listenerCount++;
                    }
                });
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

    private interface ListenerRegister extends BiFunction<EventsRegistry, ListenerContext, EventsRegistry> {
    }

    private interface PriorityRetriever extends Function<Method, Integer> {
    }
}
