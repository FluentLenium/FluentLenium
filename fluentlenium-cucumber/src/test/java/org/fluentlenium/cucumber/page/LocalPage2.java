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
package org.fluentlenium.cucumber.page;

import org.fluentlenium.core.FluentPage;

import java.io.File;
import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

public class LocalPage2 extends FluentPage {

	@Override
	public String getUrl() {
        try {
            File currentFile = new File(".");
            String path = currentFile.getCanonicalPath();
            return "file://" + path + "/src/test/html/index.html";
        } catch (IOException e) {
            //NOTHING TO DO
        }
        return "";
    }

    @Override
    public void isAt() {
        assertThat(title()).contains("Page 2");
    }
}
