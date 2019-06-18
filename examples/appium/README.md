# FluentLenium Appium tests

a) Setup mobile environment
- [iOS Guide](https://medium.com/2359media/tutorial-automated-testing-on-ios-with-appium-test-ng-and-java-on-mac-bc115d0ec881)
- [Android Guide](https://medium.com/2359media/tutorial-automated-testing-on-android-and-ios-with-appium-testng-and-java-on-mac-210119edf323)
- [Appium Install](https://www.swtestacademy.com/appium-tutorial/)

b) Get sample apps:
- [iOS](https://github.com/King-of-Spades/AppCenter-Samples/tree/master/Appium/iOS)
- [Android](https://github.com/King-of-Spades/AppCenter-Samples/tree/master/Appium/Android)

c) Change getTestAppPath() methods in Iphone and Android classes

d) Run test

`mvn clean test -DdeviceName=android -Dtest=AndroidSwiftNotesApp`

`mvn clean test -DdeviceName=iphone -Dtest=IosUITestDemo`