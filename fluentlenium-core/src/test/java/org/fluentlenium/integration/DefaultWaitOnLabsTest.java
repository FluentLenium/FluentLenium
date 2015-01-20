/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package org.fluentlenium.integration;


import org.fluentlenium.integration.localtest.SauceLabsFluentCase;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

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
        withDefaultSearchWait(5, TimeUnit.HOURS);
    }

    @Test
    public void when_default_search_wait_then_implicit_wait() {
        goTo(JAVASCRIPT_URL);
        assertThat(find("#newField")).hasSize(1);
    }


}
