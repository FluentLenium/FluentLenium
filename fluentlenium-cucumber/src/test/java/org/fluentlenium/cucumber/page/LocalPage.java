package org.fluentlenium.cucumber.page;

import org.fluentlenium.core.FluentPage;

import java.io.File;
import java.io.IOException;

public class LocalPage extends FluentPage {

    @Override
    public String getUrl() {
        try {
            File currentFile = new File(".");
            String path = currentFile.getCanonicalPath();
            return "file://" + path + "/src/test/resources/html/index.html";
        } catch (IOException e) {
            //NOTHING TO DO
        }
        return "";
    }
}
