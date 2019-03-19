package io.enoa.json.provider.gson;

import io.enoa.json.Json;
import io.enoa.toolkit.map.Kv;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class GsonProviderTest {


  @Before
  public void ibe() {
    Json.epm().install(new GsonProvider(new Gsonfig.Builder().fixPrecision().build()));
  }

  @Test
  public void testGson() {
    String json = "{\"username\":\"tomcat\",\"uuid\":123456789012,\"mapObj\":{\"tname\":\"jerry\",\"tid\":111523065825},\"arg\":\"abc\"}";
    Kv kv = Json.parse(json, Kv.class);
    Map map = Json.parse(json, Map.class);
    System.out.println(kv);
    System.out.println(map);
  }

}
