package io.fluentlenium.example.spring.config.browser;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.edge.EdgeOptions;

class Edge implements IBrowser {

    @Override
    public Capabilities getCapabilities() {
        return new EdgeOptions();
    }

    @Override
    public String toString() {
        return "Edge";
    }
}
