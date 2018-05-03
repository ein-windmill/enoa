package io.enoa.toolkit.http;

import org.junit.Test;

public class UrlKitTest {


  @Test
  public void correct() {
    System.out.println(UrlKit.correct("http://httpbin.org/get"));
    System.out.println(UrlKit.correct("http://httpbin.org/get?key=v&b=1"));
    System.out.println(UrlKit.correct("http://httpbin.org/get/"));
    System.out.println(UrlKit.correct("http://httpbin.org/get//?key=v&b=1"));
  }

}
