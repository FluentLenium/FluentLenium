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

import java.io.File;
import java.io.IOException;

import org.fluentlenium.integration.localtest.SauceLabsFluentCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class TakeSnapshotOnLabsTest extends SauceLabsFluentCase {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void can_take_a_snapshot() throws IOException {
        goTo(DEFAULT_URL);
        String absolutePath = folder.newFile("fluentlenium.png").getAbsolutePath();
        takeScreenShot(absolutePath);
        assertThat(new File(absolutePath)).exists();
    }

}
