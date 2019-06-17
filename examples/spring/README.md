# FluentLenium E2E tests

Each config property can be overridden by system property.

# Running web browser tests on Selenium Grid

`mvn clean test -DbrowserName=ie -Dmobile.simulator=false -DuseHub=true -DgridUrl=$BROWSERSTACK_GRID_URL`

`mvn clean test -DbrowserName=edge -Dmobile.simulator=false -DuseHub=true -DgridUrl=$BROWSERSTACK_GRID_URL`

`mvn clean test -DbrowserName=firefox -Dmobile.simulator=false -DuseHub=true -DgridUrl=$BROWSERSTACK_GRID_URL`

`mvn clean test -DbrowserName=chrome -Dmobile.simulator=false -DuseHub=true -DgridUrl=$BROWSERSTACK_GRID_URL`

`mvn clean test -DbrowserName=safari -Dmobile.simulator=false -DuseHub=true -DgridUrl=$BROWSERSTACK_GRID_URL`

# Running web mobile devices tests on Selenium Grid

`mvn clean test -DbrowserName=iphone -Dmobile.simulator=false -DuseHub=true -DgridUrl=$BROWSERSTACK_GRID_URL`

`mvn clean test -DbrowserName=android -Dmobile.simulator=false -DuseHub=true -DgridUrl=$BROWSERSTACK_GRID_URL`

# Running web tests locally

`mvn clean test -DbrowserName=chrome -Dmobile.simulator=false -DuseHub=false`

`mvn clean test -DbrowserName=ie -Dmobile.simulator=false -DuseHub=false`

`mvn clean test -DbrowserName=edge -Dmobile.simulator=false -DuseHub=false`

`mvn clean test -DbrowserName=firefox -Dmobile.simulator=false -DuseHub=false`

`mvn clean test -DbrowserName=safari -Dmobile.simulator=false -DuseHub=false`

# Running web mobile devices tests locally on Simulators/Emulators

How to setup simulator
- [iOS Guide](https://medium.com/2359media/tutorial-automated-testing-on-ios-with-appium-test-ng-and-java-on-mac-bc115d0ec881)
- [Android Guide](https://medium.com/2359media/tutorial-automated-testing-on-android-and-ios-with-appium-testng-and-java-on-mac-210119edf323)


`mvn clean test -DbrowserName=android_simulator -Dmobile.simulator=true -DappiumServerUrl=http://127.0.0.1:4723/wd/hub -DuseHub=false`

`mvn clean test -DbrowserName=ios_simulator -Dmobile.simulator=true -DappiumServerUrl=http://127.0.0.1:4723/wd/hub -DuseHub=false`
