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

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.testng.annotations.Test;

/**
 * @author Tomasz Nurkiewicz
 * @since 7/28/13, 11:05 AM
 */
public class CyclicDependencyTest extends LocalFluentCase {

    @Page
    private MainPage mainPage;

    @Test
    public void simpleCyclicDependency() {
        mainPage.
                openDialog().
                showPanel().
                hide().
                close().
                done();
    }

    @Test
    public void cyclicDependencyWithMultipleSteps() {
        mainPage.
                openDialog().
                showPanel().
                closeAll().
                openDialog();
    }

}

class MainPage extends FluentPage {

    @Page
    private Dialog dialog;

    public Dialog openDialog() {
        return dialog;
    }

    public MainPage done() {
        return this;
    }

}

class Dialog extends FluentPage {

    @Page
    private Panel panel;

    @Page
    private MainPage mainPage;

    public Panel showPanel() {
        return panel;
    }

    public MainPage close() {
        return mainPage;
    }

}

class Panel extends FluentPage {

    @Page
    private Dialog dialog;

    @Page
    private MainPage mainPage;

    public Dialog hide() {
        return dialog;
    }

    public MainPage closeAll() {
        return mainPage;
    }

}