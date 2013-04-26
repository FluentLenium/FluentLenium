package org.fluentlenium.adapter.util;

import org.fluentlenium.adapter.FluentTest;

/**
 * @author : Mathilde Lemee
 */
public class ShutdownHook extends Thread {
  private final FluentTest test;

  public ShutdownHook(final String s, final FluentTest test) {
    super(s);
    this.test = test;
    this.setName("browserShutdownHook");
  }

  @Override
  public synchronized void start() {
    test.quit();
  }
}
