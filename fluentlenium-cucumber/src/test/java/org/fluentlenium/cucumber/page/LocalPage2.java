package org.fluentlenium.cucumber.page;

import org.fluentlenium.core.FluentPage;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalPage2 extends FluentPage {

    @Override
    public String getUrl() {
        try {
            File currentFile = new File(".");
            String path = currentFile.getCanonicalPath();
            return "file://" + path + "/src/test/html/index.html";
        } catch (IOException e) {
            //NOTHING TO DO
        }
        return "";
    }

    @Override
    public void isAt() {
        assertThat(title()).contains("Page 2");
    }
}
