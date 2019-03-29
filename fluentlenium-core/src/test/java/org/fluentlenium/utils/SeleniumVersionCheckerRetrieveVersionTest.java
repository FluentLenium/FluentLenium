package org.fluentlenium.utils;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.utils.SeleniumVersionChecker.resolveParametrisedVersionFromPom;
import static org.fluentlenium.utils.SeleniumVersionChecker.retrieveVersionFromPom;
import static org.fluentlenium.utils.SeleniumVersionCheckerTestConstants.CHILD_POM;
import static org.fluentlenium.utils.SeleniumVersionCheckerTestConstants.EXPECTED_VERSION;
import static org.fluentlenium.utils.SeleniumVersionCheckerTestConstants.PARAMETRIZED_POM;
import static org.fluentlenium.utils.SeleniumVersionCheckerTestConstants.PARENT_POM;
import static org.fluentlenium.utils.SeleniumVersionCheckerTestConstants.WRONG_VERSION_POM;

public class SeleniumVersionCheckerRetrieveVersionTest {

    @Test
    public void retrieveGoodVersionShouldReturnTrueTest() throws IOException, XmlPullParserException {

        Model model = getModel(PARENT_POM);

        String actualVersion = retrieveVersionFromPom(model);

        assertThat(actualVersion).isEqualTo(EXPECTED_VERSION);
    }

    @Test
    public void retrieveGoodVersionFromPomPropertiesShouldReturnTrueTest() throws IOException, XmlPullParserException {

        Model model = getModel(PARAMETRIZED_POM);

        String parametrizedVersion = retrieveVersionFromPom(model);
        String actualVersion = resolveParametrisedVersionFromPom(parametrizedVersion, model);

        assertThat(actualVersion).isEqualTo(EXPECTED_VERSION);
    }

    @Test
    public void retrieveBadVersionShouldReturnFalseTest() throws IOException, XmlPullParserException {

        Model model = getModel(WRONG_VERSION_POM);

        String actualVersion = retrieveVersionFromPom(model);

        assertThat(actualVersion).isNotEqualTo(EXPECTED_VERSION);
    }

    @Test
    public void retrieveVersionShouldReturnNullWhenVersionNotPresentTest() throws IOException, XmlPullParserException {

        Model model = getModel(CHILD_POM);

        String actualVersion = retrieveVersionFromPom(model);

        assertThat(actualVersion).isNull();
    }

    private Model getModel(String pom) throws IOException, XmlPullParserException {

        MavenXpp3Reader reader = new MavenXpp3Reader();
        File file = new File(pom);
        return reader.read(new FileReader(file));
    }

}
