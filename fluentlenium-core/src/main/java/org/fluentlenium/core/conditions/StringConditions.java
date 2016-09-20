package org.fluentlenium.core.conditions;

import java.util.regex.Pattern;

/**
 * Conditions API from String.
 */
public interface StringConditions extends Conditions<String> {
    @Override
    StringConditions not();

    boolean contains(CharSequence s);

    boolean startsWith(String prefix);

    boolean endsWith(String suffix);

    boolean equals(String anotherString);

    boolean equalsIgnoreCase(String anotherString);

    boolean matches(String regex);

    boolean matches(Pattern pattern);
}
