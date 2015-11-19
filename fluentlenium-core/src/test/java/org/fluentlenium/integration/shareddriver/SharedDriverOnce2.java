package org.fluentlenium.integration.shareddriver;

import org.fluentlenium.adapter.util.SharedDriver;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

@SharedDriver
public class SharedDriverOnce2 extends LocalFluentCase {




  @Test
  public void secondMethod() {
    assertThat($(".small", withName("name"))).hasSize(1);
  }




}
