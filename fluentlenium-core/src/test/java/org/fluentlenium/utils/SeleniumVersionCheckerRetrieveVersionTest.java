package org.fluentlenium.utils;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.utils.SeleniumVersionChecker.checkModelForParametrizedValue;
import static org.fluentlenium.utils.SeleniumVersionChecker.retrieveVersionFromPom;

public class SeleniumVersionCheckerRetrieveVersionTest implements SeleniumVersionCheckerTestConstants {

    private MavenXpp3Reader reader;

    @Before
    public void init() {
        reader = new MavenXpp3Reader();
    }

    @Test
    public void retrieveGoodVersionShouldReturnTrueTest() throws IOException, XmlPullParserException {

        File file = new File(PARENT_POM);

        Model model = reader.read(new FileReader(file));

        String actualVersion = retrieveVersionFromPom(model);

        assertThat(actualVersion).isEqualTo(EXPECTED_VERSION);
    }

    @Test
    public void retrieveGoodVersionFromPomPropertiesShouldReturnTrueTest() throws IOException, XmlPullParserException {

        File file = new File(PARAMETRIZED_POM);

        Model model = reader.read(new FileReader(file));

        String parametrizedVersion = retrieveVersionFromPom(model);
        String actualVersion = checkModelForParametrizedValue(parametrizedVersion, model);

        assertThat(actualVersion).isEqualTo(EXPECTED_VERSION);
    }

    @Test
    public void retrieveBadVersionShouldReturnFalseTest() throws IOException, XmlPullParserException {

        File file = new File(WRONG_VERSION_POM);

        Model model = reader.read(new FileReader(file));

        String actualVersion = retrieveVersionFromPom(model);

        assertThat(actualVersion).isNotEqualTo(EXPECTED_VERSION);
    }

    @Test
    public void retrieveVersionShouldReturnNullWhenVersionNotPresentTest() throws IOException, XmlPullParserException {

        File file = new File(CHILD_POM);

        Model model = reader.read(new FileReader(file));

        String actualVersion = retrieveVersionFromPom(model);

        assertThat(actualVersion).isNull();
    }

}
