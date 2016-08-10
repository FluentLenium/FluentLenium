package org.fluentlenium.adapter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

/**
 * All TestNG Test should extends this class. It provides default parameters.
 */
public abstract class FluentTestNg extends FluentTestRunnerAdapter {

    public FluentTestNg() {
        super(new ThreadLocalDriverContainer());
    }

    private Map<ITestContext, Map<Method, ITestNGMethod>> methods = new HashMap<>();

    synchronized public Map<Method, ITestNGMethod> getMethods(ITestContext context) {
        Map<Method, ITestNGMethod> testMethods = methods.get(context);

        if (testMethods == null) {
            testMethods = new HashMap<>();

            for (ITestNGMethod method : context.getAllTestMethods()) {
                testMethods.put(method.getConstructorOrMethod().getMethod(), method);
            }

            methods.put(context, testMethods);
        }
        return testMethods;
    }

    @AfterTest(alwaysRun = true)
    synchronized public void afterTest(ITestContext context) {
        methods.remove(context);
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method m, ITestContext context) {
        ITestNGMethod testNGMethod = getMethods(context).get(m);
        starting(testNGMethod.getRealClass(), testNGMethod.getMethodName());
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
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
