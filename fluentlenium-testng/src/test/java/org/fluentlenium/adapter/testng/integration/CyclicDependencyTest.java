package org.fluentlenium.adapter.testng.integration;

import org.fluentlenium.adapter.testng.integration.localtest.IntegrationFluentTestNg;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.testng.annotations.Test;

public class CyclicDependencyTest extends IntegrationFluentTestNg {

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
