package org.openqa.selenium.interactions;

@Deprecated
public interface Mouse {
    void click(Coordinates where);

    void doubleClick(Coordinates where);

    void mouseDown(Coordinates where);

    void mouseUp(Coordinates where);

    void mouseMove(Coordinates where);

    void mouseMove(Coordinates where, long xOffset, long yOffset);

    void contextClick(Coordinates where);
}
