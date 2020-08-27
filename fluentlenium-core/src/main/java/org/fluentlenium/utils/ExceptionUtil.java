package org.fluentlenium.utils;

public final class ExceptionUtil {

    private ExceptionUtil() {
    }

    public static String getCauseMessage(Exception e) {
        String causeMessage = null;
        Throwable cause = e;
        while (true) {
            if (cause.getCause() == null || cause.getCause() == cause) {
                break;
            } else {
                cause = cause.getCause();
                if (cause.getLocalizedMessage() != null) {
                    causeMessage = cause.getLocalizedMessage();
                }
            }
        }
        return causeMessage;
    }

}
