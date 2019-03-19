package io.enoa.docker;

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dket.dockerhub.HRet;
import io.enoa.docker.dket.dockerhub.build.EHAutobuild;
import io.enoa.docker.dket.dockerhub.build.EHBuildHistory;
import io.enoa.docker.dket.dockerhub.explore.EHExplore;
import io.enoa.docker.dket.dockerhub.inspece.EHRepository;
import io.enoa.docker.dket.dockerhub.search.EHSearch;
import io.enoa.docker.dket.dockerhub.tag.EHTag;
import io.enoa.http.EoHttp;
import io.enoa.http.protocol.enoa.IHttpHandler;
import io.enoa.http.protocol.enoa.IHttpReporter;
import io.enoa.json.Json;
import io.enoa.json.provider.gson.GsonProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

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


  @Test
  public void testEnqueue() {
    Dockerhub.async()
      .inspect("library/nginx")
      .enqueue()
      .asset(HRet::ok)
      .failthrow(ret -> System.err.println(ret.message()))
      .<HRet<EHRepository>>then(HRet::data)
      .<EHRepository>execute(repo -> System.out.println(Json.toJson(repo)))
      .capture(System.err::println)
      .always(() -> System.out.println("Always"));
    this.sleep();
  }

  private void sleep() {
    try {
      TimeUnit.SECONDS.sleep(4L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
