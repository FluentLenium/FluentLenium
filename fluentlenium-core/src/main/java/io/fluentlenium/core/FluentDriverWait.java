package io.fluentlenium.core;

import io.fluentlenium.configuration.Configuration;
import io.fluentlenium.core.wait.FluentWait;

import static java.util.Objects.requireNonNull;

/**
 * Creates and configures a {@link FluentWait} from a {@link Configuration} to be used via {@link FluentDriver}.
 */
public class FluentDriverWait {

    private final Configuration configuration;

    public FluentDriverWait(Configuration configuration) {
        this.configuration = requireNonNull(configuration);
    }

    /**
     * Creates a {@link FluentWait} instance with the argument {@link FluentControl},
     * and configures the FluentWait with the {@code awaitAtMost} and {@code pollingEvery} values from
     * a {@link Configuration} if they are set in that configuration.
     *
     * @param control the fluent control
     * @return the configured FluentWait
     */
    public FluentWait await(FluentControl control) {
        FluentWait fluentWait = new FluentWait(control);
        configureWithAwaitAtMost(fluentWait);
        configureWithPollingEvery(fluentWait);
        return fluentWait;
    }

    private void configureWithAwaitAtMost(FluentWait fluentWait) {
        Long atMost = configuration.getAwaitAtMost();
        if (atMost != null) {
            fluentWait.atMost(atMost);
        }
    }

    private void configureWithPollingEvery(FluentWait fluentWait) {
        Long pollingEvery = configuration.getAwaitPollingEvery();
        if (pollingEvery != null) {
            fluentWait.pollingEvery(pollingEvery);
        }
    }
}
