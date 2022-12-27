package io.fluentlenium.adapter;

/**
 * Instead of extending FluentTest, you can instantiate this class directly.
 * <p>
 * If you want to test concurrency, or if you need for any reason to not use JUnit nor TestNG,
 * you may use this class.
 * <p>
 * You should call {@link #quit()} manually to close the underlying webdriver.
 *
 * @deprecated use either {@link FluentStandaloneRunnable} and {@link FluentStandalone}.
 */
@Deprecated
public class IsolatedTest extends FluentStandalone {

    /**
     * Creates a new isolated test.
     */
    public IsolatedTest() {
        init();
    }
}
