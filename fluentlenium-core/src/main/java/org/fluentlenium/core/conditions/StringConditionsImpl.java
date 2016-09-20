package org.fluentlenium.core.conditions;

import com.google.common.base.Predicate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringConditionsImpl extends AbstractObjectConditions<String> implements StringConditions {
    public StringConditionsImpl(String string) {
        super(string);
    }

    @Override
    protected StringConditionsImpl newInstance() {
        return new StringConditionsImpl(object);
    }

    @Override
    public StringConditionsImpl not() {
        return (StringConditionsImpl) super.not();
    }

    @Override
    public boolean contains(final CharSequence s) {
        return isVerified(new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                if (input == null) return false;
                return input.contains(s);
            }
        });
    }

    @Override
    public boolean startsWith(final String prefix) {
        return isVerified(new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                if (input == null) return false;
                return input.startsWith(prefix);
            }
        });
    }

    @Override
    public boolean endsWith(final String suffix) {
        return isVerified(new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                if (input == null) return false;
                return input.endsWith(suffix);
            }
        });
    }

    @Override
    public boolean equals(final String anotherString) {
        return isVerified(new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                if (input == null) return false;
                return input.equals(anotherString);
            }
        });
    }

    @Override
    public boolean equalsIgnoreCase(final String anotherString) {
        return isVerified(new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                if (input == null) return false;
                return input.equalsIgnoreCase(anotherString);
            }
        });
    }

    @Override
    public boolean matches(final String regex) {
        return isVerified(new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                if (input == null) return false;
                return input.matches(regex);
            }
        });
    }

    @Override
    public boolean matches(final Pattern pattern) {
        return isVerified(new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                if (input == null) return false;
                Matcher m = pattern.matcher(input);
                return m.matches();
            }
        });
    }

}
