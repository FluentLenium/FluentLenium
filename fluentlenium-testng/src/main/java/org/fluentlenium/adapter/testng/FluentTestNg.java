package org.fluentlenium.adapter.testng;

import org.fluentlenium.adapter.FluentTestRunnerAdapter;
import org.fluentlenium.adapter.ThreadLocalFluentControlContainer;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * TestNG FluentLenium Test Adapter.
 * <p>
 * Extends this class to provide FluentLenium support to your TestNG Test class.
 */
public class FluentTestNg extends FluentTestRunnerAdapter {
    private final Map<ITestContext, Map<Method, ITestNGMethod>> methods = new HashMap<>();

    public FluentTestNg() {
        super(new ThreadLocalFluentControlContainer());
    }

    public Map<Method, ITestNGMethod> getMethods(final ITestContext context) {
        synchronized (this) {
            Map<Method, ITestNGMethod> testMethods = methods.get(context);

            if (testMethods == null) {
                testMethods = new HashMap<>();

                for (final ITestNGMethod method : context.getAllTestMethods()) {
                    testMethods.put(method.getConstructorOrMethod().getMethod(), method);
                }

                methods.put(context, testMethods);
            }
            return testMethods;
        }
    }

    @AfterTest(alwaysRun = true)
    public void afterTest(final ITestContext context) {
        synchronized (this) {
            methods.remove(context);
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(final Method m, final ITestContext context) {
        final ITestNGMethod testNGMethod = getMethods(context).get(m);
        starting(testNGMethod.getRealClass(), testNGMethod.getMethodName());
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(final ITestResult result) {
        if (!result.isSuccess()) {
            failed(result.getThrowable(), result.getTestClass().getRealClass(), result.getName());
        }
        finished(result.getTestClass().getRealClass(), result.getName());
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        FluentTestRunnerAdapter.afterClass(getClass());
    }

}
