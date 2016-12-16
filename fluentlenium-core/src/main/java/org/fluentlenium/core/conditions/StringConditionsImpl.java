package org.fluentlenium.core.conditions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Conditions for string
 */
public class StringConditionsImpl extends AbstractObjectConditions<String> implements StringConditions {
    /**
     * Creates a new conditions object on string.
     *
     * @param string underlying string
     */
    public StringConditionsImpl(final String string) {
        super(string);
    }

    /**
     * Creates a new conditions object on string.
     *
     * @param string   underlying string
     * @param negation negation value
     */
    public StringConditionsImpl(final String string, final boolean negation) {
        super(string, negation);
    }

    @Override
    protected StringConditionsImpl newInstance(final boolean negationValue) {
        return new StringConditionsImpl(object, negationValue);
    }

    @Override
    @Negation
    public StringConditionsImpl not() {
        return (StringConditionsImpl) super.not();
    }

    @Override
    public boolean contains(final CharSequence charSequence) {
        return verify(input -> {
            if (input == null) {
                return false;
            }
            return input.contains(charSequence);
        });
    }

    @Override
    public boolean startsWith(final String prefix) {
        return verify(input -> {
            if (input == null) {
                return false;
            }
            return input.startsWith(prefix);
        });
    }

    @Override
    public boolean endsWith(final String suffix) {
        return verify(input -> {
            if (input == null) {
                return false;
            }
            return input.endsWith(suffix);
        });
    }

    @Override
    public boolean equalTo(final String anotherString) {
        return verify(input -> {
            if (input == null) {
                return false;
            }
            return input.equals(anotherString);
        });
    }

    @Override
    public boolean equalToIgnoreCase(final String anotherString) {
        return verify(input -> {
            if (input == null) {
                return false;
            }
            return input.equalsIgnoreCase(anotherString);
        });
    }

    @Override
    public boolean matches(final String regex) {
        return verify(input -> {
            if (input == null) {
                return false;
            }
            return input.matches(regex);
        });
    }

    @Override
    public boolean matches(final Pattern pattern) {
        return verify(input -> {
            if (input == null) {
                return false;
            }
            final Matcher matcher = pattern.matcher(input);
            return matcher.matches();
        });
    }

}
