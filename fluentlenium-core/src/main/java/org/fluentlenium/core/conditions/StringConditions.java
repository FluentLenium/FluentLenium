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

    @Message("contains \"{0}\"")
    @NotMessage("does not contain \"{0}\"")
    boolean contains(CharSequence charSequence);

    @Message("starts with \"{0}\"")
    @NotMessage("does not start with \"{0}\"")
    boolean startsWith(String prefix);

    @Message("ends with \"{0}\"")
    @NotMessage("does not end with \"{0}\"")
    boolean endsWith(String suffix);

    @Message("is equal to \"{0}\"")
    @NotMessage("is not equal to \"{0}\"")
    boolean equalTo(String anotherString);

    @Message("is equal (ignore case) to \"{0}\"")
    @NotMessage("is not equal (ignore case) to \"{0}\"")
    boolean equalToIgnoreCase(String anotherString);

    @Message("matches \"{0}\"")
    @NotMessage("does not match \"{0}\"")
    boolean matches(String regex);

    @Message("matches \"{0}\"")
    @NotMessage("does not match \"{0}\"")
    boolean matches(Pattern pattern);
}
