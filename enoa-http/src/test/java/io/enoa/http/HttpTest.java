package io.enoa.http;

import io.enoa.http.protocol.HttpResponse;
import io.enoa.http.provider.httphelper.conn.ssl.TLSv;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class HttpTest {


  @Test
  public void testSSLvt() {
    HttpResponse response = Http.request("https://lod1.0u0.me")
      .tlsv(TLSv.V_1_2)
      .emit();
    String body = response.body().string();
    System.out.println(body);
  }


}
