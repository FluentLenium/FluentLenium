package org.fluentlenium.adapter.testng;

import org.fluentlenium.adapter.FluentTestRunnerAdapter;
import org.fluentlenium.adapter.ThreadLocalFluentControlContainer;

public class TestNGSpringAdapter extends FluentTestRunnerAdapter {

    public TestNGSpringAdapter() {
        super(new ThreadLocalFluentControlContainer());
    }

}
