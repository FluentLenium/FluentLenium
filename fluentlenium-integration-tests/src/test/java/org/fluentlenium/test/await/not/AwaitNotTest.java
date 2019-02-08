package org.fluentlenium.test.await.not;

import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class AwaitNotTest extends IntegrationFluentTest {

    @Test
    void notShouldWorkWellInAwaitsChains() {
        goTo(DISAPPEARING_EL_URL);
        await().explicitlyFor(3, TimeUnit.SECONDS);
        await().until(el(".row")).not().present();
        await().until(el(".row")).not().displayed();
    }

}
