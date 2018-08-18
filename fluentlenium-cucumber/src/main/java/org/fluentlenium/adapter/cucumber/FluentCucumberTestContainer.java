package org.fluentlenium.adapter.cucumber;

import org.fluentlenium.adapter.FluentControlContainer;
import org.fluentlenium.adapter.SharedMutator;
import org.fluentlenium.adapter.ThreadLocalFluentControlContainer;

public enum FluentCucumberTestContainer {

    FLUENT_TEST;

    private FluentCucumberTest fluentCucumberTest;
    private FluentControlContainer controlContainer;
    private Class loaderClass;

    public FluentCucumberTest instance() {
        if (fluentCucumberTest == null) {
            controlContainer = new ThreadLocalFluentControlContainer();
            SharedMutator sharedMutator = new FluentCucumberSharedMutator();
            if (loaderClass != null) {
                fluentCucumberTest = new FluentCucumberTest(controlContainer, loaderClass, sharedMutator);
            } else {
                fluentCucumberTest = new FluentCucumberTest(controlContainer, sharedMutator);
            }
        }
        return fluentCucumberTest;
    }

    protected FluentControlContainer getControlContainer() {
        return controlContainer;
    }

    protected void setRunnerClass(Class clazz) {
        this.loaderClass = clazz;
    }
}
