package org.fluentlenium.core.conditions;

import org.fluentlenium.core.conditions.message.Message;
import org.fluentlenium.core.conditions.message.MessageContext;
import org.fluentlenium.core.conditions.message.NotMessage;
import org.openqa.selenium.Rectangle;


/**
 * Conditions API for Rectangle
 */
public interface RectangleConditions extends Conditions<Rectangle> {
    @Override
    @Negation
    RectangleConditions not();

    @Message("has x == {0}")
    @NotMessage("has x != {0}")
    boolean withX(int x);

    @MessageContext("x")
    IntegerConditions withX();

    @Message("has y == {0}")
    @NotMessage("has y != {0}")
    boolean withY(int y);

    @MessageContext("y")
    IntegerConditions withY();

    @Message("has position == (x:{0}, y:{1})")
    @NotMessage("has position != (x:{0}, y:{1})")
    boolean withPosition(int x, int y);

    @Message("has width == {0}")
    @NotMessage("has width != {0}")
    boolean withWidth(int width);

    @MessageContext("width")
    IntegerConditions withWidth();

    @Message("has height == {0}")
    @NotMessage("has height != {0}")
    boolean withHeight(int height);

    @MessageContext("height")
    IntegerConditions withHeight();

    @Message("has dimension == (width:{0}, height:{1})")
    @NotMessage("has dimension != (width:{0}, height:{1})")
    boolean withDimension(int width, int height);

    @Message("has position/dimension == (x:{0}, y:{1}, width:{2}, height:{4})")
    @NotMessage("has position/dimension != (x:{0}, y:{1}, width:{2}, height:{3})")
    boolean withPositionAndDimension(int x, int y, int width, int height);
}
