/*
 * Copyright (c) 2018, enoa (fewensa@enoa.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.enoa.example.yosart;

import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import io.enoa.db.provider.db.activerecord.ActiveRecordConfig;
import io.enoa.db.provider.db.activerecord.ActiveRecordProvider;
import io.enoa.db.provider.db.mybatis.MybatisConfig;
import io.enoa.db.provider.db.mybatis.MybatisProvider;
import io.enoa.db.provider.ds.c3p0.C3p0Config;
import io.enoa.db.provider.ds.c3p0.C3p0Provider;
import io.enoa.db.provider.ds.druid.DruidConfig;
import io.enoa.db.provider.ds.druid.DruidProvider;
import io.enoa.db.provider.ds.hikaricp.HikariCpConfig;
import io.enoa.db.provider.ds.hikaricp.HikariCpProvider;
import io.enoa.example.yosart.control.DbControl;
import io.enoa.example.yosart.control.PartyControl;
import io.enoa.example.yosart.control.SessControl;
import io.enoa.example.yosart.control.YosartControl;
import io.enoa.example.yosart.hook.GlobalHook;
import io.enoa.example.yosart.mapper.BaseMapper;
import io.enoa.example.yosart.router.WorkRouter;
import io.enoa.example.yosart.router.ZombieRouter;
import io.enoa.example.yosart.thr.AnostCatch;
import io.enoa.ext.sess.SessExt;
import io.enoa.ext.sess.impl.file.FileSession;
import io.enoa.json.provider.enoa.EoJsonProvider;
import io.enoa.log.Log;
import io.enoa.log.provider.slf4j.Slf4JLogProvider;
import io.enoa.provider.db.beetlsql.BeetlSQLConfig;
import io.enoa.provider.db.beetlsql.BeetlSQLProvider;
import io.enoa.repeater.http.Method;
import io.enoa.repeater.http.Response;
import io.enoa.repeater.provider.nanohttpd.server.NanoHTTPDProvider;
import io.enoa.serialization.provider.hessian.HessianProvider;
import io.enoa.template.EoTemplateFactory;
import io.enoa.template.compressor.provider.htmlcompressor.HtmlCompressorProvider;
import io.enoa.template.provider.beetl.BeetlProvider;
import io.enoa.template.provider.enjoy.EnjoyProvider;
import io.enoa.template.provider.freemarker.FreemarkerProvider;
import io.enoa.template.provider.velocity.VelocityProvider;
import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.path.PathKit;
import io.enoa.toolkit.sys.EnvKit;
import io.enoa.yosart.YoConfig;
import io.enoa.yosart.Yosart;
import io.enoa.yosart.ext.anost.AnostExt;
import io.enoa.yosart.ext.anost.AnostHookMgr;
import io.enoa.yosart.ext.render.template.TemplateRenderExt;
import io.enoa.yosart.ext.yemgr.YemgrExt;
import io.enoa.yosart.kernel.http.Session;
import io.enoa.yosart.kernel.http.YoRequest;
import io.enoa.yosart.kernel.resources.YoAction;
import io.enoa.yosart.plugin.db.DbPlugin;
import io.enoa.yosart.plugin.json.JsonPlugin;
import io.enoa.yosart.plugin.json.ext.JsonRenderExt;
import io.enoa.yosart.resp.Resp;
import org.beetl.sql.core.db.PostgresStyle;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;

public class YosartBoot {

  private static boolean ISPKG = false;

  static {
    try {
      isPkg();
    } catch (IOException ignored) {
    }
  }

  private static void isPkg() throws IOException {
    Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources("io/enoa/");
    if (urls.hasMoreElements()) {
      URL url = urls.nextElement();
      if ("jar".equals(url.getProtocol())) {
        ISPKG = true;
      }
    }
  }

  private Path tmp = PathKit.path().resolve("tmp");

  private YoConfig config() {
    return new YoConfig.Builder()
      .charset(EoConst.CHARSET)
      .tmp(this.tmp)
      .debug(true)
      .info(true)
      .infoUseLog(false)
      .other(
        Kv.create()
          .set("provider.tomcat.upload.vendor", "cos")
          .set("provider.jetty.upload.vendor", "cos")
          .set("provider.netty.upload.vendor", "cos")
      )
      .build();
  }

  private enum TEMPLATE {
    ENJOY(new EnjoyProvider(), "html"),
    BEETL(new BeetlProvider(), "html"),
    FREEMARKER(new FreemarkerProvider(), "ftl"),
    VELOCITY(new VelocityProvider(), "vm");

    private final EoTemplateFactory factory;
    private final String suffix;

    TEMPLATE(EoTemplateFactory factory, String suffix) {
      this.factory = factory;
      this.suffix = suffix;
    }


    public String basePath() {
      Path tplPath = ISPKG ? PathKit.path().resolve("resources/tpl") : PathKit.debugPath().resolve("src/main/tpl/");
      Log.debug("Template path: {}", tplPath);
      return tplPath.resolve(this.name().toLowerCase()).toString();
    }
  }

  private String DB_URL = "jdbc:postgresql://localhost:5432/enoa";
  private String DB_USER = "postgres";
  private String DB_PASSWD = "passwd";

  private DbPlugin activeRecordPlugin() {
    ActiveRecordConfig config = new ActiveRecordConfig.Builder()
      .name("activerecord")
      .ds(new HikariCpProvider(), new HikariCpConfig.Builder().url(DB_URL).user(DB_USER).passwd(DB_PASSWD).build())
      .baseSqlTemplatePath(PathKit.path().resolve("activerecord").toString())
      .sqlTemplate("template.sql")
      .dialect(new PostgreSqlDialect())
      .build();
    return new DbPlugin(new ActiveRecordProvider(), config);
  }

  private DbPlugin beetlSQLPlugin() {
    BeetlSQLConfig config = new BeetlSQLConfig.Builder()
      .name("beetlsql")
      .ds(new DruidProvider(), new DruidConfig.Builder().url(DB_URL).user(DB_USER).passwd(DB_PASSWD).build())
      .style(new PostgresStyle())
      .load("/beetl")
      .build();
    return new DbPlugin(new BeetlSQLProvider(), config);
  }

  private DbPlugin mybatisPlugin() {
    MybatisConfig config = new MybatisConfig.Builder()
      .name("mybatis")
      .ds(new C3p0Provider(), new C3p0Config.Builder().url(DB_URL).user(DB_USER).passwd(DB_PASSWD).build())
      .scan("io.enoa.example.yosart.mapper", BaseMapper.class)
      .mapper(PathKit.path().resolve("mybatis").toString())
      .suffix("xml")
      .build();
    return new DbPlugin(new MybatisProvider(), config);
  }

  // dance your self clean


  private AnostExt anost() {
    AnostExt ext = new AnostExt();
    AnostHookMgr mgr = ext.hookMgr();
    mgr.load(new GlobalHook());
    return ext;
  }

  private void start() {
    Yosart.createServer()
      .config(this.config())
      .provider(new NanoHTTPDProvider())
      .name("Yosart")
      .ssl(false)
      .log(new Slf4JLogProvider())
      .plugin(new JsonPlugin(new EoJsonProvider()))
//      .plugin(new RedisPlugin("localhost", 6379, new JdkSerializeProvider()))
//      .plugin(this.activeRecordPlugin())
//      .plugin(this.beetlSQLPlugin())
//      .plugin(this.mybatisPlugin())
//      .plugin(new ConfPlugin("https://github.com/mstine/config-repo.git"))
      .ext(new JsonRenderExt())
      .ext(new TemplateRenderExt(TEMPLATE.BEETL.factory, TEMPLATE.BEETL.basePath(), TEMPLATE.BEETL.suffix, new HtmlCompressorProvider()))
      .ext(this.anost())
      .ext(new AnostCatch())
      .ext(new YemgrExt())
      .ext(new SessExt(new FileSession("YOSESS", this.tmp, new HessianProvider(), true)))
//      .ext(new SessExt(new RedisSession("YOSESS", new RedisPlugin("SESS_REDIS","localhost", 6379, new FstProvider()), true)))
      .context("/example")
      .assets("/assets", Paths.get(EnvKit.string("java.io.tmpdir")), true)
      .handle(Method.GET, "/", (req) -> Resp.with(req).attr("req", req.data()).renderTemplate("info").end())
      .handle(YosartControl.class)
      .handle(new WorkRouter())
      .handle("/yosart", (req) -> null)
      .handle("/party", PartyControl.class)
      .handle("/corpse", new ZombieRouter())
      .handle("/download", req -> Resp.with(req).renderFile(PathKit.path().resolve("download/file.txt")).end())
      .handle("/db", DbControl.class)
      .handle("/sess", SessControl.class)
      .handle("/actionanno", new YoAction() {
        //        @Before({ActionAnnoValid.class, PartyGoHandler.class})
        @Override
        public Response handle(YoRequest request) {
          return Resp.with(request).render("action anno").end();
        }
      })
      .handle("/actionsess", (req) -> {
        Session session = req.session();
        session.set("ans", "anss");
        return Resp.with(req).forward("/sess/example");
      })
      .listen(9102);
  }

  public static void main(String[] args) {
    YosartBoot boot = new YosartBoot();
    boot.start();
  }

}
