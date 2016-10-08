package org.fluentlenium.core.conditions;

import org.fluentlenium.core.conditions.message.Message;
import org.fluentlenium.core.conditions.message.NotMessage;

import java.util.regex.Pattern;

/**
 * Conditions API from String.
 */
public interface StringConditions extends Conditions<String> {

    @Override
    @Negation
    StringConditions not();

    /**
     * Check that this contains the given sequence of characters.
     *
     * @param charSequence sequence of characters
     * @return true if it contains the given sequence of characters, false otherwise
     */
    @Message("contains \"{0}\"")
    @NotMessage("does not contain \"{0}\"")
    boolean contains(CharSequence charSequence);

    /**
     * Check that this starts with the given string.
     *
     * @param prefix string
     * @return true if it starts with the given string, false otherwise
     */
    @Message("starts with \"{0}\"")
    @NotMessage("does not start with \"{0}\"")
    boolean startsWith(String prefix);

    /**
     * Check that this ends with the given string.
     *
     * @param suffix string
     * @return true if it ends with the given string, false otherwise
     */
    @Message("ends with \"{0}\"")
    @NotMessage("does not end with \"{0}\"")
    boolean endsWith(String suffix);

    /**
     * Check that this is equal to with the given string.
     *
     * @param anotherString another string
     * @return true if it is equal to the given string, false otherwise
     */
    @Message("is equal to \"{0}\"")
    @NotMessage("is not equal to \"{0}\"")
    boolean equalTo(String anotherString);

    /**
     * Check that this is equal to with the given string, ignoring case.
     *
     * @param anotherString another string
     * @return true if it is equal to the given string, ignoring case, false otherwise
     */
    @Message("is equal (ignore case) to \"{0}\"")
    @NotMessage("is not equal (ignore case) to \"{0}\"")
    boolean equalToIgnoreCase(String anotherString);

    /**
     * Check that this matches the given regular expression string.
     *
     * @param regex regular expression string
     * @return true if it matches the given regular expression string, false otherwise
     */
    @Message("matches \"{0}\"")
    @NotMessage("does not match \"{0}\"")
    boolean matches(String regex);

    /**
     * Check that this matches the given regular expression pattern.
     *
     * @param pattern regular expression pattern
     * @return true if it matches the given regular expression pattern, false otherwise
     */
    @Message("matches \"{0}\"")
    @NotMessage("does not match \"{0}\"")
    boolean matches(Pattern pattern);
}
