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

import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;

public class ActionOnListTest extends LocalFluentCase {

    @Test
    public void checkFillAction() {
        goTo(DEFAULT_URL);
        $("input[type=text]").text("zzz");

        assertThat($("input[type=text]").getValues()).contains("zzz");
    }

    @Test
    public void checkClearAction() {
        goTo(DEFAULT_URL);
        assertThat($("#name").getValues()).contains("John");
        $("#name").clear();
        assertThat($("#name").getValues()).contains("");
    }

    @Test
    public void checkClickAction() {
        goTo(DEFAULT_URL);
        assertThat(title()).contains("Selenium");
        $("#linkToPage2").click();
        assertThat(title()).isEqualTo("Page 2");
    }

    @Test
    public void checkTextAction() {
        goTo(DEFAULT_URL);
        assertThat($("#name").getValues()).contains("John");
        assertThat($(".small").getTexts()).contains("Small 1", "Small 2", "Small 3");
    }

    @Test
    public void checkFillFileInput() {
        goTo(DEFAULT_URL);
        fill("#fileUpload").with("/data/fileName");
        assertThat($("#fileUpload").getValue()).endsWith("fileName");
    }

    @Test
    public void checkFillFileInputUpperCase() {
        goTo(DEFAULT_URL);
        fill("#fileUpload2").with("/data/fileName");
        assertThat($("#fileUpload2").getValue()).endsWith("fileName");
    }
}
