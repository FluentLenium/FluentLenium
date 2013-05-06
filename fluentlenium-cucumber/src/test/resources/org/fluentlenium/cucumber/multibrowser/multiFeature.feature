Feature: multi browser test with only one driver instance per feature

  Scenario Outline: scenario 1
    Given I use browser <browser> with <parameters>
    And multifeature I am on the first page
    When multifeature I click on next page
    Then multifeature I am on the second page
  Examples:
    | browser   | parameters                                                            |
    | firefox   |                                                                       |
#    | phantomjs | phantomjs.binary.path@/opt/phantomjs-1.9.0-linux-x86_64/bin/phantomjs |
    #    | chrome    | webdriver.chrome.driver@/opt/chromedriver/chromedriver                |
#    | remote    | browser.name@firefox;webdriver.remote.url@http://10.147.2.83:4444/wd/hub;os.name@WIN7   |

  Scenario Outline: scenario 2
    Given I use browser <browser> with <parameters>
    And multifeature I am on the first page
    When multifeature I click on next page
    Then multifeature I am on the second page
  Examples:
    | browser   | parameters                                                            |
    | firefox   |                                                                       |
#    | phantomjs | phantomjs.binary.path@/opt/phantomjs-1.9.0-linux-x86_64/bin/phantomjs |