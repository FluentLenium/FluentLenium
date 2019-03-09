package org.fluentlenium.adapter.junit.integration.adapter;


import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class CustomRule implements TestRule {

    private String value;

    public CustomRule(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return base;
    }
}

