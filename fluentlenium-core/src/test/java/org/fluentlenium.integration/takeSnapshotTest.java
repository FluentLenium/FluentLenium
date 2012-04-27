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

import junit.framework.AssertionFailedError;
import org.fluentlenium.adapter.FluentTest;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.fail;
import static org.fest.assertions.Assertions.assertThat;


public class TakeSnapshotTest extends FluentTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void can_take_a_snapshot() throws IOException {
        goTo(LocalFluentCase.DEFAULT_URL);
        String absolutePath = folder.newFile("fluentlenium.png").getAbsolutePath();
        takeScreenShot(absolutePath);
        assertThat(new File(absolutePath)).exists();
    }

    @Test(expected = AssertionFailedError.class)
    public void can_take_a_snapshot_when_test_fail() throws IOException {
        goTo(LocalFluentCase.DEFAULT_URL);
        this.setSnapshotMode(Mode.TAKE_SNAPSHOT_ON_FAIL);
        String canonicalPath = folder.newFolder("folder").getCanonicalPath();
        this.setSnapshotPath(canonicalPath);
        fail();
    }


}
