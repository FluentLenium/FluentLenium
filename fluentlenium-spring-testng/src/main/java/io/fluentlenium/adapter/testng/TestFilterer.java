package io.fluentlenium.adapter.testng;

import io.fluentlenium.adapter.IFluentAdapter;
import io.fluentlenium.adapter.TestRunnerAdapter;
import io.fluentlenium.core.FluentControl;
import io.fluentlenium.adapter.IFluentAdapter;
import io.fluentlenium.adapter.TestRunnerAdapter;
import io.fluentlenium.core.FluentControl;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * TestNG attempts to run default methods from inherited interfaces when there is class @Test annotation.
 * Only methods returning void are treated as "tests".
 * This filterer makes sure end user never sees this weird behaviour.
 */
public class TestFilterer implements IMethodInterceptor {

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {

        return methods.stream()
                .filter(filterFluentLeniumMethods(FluentControl.class))
                .filter(filterFluentLeniumMethods(IFluentAdapter.class))
                .filter(filterFluentLeniumMethods(TestRunnerAdapter.class))
                .collect(Collectors.toList());
    }

    private Predicate<IMethodInstance> filterFluentLeniumMethods(Class<?> flClass) {
        return method -> !method.getMethod().getRealClass().equals(flClass);
    }

}
