package io.enoa.toolkit.number;

import org.junit.Test;

public class HexKitTest {

  @Test
  public void testHex() {
    String text = "11";
//    String hex = HexKit.hex(text.getBytes());
//    System.out.println(hex);
    byte[] bytes = HexKit.bytes(text);
    System.out.println(new String(bytes));

  }


}
