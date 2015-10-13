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

package org.fluentlenium.unit;

import org.fluentlenium.core.domain.FluentJavascript;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.HashSet;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GoToInNewTabTest extends LocalFluentCase{
    WebDriver webDriver = Mockito.mock(WebDriver.class);
    WebDriver.TargetLocator locator = Mockito.mock(WebDriver.TargetLocator.class);

    @Test
    public void checkGoToInNewTab() {
        when(webDriver.getWindowHandles()).thenReturn(new HashSet<String>(Arrays.asList("a")),
                                                      new HashSet<String>(Arrays.asList("a", "b")));
        when(webDriver.switchTo()).thenReturn(locator);
        goToInNewTab(DEFAULT_URL);
        verify(locator).window("b");
    }

    @Override
    public FluentJavascript executeScript(String script, Object... args) {
        return null; // do nothing, it's a unit test
    }

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

}
