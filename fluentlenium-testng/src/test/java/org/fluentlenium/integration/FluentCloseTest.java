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

import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

public class FluentCloseTest extends LocalFluentCase {

    @Test
    public void when_default_shared_driver_then_driver_is_closed_after_method_call() {
        goTo("http://www.google.com");
    }

    @AfterClass
    public void afterClass() {
        try {
            getDriver().get("http://www.yahoo.fr");
            fail("should have a SessionNotFoundException because driver is closed");
        } catch (WebDriverException e) {

        }
    }

}
