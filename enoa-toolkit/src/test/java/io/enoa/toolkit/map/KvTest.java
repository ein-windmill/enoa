package io.enoa.toolkit.map;

import org.junit.Test;

public class KvTest {

  @Test
  public void testKv() {
    Kv kv = Kv.create();
    kv.set("a", "b");
    System.out.println(kv);
  }

}
