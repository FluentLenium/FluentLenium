package org.fluentlenium.adapter;

import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * All TestNG Test should extends this class. It provides default parameters.
 */
public abstract class FluentTestNg extends FluentTestRunnerAdapter {
    public FluentTestNg() {
        super();
    }

    private static Map<Method, ITestNGMethod> methods = new HashMap<>();

    @BeforeTest
    public void beforeTest(ITestContext context) {
        for (ITestNGMethod method : context.getAllTestMethods()) {
            methods.put(method.getConstructorOrMethod().getMethod(), method);
        }
    }

    @AfterTest
    public void afterTest() {
        methods.clear();
    }

    @BeforeMethod
    public void beforeMethod(Method m, ITestContext context) {
        ITestNGMethod testNGMethod = methods.get(m);
        starting(testNGMethod.getRealClass(), testNGMethod.getMethodName());
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (!result.isSuccess()) {
            failed(result.getThrowable(), result.getTestClass().getRealClass(), result.getName());
        }
        finished(result.getTestClass().getRealClass(), result.getName());
    }

    @AfterClass
    public void afterClass() {
        releaseSharedDriver();
    }

}
