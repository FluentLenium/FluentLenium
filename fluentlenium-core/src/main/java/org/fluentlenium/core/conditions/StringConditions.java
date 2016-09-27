package org.fluentlenium.core.conditions;

import org.fluentlenium.core.conditions.message.Message;

import java.util.regex.Pattern;

/**
 * Conditions API from String.
 */
public interface StringConditions extends Conditions<String> {
    @Override
    @Negation
    StringConditions not();

    @Message("should [not ]contain \"{0}\"")
    boolean contains(CharSequence s);

    @Message("should [not ]starts with \"{0}\"")
    boolean startsWith(String prefix);

    @Message("should [not ]ends with \"{0}\"")
    boolean endsWith(String suffix);

    @Message("should [not ]be equal to \"{0}\"")
    boolean equalsTo(String anotherString);

    @Message("should [not ]be equal to \"{0}\" (ignore case)")
    boolean equalsToIgnoreCase(String anotherString);

    @Message("should [not ]match \"{0}\" regular expression")
    boolean matches(String regex);

    @Message("should [not ]match \"{0}\" regular expression")
    boolean matches(Pattern pattern);
}
