package io.enoa.docker;

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.ret.dockerhub.HRet;
import io.enoa.docker.ret.dockerhub.build.EHAutobuild;
import io.enoa.docker.ret.dockerhub.build.EHBuildHistory;
import io.enoa.docker.ret.dockerhub.explore.EHExplore;
import io.enoa.docker.ret.dockerhub.inspece.EHRepository;
import io.enoa.docker.ret.dockerhub.search.EHSearch;
import io.enoa.docker.ret.dockerhub.tag.EHTag;
import io.enoa.http.EoHttp;
import io.enoa.http.protocol.enoa.IHttpHandler;
import io.enoa.http.protocol.enoa.IHttpReporter;
import io.enoa.json.Json;
import io.enoa.json.provider.gson.GsonProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class DockerhubTest {


  @Before
  public void ibe() {
    DockerhubConfig config = new DockerhubConfig.Builder()
      .json(new GsonProvider())
      .http(() -> EoHttp.def().http().handler(IHttpHandler.def()).reporter(IHttpReporter.def()))
      .build();
    Dockerhub.epm().install(config);
    Json.epm().install(new GsonProvider());
  }


  @Test
  public void testExplore() {
    HRet<EHExplore> ret = Dockerhub.explore();
    Assert.assertTrue(ret.ok());
    EHExplore data = ret.data();
    String json = Json.toJson(data);
    System.out.println(json);
  }

  @Test
  public void testSearch() {
    HRet<EHSearch> ret = Dockerhub.search("nginx");
    Assert.assertTrue(ret.ok());
    EHSearch data = ret.data();
    String json = Json.toJson(data);
    System.out.println(json);
  }

  @Test
  public void testInspect() {
    HRet<EHRepository> ret = Dockerhub.inspect("library/nginx");
    Assert.assertTrue(ret.ok());
    EHRepository data = ret.data();
    String json = Json.toJson(data);
    System.out.println(json);
  }

  @Test
  public void testTags() {
    HRet<EHTag> ret = Dockerhub.tags("/library/nginx", DQP.hub().page().pagesize(2).pagenumber(1));
    Assert.assertTrue(ret.ok());
    EHTag data = ret.data();
    String json = Json.toJson(data);
    System.out.println(json);
  }

  @Test
  public void testDockerfile() {
    HRet<String> ret = Dockerhub.dockerfile("jwilder/nginx-proxy");
    Assert.assertTrue(ret.ok());
    String data = ret.data();
    System.out.println(data);
  }

  @Test
  public void testAutobuild() {
    HRet<EHAutobuild> ret = Dockerhub.autobuild("jwilder/nginx-proxy");
    Assert.assertTrue(ret.ok());
    EHAutobuild data = ret.data();
    String json = Json.toJson(data);
    System.out.println(json);
  }

  @Test
  public void testHistory() {
    HRet<EHBuildHistory> ret = Dockerhub.history("jwilder/nginx-proxy");
    Assert.assertTrue(ret.ok());
    EHBuildHistory data = ret.data();
    String json = Json.toJson(data);
    System.out.println(json);
  }

}
