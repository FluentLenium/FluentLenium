# FluentLenium E2E tests

Each config property can be overridden by system property.

# Running web tests on Grid

`mvn clean test -DbrowserName=ie -DgridUrl=$BROWSERSTACK_GRID_URL`

`mvn clean test -DbrowserName=iphone -DgridUrl=$BROWSERSTACK_GRID_URL`

# Running web tests locally

`mvn clean test -DbrowserName=chrome -DuseHub=false`

`mvn clean test -DbrowserName=android_simulator -DuseHub=false`

# Appium guides

- [iOS Guide](https://medium.com/2359media/tutorial-automated-testing-on-ios-with-appium-test-ng-and-java-on-mac-bc115d0ec881)
- [Android Guide](https://medium.com/2359media/tutorial-automated-testing-on-android-and-ios-with-appium-testng-and-java-on-mac-210119edf323)

