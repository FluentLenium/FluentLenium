package org.fluentlenium.configuration;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.opera.OperaOptions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GenericBrowserCapabilities<T extends Capabilities> {
    public T getBrowserOptions(Class<T> browserCapabilitiesTypeToReturn, Capabilities capabilitiesToMerge) {
        T browserOptionsToReturn;

        if (browserCapabilitiesTypeToReturn == ChromeOptions.class || browserCapabilitiesTypeToReturn == ChromiumOptions.class) {
            browserOptionsToReturn = mergeChromeOptions(capabilitiesToMerge);
        } else if (browserCapabilitiesTypeToReturn == EdgeOptions.class) {
            browserOptionsToReturn = mergeEdgeOptions(capabilitiesToMerge);
        } else if (browserCapabilitiesTypeToReturn == OperaOptions.class) {
            browserOptionsToReturn = mergeOperaOptions(capabilitiesToMerge);
        } else {
            browserOptionsToReturn = (T) new MutableCapabilities().merge(capabilitiesToMerge);
        }

        return browserOptionsToReturn;
    }

    private T mergeChromeOptions(Capabilities capabilitiesToMerge) {
        ChromeOptions browserOptionsToReturn = new ChromeOptions();

        if (capabilitiesToMerge != null) {
            List argsList = (List) ((Map) capabilitiesToMerge.asMap().get(ChromeOptions.CAPABILITY)).get("args");
            if (argsList != null) {
                for (Object s : argsList) {
                    browserOptionsToReturn.addArguments((String) s);
                }
            }
        }

        return (T) browserOptionsToReturn;
    }

    private T mergeEdgeOptions(Capabilities capabilitiesToMerge) {
        EdgeOptions browserOptionsToReturn = new EdgeOptions();

        for (Object s : (ArrayList) ((LinkedHashMap) capabilitiesToMerge.asMap().get(EdgeOptions.CAPABILITY)).get("args")) {
            browserOptionsToReturn.addArguments((String) s);
        }

        return (T) browserOptionsToReturn;
    }

    private T mergeOperaOptions(Capabilities capabilitiesToMerge) {
        OperaOptions browserOptionsToReturn = new OperaOptions();

        for (Object s : (ArrayList) ((LinkedHashMap) capabilitiesToMerge.asMap().get(OperaOptions.CAPABILITY)).get("args")) {
            browserOptionsToReturn.addArguments((String) s);
        }

        return (T) browserOptionsToReturn;
    }
}
