package io.fluentlenium.utils;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static io.fluentlenium.utils.SeleniumVersionCheckerTestConstants.CHILD_POM;
import static io.fluentlenium.utils.SeleniumVersionCheckerTestConstants.EXPECTED_VERSION;
import static io.fluentlenium.utils.SeleniumVersionCheckerTestConstants.PARAMETRIZED_POM;
import static io.fluentlenium.utils.SeleniumVersionCheckerTestConstants.PARENT_POM;
import static io.fluentlenium.utils.SeleniumVersionCheckerTestConstants.WRONG_VERSION_POM;
import static org.assertj.core.api.Assertions.assertThat;

public class SeleniumVersionCheckerRetrieveVersionTest {

    @Test
    public void retrieveGoodVersionShouldReturnTrueTest() throws IOException, XmlPullParserException {

        Model model = getModel(PARENT_POM);

        String actualVersion = SeleniumVersionChecker.retrieveVersionFromPom(model);

        assertThat(actualVersion).isEqualTo(EXPECTED_VERSION);
    }

    @Test
    public void retrieveGoodVersionFromPomPropertiesShouldReturnTrueTest() throws IOException, XmlPullParserException {

        Model model = getModel(PARAMETRIZED_POM);

        String parametrizedVersion = SeleniumVersionChecker.retrieveVersionFromPom(model);
        String actualVersion = SeleniumVersionChecker.resolveParametrisedVersionFromPom(parametrizedVersion, model);

        assertThat(actualVersion).isEqualTo(EXPECTED_VERSION);
    }

    @Test
    public void retrieveBadVersionShouldReturnFalseTest() throws IOException, XmlPullParserException {

        Model model = getModel(WRONG_VERSION_POM);

        String actualVersion = SeleniumVersionChecker.retrieveVersionFromPom(model);

        assertThat(actualVersion).isNotEqualTo(EXPECTED_VERSION);
    }

    @Test
    public void retrieveVersionShouldReturnNullWhenVersionNotPresentTest() throws IOException, XmlPullParserException {

        Model model = getModel(CHILD_POM);

        String actualVersion = SeleniumVersionChecker.retrieveVersionFromPom(model);

        assertThat(actualVersion).isNull();
    }

    private Model getModel(String pom) throws IOException, XmlPullParserException {

        MavenXpp3Reader reader = new MavenXpp3Reader();
        File file = new File(pom);
        return reader.read(new FileReader(file));
    }

}
