/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package org.fluentlenium.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.fluentlenium.integration.localtest.SauceLabsFluentCase;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DefaultWaitOnLabsTest extends SauceLabsFluentCase {
    @Before
    public void before() {
        goTo(DEFAULT_URL);

    }

    @Override
    public WebDriver getDefaultDriver() {
        return new FirefoxDriver();
    }

    @Override
    public void setDefaultConfig() {
        withDefaultSearchWait(20, TimeUnit.SECONDS);
    }

    @Test
    public void when_default_search_wait_then_implicit_wait() {
        goTo(JAVASCRIPT_URL);
        assertThat(find("#newField")).hasSize(1);
    }

}
