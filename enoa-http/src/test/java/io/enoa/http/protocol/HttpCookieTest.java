package io.enoa.http.protocol;

import org.junit.Assert;
import org.junit.Test;

public class HttpCookieTest {


  @Test
  public void testOf() {
    HttpCookie hc = new HttpCookie.Builder()
      .name("age")
      .value("12")
      .secure()
      .httpOnly()
      .domain(".enoa.io")
      .expires(10)
      .httpOnly()
      .build();

    String cks = hc.toString();
    HttpCookie c2 = HttpCookie.single(cks);

//    Set<HttpCookie> shc = HttpCookie.of(cks);

    Assert.assertEquals(hc.name(), c2.name());
    Assert.assertEquals(hc.value(), c2.value());
    Assert.assertNotNull(c2);
  }


}
