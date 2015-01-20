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
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StateOnElementTest extends LocalFluentCase {
    @Before
    public void before() {
        goTo(DEFAULT_URL);
    }

    @Test
    public void checkIsEnabled() {
        assertThat($("input").first().isEnabled()).isTrue();
    }

    @Test
    public void checkIsDisplayed() {
        assertThat($("input").first().isDisplayed()).isTrue();
    }

    @Test
    public void checkIsNotSelected() {
        assertThat($("input").first().isSelected()).isFalse();
    }

    @Test
    public void checkIsSelected() {
        assertThat($("#selected").first().isSelected()).isTrue();
    }

    @Test
    public void checkIsDisabled() {
        assertThat($("#disabled").first().isSelected()).isFalse();
    }

    @Test
    public void checkIsNonDisplay() {
        assertThat($("#non_display").first().isDisplayed()).isFalse();
    }


}
