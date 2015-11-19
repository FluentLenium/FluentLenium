package org.fluentlenium.integration.shareddriver;

import org.fluentlenium.adapter.util.SharedDriver;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

@SharedDriver(type = SharedDriver.SharedType.PER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SharedDriverPerClass1 extends LocalFluentCase {

  @Test
  public void firstMethod() {
    goTo(LocalFluentCase.DEFAULT_URL);
    assertThat($(".small", withName("name"))).hasSize(1);
  }


  @Test
  public void secondMethod() {
    assertThat($(".small", withName("name"))).hasSize(1);
  }


}
