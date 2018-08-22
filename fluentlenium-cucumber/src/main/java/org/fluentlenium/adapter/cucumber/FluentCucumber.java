package org.fluentlenium.adapter.cucumber;

import cucumber.api.TypeRegistryConfigurer;
import cucumber.api.event.TestRunFinished;
import cucumber.api.formatter.Formatter;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.RuntimeOptionsFactory;
import cucumber.runtime.Reflections;
import cucumber.runtime.DefaultTypeRegistryConfiguration;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import cucumber.runtime.java.JavaBackend;
import cucumber.runtime.junit.Assertions;
import cucumber.runtime.junit.FeatureRunner;
import cucumber.runtime.junit.JUnitOptions;
import cucumber.runtime.junit.JUnitReporter;
import cucumber.runtime.model.CucumberFeature;
import io.cucumber.stepexpression.TypeRegistry;
import org.fluentlenium.configuration.FluentConfiguration;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;
import static org.fluentlenium.adapter.cucumber.FluentCucumberTestContainer.FLUENT_TEST;

/**
 * Main point for integrating FluentLenium with Cucumber. Pass this class to JUnit @RunWith() annotation to enable
 * FluentLenium dependency injection for Cucumber steps.
 * To use annotation configuration annotate class with @RunWith(FluentCucumber.class) and not on BaseTest or any other
 * class containing Cucumber steps.
 */
@SuppressWarnings("unused")
public class FluentCucumber extends ParentRunner<FeatureRunner> {
    private final JUnitReporter jUnitReporter;
    private final List<FeatureRunner> children = new ArrayList<>();
    final Runtime runtime;
    private final Formatter formatter;
    private final ClassFinder classFinder;
    private final RuntimeOptions runtimeOptions;

    /**
     * Constructor for FluentCucumber.
     *
     * @param clazz runner class
     * @throws InitializationError problem with initialization
     * @throws IOException         problem with reading feature files
     */
    public FluentCucumber(Class clazz) throws InitializationError, IOException {
        super(clazz);
        ClassLoader classLoader = clazz.getClassLoader();
        Assertions.assertNoCucumberAnnotatedMethods(clazz);
        RuntimeOptionsFactory runtimeOptionsFactory = new RuntimeOptionsFactory(clazz);
        runtimeOptions = runtimeOptionsFactory.create();
        ResourceLoader resourceLoader = new MultiLoader(classLoader);
        classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);

        getFluentConfiguration(clazz);
        this.runtime = new Runtime(resourceLoader, classLoader, singletonList(getBackend()), runtimeOptions);
        this.formatter = runtimeOptions.formatter(classLoader);

        JUnitOptions junitOptions = new JUnitOptions(runtimeOptions.getJunitOptions());
        List<CucumberFeature> cucumberFeatures =
                runtimeOptions.cucumberFeatures(resourceLoader, this.runtime.getEventBus());

        this.jUnitReporter = new JUnitReporter(this.runtime.getEventBus(), runtimeOptions.isStrict(), junitOptions);
        this.addChildren(cucumberFeatures);
    }

    /**
     * Initialization of JavaBackend with {@link FluentObjectFactory} for FluentCucumber tests.
     *
     * @return backend with {@link FluentObjectFactory}
     */
    private JavaBackend getBackend() {
        Reflections reflections = new Reflections(classFinder);
        TypeRegistryConfigurer typeRegistryConfigurer =
                reflections.instantiateExactlyOneSubclass(TypeRegistryConfigurer.class,
                        MultiLoader.packageName(runtimeOptions.getGlue()),
                        new Class[0],
                        new Object[0],
                        new DefaultTypeRegistryConfiguration());

        FLUENT_TEST.instance();

        return new JavaBackend(new FluentObjectFactory(),
                classFinder, new TypeRegistry(typeRegistryConfigurer.locale()));
    }

    /**
     * Check for {@link FluentConfiguration} and pass runner class to {@link FluentCucumberTest}
     *
     * @param clazz runner class
     */
    private void getFluentConfiguration(Class clazz) {
        ofNullable(clazz.getAnnotation(FluentConfiguration.class))
                .ifPresent(annotation -> FLUENT_TEST.setRunnerClass(clazz));
    }

    /**
     * @return list of children
     */
    public List<FeatureRunner> getChildren() {
        return this.children;
    }

    /**
     * @param child FeatureRunner child
     * @return description of a FeatureRunner child
     */
    protected Description describeChild(FeatureRunner child) {
        return child.getDescription();
    }

    /**
     * Run feature.
     *
     * @param child    FeatureRunner child
     * @param notifier notifier used by Cucumber
     */
    protected void runChild(FeatureRunner child, RunNotifier notifier) {
        child.run(notifier);
    }

    /**
     * Invoke feature
     *
     * @param notifier notifier
     * @return evaluated Statement
     */
    protected Statement childrenInvoker(RunNotifier notifier) {
        final Statement features = super.childrenInvoker(notifier); //NOPMD
        return new Statement() {
            public void evaluate() {
                try {
                    features.evaluate();
                } catch (Throwable throwable) {
                    throw new RuntimeException("Cannot evaluate statement", throwable);
                }
                FluentCucumber.this.runtime.getEventBus()
                        .send(new TestRunFinished(
                                FluentCucumber.this.runtime.getEventBus()
                                        .getTime()));
            }
        };
    }

    /**
     * Add features.
     *
     * @param cucumberFeatures list of Cucumber features
     * @throws InitializationError problem with initializing features.
     */
    private void addChildren(List<CucumberFeature> cucumberFeatures) throws InitializationError {
        Iterator var2 = cucumberFeatures.iterator();

        while (var2.hasNext()) {
            CucumberFeature cucumberFeature = (CucumberFeature) var2.next();
            FeatureRunner featureRunner = new FeatureRunner(cucumberFeature, this.runtime, this.jUnitReporter);
            if (!featureRunner.isEmpty()) {
                this.children.add(featureRunner);
            }
        }
    }
}
