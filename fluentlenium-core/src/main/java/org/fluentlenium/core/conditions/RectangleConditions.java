package org.fluentlenium.core.conditions;

import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.Rectangle;


/**
 * Conditions API for Rectangle
 */
public interface RectangleConditions extends Conditions<Rectangle> {
    @Override
    RectangleConditions not();

    boolean withX(int x);

    IntegerConditions withX();

    boolean withY(int y);

    IntegerConditions withY();

    boolean withPosition(int x, int y);

    boolean withWidth(int width);

    IntegerConditions withWidth();

    boolean withHeight(int height);

    IntegerConditions withHeight();

    boolean withDimension(int width, int height);

    boolean withPositionAndDimension(int x, int y, int width, int height);
}
