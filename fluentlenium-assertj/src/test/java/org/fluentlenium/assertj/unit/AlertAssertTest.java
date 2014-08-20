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

package org.fluentlenium.assertj.unit;

import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.assertj.custom.AlertAssert;
import org.fluentlenium.core.Alert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.NoAlertPresentException;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AlertAssertTest {

    @Mock
    Alert alert;
    @InjectMocks
    AlertAssert alertAssert = FluentLeniumAssertions.assertThat(alert);

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHasTextOk() {
        when(alert.getText()).thenReturn("some text");
        alertAssert.hasText("some text");
    }

    @Test(expected = AssertionError.class)
    public void testHasTextKo() throws Exception {
        when(alert.getText()).thenReturn("other text");
        alertAssert.hasText("some text");
    }

    @Test(expected = AssertionError.class)
    public void testHasTextKoNoAlert() throws Exception {
        doThrow(new NoAlertPresentException()).when(alert).getText();
        alertAssert.hasText("some text");
    }

    @Test
    public void testIsPresentOk() {
        alertAssert.isPresent();
        verify(alert).switchTo();
    }

    @Test(expected = AssertionError.class)
    public void testIsPresentKo() {
        doThrow(new NoAlertPresentException()).when(alert).switchTo();
        alertAssert.isPresent();
    }

}
