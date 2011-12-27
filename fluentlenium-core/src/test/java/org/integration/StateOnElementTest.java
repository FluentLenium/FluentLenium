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

package org.integration;

import org.integration.localTest.LocalFluentCase;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class StateOnElementTest extends LocalFluentCase {

    @Test
    public void checkIsEnabled() {
        goTo(DEFAULT_URL);
        assertThat($("input").first().isEnabled()).isTrue();
    }

    @Test
    public void checkIsDisplayed() {
        goTo(DEFAULT_URL);
        assertThat($("input").first().isDisplayed()).isTrue();
    }

    @Test
    public void checkIsNotSelected() {
        goTo(DEFAULT_URL);
        assertThat($("input").first().isSelected()).isFalse();
    }

    @Test
    public void checkIsSelected() {
        goTo(DEFAULT_URL);
        assertThat($("#selected").first().isSelected()).isTrue();
    }

    @Test
    public void checkIsDisabled() {
        goTo(DEFAULT_URL);
        assertThat($("#disabled").first().isSelected()).isFalse();
    }

    @Test
    public void checkIsNonDisplay() {
        goTo(DEFAULT_URL);
        assertThat($("#non_display").first().isDisplayed()).isFalse();
    }


}
