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

import static org.mockito.Mockito.verify;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

public class GoToTest extends LocalFluentCase {
    WebDriver webDriver = Mockito.mock(WebDriver.class);

    @Test(expected = IllegalArgumentException.class)
    public void goToWithNullString() {
        String url = null;
        goTo(url);
    }

    @Test
    public void checkGoToUrl() {
        goTo(DEFAULT_URL);
        verify(webDriver).get(DEFAULT_URL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void goToWithNullPage() {
        FluentPage page = null;
        goTo(page);
    }

    @Test(expected = IllegalArgumentException.class)
    public void goToWithNullUrlOnPage() {
        FluentPage page = new FluentPage() {
        };
        goTo(page);
    }

    @Test
    public void checkGoToPage() {
        FluentPage page = createPage(MyPage.class);
        goTo(page);
        verify(webDriver).get(DEFAULT_URL);
    }

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }
}

class MyPage extends FluentPage {
    @Override
    public String getUrl() {
        return LocalFluentCase.DEFAULT_URL;
    }

};