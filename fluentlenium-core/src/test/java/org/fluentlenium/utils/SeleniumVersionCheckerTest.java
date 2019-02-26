package org.fluentlenium.utils;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.utils.SeleniumVersionChecker.retrieveVersionFromPom;

public class SeleniumVersionCheckerTest {

    private final static String EXPECTED_VERSION = "3.141.59";
    private final static String PATH_TO_POM = "org/fluentlenium/utils/";
    private final static String POM = "dummy_pom.xml";

    @Test
    public void retrieveVersionFromPomTest() throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(requireNonNull(classLoader.getResource(PATH_TO_POM + POM)).getFile());

        Model model = reader.read(new FileReader(file));

        String actualVersion = retrieveVersionFromPom(model);

        assertThat(actualVersion).isEqualTo(EXPECTED_VERSION);
    }

    @Test
    public void shouldReturnNullWhenVersionNotPresentTest() throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(requireNonNull(classLoader.getResource(PATH_TO_POM + "dummy/" + POM)).getFile());

        Model model = reader.read(new FileReader(file));

        String actualVersion = retrieveVersionFromPom(model);

        assertThat(actualVersion).isNull();
    }
}