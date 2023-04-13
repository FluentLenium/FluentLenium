# FluentLenium Appium tests

a) Setup mobile environment

- [iOS Guide](https://medium.com/2359media/tutorial-automated-testing-on-ios-with-appium-test-ng-and-java-on-mac-bc115d0ec881)
- [Android Guide](https://medium.com/2359media/tutorial-automated-testing-on-android-and-ios-with-appium-testng-and-java-on-mac-210119edf323)

You'll need
* iOS Simulator (via XCode)
* Android SDK (via Android Studio

- ensure that environment variables are properly setup:
    - pom.xml sets it up to the default mac location

b) The Sample app that is being tested. It will be downloaded by the Maven build to the _target_ folder

- [iOS](https://github.com/King-of-Spades/AppCenter-Samples/tree/master/Appium/iOS)
- [Android](https://github.com/King-of-Spades/AppCenter-Samples/tree/master/Appium/Android)

c) start the Android Emulator and/or the iPhone Simulator locally

d) Run test

`mvn clean verify -PAndroid` (Android)

`mvn clean verify -iOS` (iOS)
