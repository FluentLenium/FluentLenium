package org.fluentlenium.integration;

import org.fluentlenium.integration.localtest.SauceLabsFluentCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


public class TakeSnapshotOnLabsTest extends SauceLabsFluentCase {


    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    @Test
    public void can_take_a_snapshot() throws IOException {
        goTo(DEFAULT_URL);
        String absolutePath = folder.newFile("fluentlenium.png").getAbsolutePath();
        takeScreenShot(absolutePath);
        assertThat(new File(absolutePath)).exists();
    }


}
