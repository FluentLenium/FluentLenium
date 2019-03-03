package org.fluentlenium.utils;

final class SeleniumVersionCheckerTestConstants {

    private static final String POM_NAME = "dummy_pom.xml";
    private static final String PATH_TO_TEST_FOLDER = "src/test/resources/org/fluentlenium/utils/";
    static final String EXPECTED_VERSION = "3.141.59";
    static final String PARENT_POM = PATH_TO_TEST_FOLDER + POM_NAME;
    static final String CHILD_POM = PATH_TO_TEST_FOLDER + "dummy/" + POM_NAME;
    static final String WRONG_VERSION_POM = PATH_TO_TEST_FOLDER + "wrong/" + POM_NAME;
    static final String PARAMETRIZED_POM = PATH_TO_TEST_FOLDER + "parametrized/" + POM_NAME;

    private SeleniumVersionCheckerTestConstants() { }
}
