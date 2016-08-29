package org.fluentlenium.integration;

import org.fluentlenium.core.hook.wait.Wait;
import org.fluentlenium.core.hook.wait.WaitHook;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;

@Wait
public class WaitHookWithTest extends IntegrationFluentTest {
    @Test
    public void testWaiting() {
        goTo(JAVASCRIPT_URL);
        find("#newField").click();
    }
}
