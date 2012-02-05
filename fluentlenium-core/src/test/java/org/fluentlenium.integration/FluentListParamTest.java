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


import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class FluentListParamTest extends LocalFluentCase {

    @Test
    public void checkTextParam() {
        goTo(DEFAULT_URL);
        FluentList list = find("span");
        assertThat(list.getTexts()).contains("Small 1", "Small 2", "Small 3");
    }

    @Test
    public void checkValueAction() {
        goTo(DEFAULT_URL);
        FluentList list = find(".small");
        assertThat(list.getNames()).contains("name", "name2");
    }

    @Test
    public void checkIdAction() {
        goTo(DEFAULT_URL);
        FluentList list = find(".small");
        assertThat(list.getIds()).contains("id", "id2");
    }

    @Test
    public void checkNameAction() {
        goTo(DEFAULT_URL);
        FluentList list = find("input");
        assertThat(list.getValues()).contains("John", "Doe");
    }
}
