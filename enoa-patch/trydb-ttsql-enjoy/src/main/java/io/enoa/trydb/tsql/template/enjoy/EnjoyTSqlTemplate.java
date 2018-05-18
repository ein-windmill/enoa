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
package io.enoa.trydb.tsql.template.enjoy;

import io.enoa.toolkit.digest.UUIDKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.trydb.tsql.template.SqlNotfoundException;
import io.enoa.trydb.tsql.template.TSql;
import io.enoa.trydb.tsql.template.TSqlTemplate;

import java.nio.file.Path;
import java.util.Map;

public class EnjoyTSqlTemplate implements TSqlTemplate {

  private SqlKit esql;

  public EnjoyTSqlTemplate(Path basePath, String template) {
    this(basePath, template, false);
  }

  public EnjoyTSqlTemplate(Path basePath, String template, boolean debug) {
    this.esql = new SqlKit(UUIDKit.next(), debug);
    this.esql.setBaseSqlTemplatePath(basePath.toString());
    this.esql.addSqlTemplate(template);
    this.esql.parseSqlTemplate();
  }

  @Override
  public TSql render(String name) {
    String sql = this.esql.getSql(name);
    if (sql == null)
      throw new SqlNotfoundException(EnoaTipKit.message("eo.tip.trydb.ttsql_notfound", name));
    return TSql.create(sql);
  }

  @Override
  public TSql render(String name, Map<String, ?> para) {
    SqlPara sp = this.esql.getSqlPara(name, para);
    if (sp == null)
      throw new SqlNotfoundException(EnoaTipKit.message("eo.tip.trydb.ttsql_notfound", name));
    return TSql.create(sp.getSql(), sp.getPara());
  }

}
