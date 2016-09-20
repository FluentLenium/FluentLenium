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
    RectangleConditions not();

    @Message("should have x={0}")
    @NotMessage("should not have x={0}")
    boolean withX(int x);

    @MessageContext("x")
    IntegerConditions withX();

    @Message("should not have y={0}")
    @NotMessage("should have y={0}")
    boolean withY(int y);

    @MessageContext("y")
    IntegerConditions withY();

    @Message("should have position=(x={0}, y={1})")
    @NotMessage("should not have position=(x={0}, y={1})")
    boolean withPosition(int x, int y);

    @Message("should have width={0}")
    @NotMessage("should not have width={0}")
    boolean withWidth(int width);

    @MessageContext("width")
    IntegerConditions withWidth();

    @Message("should have height={0}")
    @NotMessage("should not have height={0}")
    boolean withHeight(int height);

    @MessageContext("height")
    IntegerConditions withHeight();

    @Message("should have dimension=(x={0}, y={1})")
    @NotMessage("should not have dimension=(x={0}, y={1})")
    boolean withDimension(int width, int height);

    @Message("should have position=(x={0}, y={1}) and dimension=(width={2}, height={3})")
    @NotMessage("should not have position=(x={0}, y={1}) and dimension=(width={2}, height={3})")
    boolean withPositionAndDimension(int x, int y, int width, int height);
}
