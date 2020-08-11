# FluentLenium Appium tests

a) Setup mobile environment
- [iOS Guide](https://medium.com/2359media/tutorial-automated-testing-on-ios-with-appium-test-ng-and-java-on-mac-bc115d0ec881)
- [Android Guide](https://medium.com/2359media/tutorial-automated-testing-on-android-and-ios-with-appium-testng-and-java-on-mac-210119edf323)
- [Appium Install](https://www.swtestacademy.com/appium-tutorial/)

b) Get sample apps:
- [iOS](https://github.com/King-of-Spades/AppCenter-Samples/tree/master/Appium/iOS)
- [Android](https://github.com/King-of-Spades/AppCenter-Samples/tree/master/Appium/Android)

c) Change app paths in config

d) Run Android Emulator and iPhone Simulator locally

e) Run test

`mvn clean test -Dspring.profiles.active=android -Dtest=AndroidSwiftNotesApp`

`mvn clean test -Dspring.profiles.active=iphone -Dtest=IosUITestDemo`