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

package org.fluentlenium.sample;

import static org.fest.assertions.Assertions.assertThat;

import org.fluentlenium.adapter.FluentTestNg;
import org.testng.annotations.Test;

public class BingTest extends FluentTestNg {

    @Test
    public void title_of_bing_should_contain_search_query_name_using_festassert() {
        goTo("http://www.bing.com");
        fill("#sb_form_q").with("FluentLenium");
        submit("#sb_form_go");
        assertThat(title()).contains("FluentLenium");
    }

    @Test
    public void title_of_bing_should_contain_search_query_name_using_pure_jUnit() {
        goTo("http://www.bing.com");
        fill("#sb_form_q").with("FluentLenium");
        submit("#sb_form_go");
        assertThat(title()).contains("FluentLenium");
    }

    @Test
    public void title_of_bing_should_contain_search_query_name_using_pure_jUnit_with_jquery_syntax() {
        goTo("http://www.bing.com");
        $("#sb_form_q").text("FluentLenium");
        $("#sb_form_go").submit();
        assertThat(title()).contains("FluentLenium");
    }

}
