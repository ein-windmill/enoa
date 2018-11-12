package io.enoa.toolkit.http;

import org.junit.Assert;
import org.junit.Test;

public class UrlKitTest {


  @Test
  public void correct() {
    System.out.println(UrlKit.correct("http://httpbin.org/get"));
    System.out.println(UrlKit.correct("http://httpbin.org/get?key=v&b=1"));
    System.out.println(UrlKit.correct("http://httpbin.org/get/"));
    System.out.println(UrlKit.correct("http://httpbin.org/get//?key=v&b=1"));
  }

  @Test
  public void testAnalysis() {
    ARL arl = UrlKit.analysis("https://httpbin.org:81//get//?key=v&b=1");
    Assert.assertNotNull(arl);
    Assert.assertEquals(arl.protocol(), ARL.Protocol.HTTPS);
    Assert.assertEquals(arl.host(), "httpbin.org");
    Assert.assertSame(arl.port(), 81);
    Assert.assertEquals(arl.remain(), "get?key=v&b=1");
    Assert.assertEquals(arl.path(), "get");
    Assert.assertEquals(arl.paras(), "key=v&b=1");
  }

}
