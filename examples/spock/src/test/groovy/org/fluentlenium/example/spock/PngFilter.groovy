package org.fluentlenium.example.spock

class PngFilter implements FilenameFilter {

    @Override
    boolean accept(final File dir, final String name) {
        return name.endsWith(".png")
    }
}
