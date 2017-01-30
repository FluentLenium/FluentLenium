package org.fluentlenium.adapter.spock.utils

class UrlUtil {

    static String getAbsoluteUrlFromFile(String file) {
        if (file == null) {
            throw new IllegalArgumentException("file must not be null")
        }

        URL url = ClassLoader.getSystemResource(file)
        if (url == null) {
            throw new NullPointerException("url from file=" + file + " is null")
        }

        return url.toString()
    }

}
