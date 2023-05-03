package io.fluentlenium.core.conditions;

import io.fluentlenium.core.conditions.message.Message;
import io.fluentlenium.core.conditions.message.MessageContext;
import io.fluentlenium.core.conditions.message.NotMessage;
import org.openqa.selenium.Rectangle;

/**
 * Conditions API for Rectangle
 */
public interface RectangleConditions extends Conditions<Rectangle> {
    @Override
    @Negation
    RectangleConditions not();

    /**
     * Check that rectangle has the given x position.
     *
     * @param x x position
     * @return true if rectangle has the given x position, false otherwise
     */
    @Message("has x == {0}")
    @NotMessage("has x != {0}")
    boolean x(int x);

    /**
     * Add advanced conditions on rectangle x position.
     *
     * @return advanced conditions builder on rectangle x position
     */
    @MessageContext("x")
    IntegerConditions x();

    /**
     * Check that rectangle has the given y position.
     *
     * @param y y position
     * @return true if rectangle has the given y position, false otherwise
     */
    @Message("has y == {0}")
    @NotMessage("has y != {0}")
    boolean y(int y);

    /**
     * Add advanced conditions on rectangle y position.
     *
     * @return advanced conditions builder on rectangle y position
     */
    @MessageContext("y")
    IntegerConditions y();

    /**
     * Check that rectangle has the given (x, y) position.
     *
     * @param x x position
     * @param y y position
     * @return true if rectangle has the given (x, y) position, false otherwise
     */
    @Message("has position == (x:{0}, y:{1})")
    @NotMessage("has position != (x:{0}, y:{1})")
    boolean position(int x, int y);

    /**
     * Check that rectangle has the given width.
     *
     * @param width width
     * @return true if rectangle has the given width, false otherwise
     */
    @Message("has width == {0}")
    @NotMessage("has width != {0}")
    boolean width(int width);

    /**
     * Add advanced conditions on rectangle width.
     *
     * @return advanced conditions builder on rectangle width
     */
    @MessageContext("width")
    IntegerConditions width();

    /**
     * Check that rectangle has the given height.
     *
     * @param height height
     * @return true if rectangle has the given height, false otherwise
     */
    @Message("has height == {0}")
    @NotMessage("has height != {0}")
    boolean height(int height);

    /**
     * Add advanced conditions on rectangle height.
     *
     * @return advanced conditions builder on rectangle height
     */
    @MessageContext("height")
    IntegerConditions height();

    /**
     * Check that rectangle has the given dimension.
     *
     * @param width  width
     * @param height height
     * @return true if rectangle has the given dimension, false otherwise
     */
    @Message("has dimension == (width:{0}, height:{1})")
    @NotMessage("has dimension != (width:{0}, height:{1})")
    boolean dimension(int width, int height);

    /**
     * Check that rectangle has the given psition and dimension.
     *
     * @param x      x position
     * @param y      y position
     * @param width  width
     * @param height height
     * @return true if rectangle has the given dimension, false otherwise
     */
    @Message("has position/dimension == (x:{0}, y:{1}, width:{2}, height:{4})")
    @NotMessage("has position/dimension != (x:{0}, y:{1}, width:{2}, height:{3})")
    boolean positionAndDimension(int x, int y, int width, int height);
}
