package com.lirus;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestContainersTest  extends AbstractTestContainers{

  @Test
  void canStartPostgresDb() {
    assertThat(pcontainer.isRunning()).isTrue();
    assertThat(pcontainer.isCreated()).isTrue();
  }
}
