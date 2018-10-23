package io.enoa.docker.registry;

import io.enoa.docker.Registry;
import io.enoa.docker.ret.registry.RRet;
import io.enoa.docker.ret.registry.tag.EITag;
import io.enoa.json.Json;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class TagTest extends AbstractRegistryTest {

  @Test
  public void testTag() {
    RRet<EITag> ret = Registry.tags("alpinex");
    Assert.assertTrue(ret.ok());
    EITag data = ret.data();
    String json = Json.toJson(data);
    System.out.println(json);
  }

}
