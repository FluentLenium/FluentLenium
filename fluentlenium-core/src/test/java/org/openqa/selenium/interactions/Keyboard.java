package org.openqa.selenium.interactions;

@Deprecated
public interface Keyboard {
    void sendKeys(CharSequence... keysToSend);

    void pressKey(CharSequence keyToPress);

    void releaseKey(CharSequence keyToRelease);
}
