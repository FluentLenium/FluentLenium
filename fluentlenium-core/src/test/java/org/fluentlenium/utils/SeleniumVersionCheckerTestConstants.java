package org.fluentlenium.utils;

class SeleniumVersionCheckerTestConstants {

    private String POM_NAME = "dummy_pom.xml";
    String EXPECTED_VERSION = "3.141.59";
    String PATH_TO_TEST_FOLDER = "src/test/resources/org/fluentlenium/utils/";
    String PARENT_POM = PATH_TO_TEST_FOLDER + POM_NAME;
    String CHILD_POM = PATH_TO_TEST_FOLDER + "dummy/" + POM_NAME;
    String WRONG_VERSION_POM = PATH_TO_TEST_FOLDER + "wrong/" + POM_NAME;
    String PARAMETRIZED_POM = PATH_TO_TEST_FOLDER + "parametrized/" + POM_NAME;
    String PARAMETRIZED_CHILD_POM = PATH_TO_TEST_FOLDER + "parametrized/child/" + POM_NAME;
    String PARAMETRIZED_WRONG_VERSION_POM = PATH_TO_TEST_FOLDER + "parametrized/wrong/" + POM_NAME;

    SeleniumVersionCheckerTestConstants() {}
}
