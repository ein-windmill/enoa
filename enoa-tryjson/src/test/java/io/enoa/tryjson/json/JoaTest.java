package io.enoa.tryjson.json;

import org.junit.Test;

public class JoaTest {

  @Test
  public void testJo() {
    Jo jo = Jo.create();
    jo.set("a", "b");
    System.out.println(jo);
  }

}
