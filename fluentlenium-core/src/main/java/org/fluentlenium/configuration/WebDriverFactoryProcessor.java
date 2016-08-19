package org.fluentlenium.configuration;

import org.atteo.classindex.processor.ClassIndexProcessor;

public class WebDriverFactoryProcessor extends ClassIndexProcessor {
    public WebDriverFactoryProcessor() {
        indexSubclasses(WebDriverFactory.class);
    }
}
