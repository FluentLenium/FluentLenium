package org.fluentlenium.utils;

class SeleniumVersionCheckerTestConstants {

    private final String POM_NAME = "dummy_pom.xml";
    final String EXPECTED_VERSION = "3.141.59";
    final String PATH_TO_TEST_FOLDER = "src/test/resources/org/fluentlenium/utils/";
    final String PARENT_POM = PATH_TO_TEST_FOLDER + POM_NAME;
    final String CHILD_POM = PATH_TO_TEST_FOLDER + "dummy/" + POM_NAME;
    final String WRONG_VERSION_POM = PATH_TO_TEST_FOLDER + "wrong/" + POM_NAME;
    final String PARAMETRIZED_POM = PATH_TO_TEST_FOLDER + "parametrized/" + POM_NAME;
    final String PARAMETRIZED_CHILD_POM = PATH_TO_TEST_FOLDER + "parametrized/child/" + POM_NAME;
    final String PARAMETRIZED_WRONG_VERSION_POM = PATH_TO_TEST_FOLDER + "parametrized/wrong/" + POM_NAME;

    SeleniumVersionCheckerTestConstants() { }
}
