/*
 * Copyright (c) 2018, enoa (ein.windmill@outlook.com)
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
package io.enoa.repeater.provider.tomcat.server;

import io.enoa.repeater.EoxAccessor;
import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.provider.EoxProviderFactory;
import io.enoa.repeater.factory.server.RepeaterServer;
import io.enoa.toolkit.file.FileKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.sys.EnvKit;
import io.enoa.toolkit.thr.EoException;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

//import io.enoa.toolkit.file.FileKit;

class _Tomcat implements RepeaterServer {

  private EoxAccessor accessor;

  _Tomcat(EoxAccessor accessor) {
    this.accessor = accessor;
  }

  @Override
  public void listen(String hostname, int port, boolean ssl, EoxConfig config, EoxProviderFactory factory) {
    Tomcat tomcat = new Tomcat();
    if (TextKit.notBlank(hostname))
      tomcat.setHostname(hostname);
    tomcat.setPort(port);

    String docBase = null;
    if (TextKit.notBlank(config.other().string("doc_base")) ||
      TextKit.notBlank(config.other().string("docBase"))) {
      docBase = config.other().string("doc_base");
      if (TextKit.isBlank(docBase))
        config.other().string("docBase");
    }
    if (TextKit.isBlank(docBase))
      docBase = config.tmp().toString();
    if (docBase == null)
      docBase = EnvKit.string("java.io.tmpdir");


    if (!FileKit.exists(docBase))
      FileKit.mkdirs(docBase);

    tomcat.setBaseDir(docBase);
    Context ctx = tomcat.addContext("", docBase);

    // 如果选择的是默认上传, 则开启
    if ("default".equals(config.other().string("provider.tomcat.upload.vendor", "default")))
      ctx.setAllowCasualMultipartParsing(true);


    Tomcat.addServlet(ctx, "repeater", new _TomcatApp(this.accessor, factory.ts(),
      factory.rule(), factory.errorRender(), config));
    ctx.addServletMappingDecoded("/*", "repeater");

    try {
      tomcat.start();
      tomcat.getServer().await();
    } catch (Exception e) {
      throw new EoException(e.getMessage(), e);
    }
  }
}
