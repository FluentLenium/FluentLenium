package org.fluentlenium.adapter.testng;

import org.fluentlenium.adapter.FluentTestRunnerAdapter;
import org.fluentlenium.utils.SeleniumVersionChecker;
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
 * TestNG FluentLenium Test Runner Adapter.
 * <p>
 * Extends this class to provide FluentLenium support to your TestNG Test class.
 */
public class FluentTestNgSpringTest extends TestNGSpringFluentTestRunnerAdapter {

    private final Map<ITestContext, Map<Method, ITestNGMethod>> methods = new HashMap<>();

    private Map<Method, ITestNGMethod> getMethods(ITestContext context) {
        synchronized (this) {
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
    }

    /**
     * After test.
     *
     * @param context test context
     */
    @AfterTest(alwaysRun = true)
    public void afterTest(ITestContext context) {
        synchronized (this) {
            methods.remove(context);
        }
    }

    /**
     * Before test.
     *
     * @param method  test method
     * @param context test context
     */
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method, ITestContext context) {
        SeleniumVersionChecker.checkSeleniumVersion();
        ITestNGMethod testNGMethod = getMethods(context).get(method);
        starting(testNGMethod.getRealClass(), testNGMethod.getMethodName());
    }

    /**
     * After test method.
     *
     * @param result test result
     */
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        if (!result.isSuccess()) {
            failed(result.getThrowable(), result.getTestClass().getRealClass(), result.getName());
        }
        finished(result.getTestClass().getRealClass(), result.getName());
    }

    /**
     * After test class.
     */
    @AfterClass(alwaysRun = true)
    public void afterClass() {
        FluentTestRunnerAdapter.afterClass(getClass());
    }
}
