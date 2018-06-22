/**
 * Copyright (c) 2011-2017, James Zhan 詹波 (jfinal@126.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.enoa.stove.firetpl.enjoy;

import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSourceFactory;
import io.enoa.firetpl.FireBody;
import io.enoa.firetpl.Firetpl;
import io.enoa.stove.api.StoveException;

import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

public class EnjoyFiretpl implements Firetpl {

  private SqlKit esql;

  public EnjoyFiretpl(Path basePath, String template) {
    this(basePath, template, false);
  }

  public EnjoyFiretpl(Path basePath, String template, boolean debug) {
    this.esql = new SqlKit(UUID.randomUUID().toString(), debug);
    this.esql.setBaseSqlTemplatePath(basePath.toString());
    this.esql.addSqlTemplate(template);
    this.esql.parseSqlTemplate();
  }

  public EnjoyFiretpl(String template) {
    this(template, false);
  }

  public EnjoyFiretpl(String template, boolean debug) {
    this.esql = new SqlKit(UUID.randomUUID().toString(), debug);
    Engine engine = this.esql.getEngine();
    engine.setSourceFactory(new ClassPathSourceFactory());
    this.esql.addSqlTemplate(template);
    this.esql.parseSqlTemplate();
  }

  @Override
  public FireBody render(String name) {
    String sql = this.esql.getSql(name);
    if (sql == null)
      throw new StoveException("Template name not found => " + name);
    return FireBody.create(sql);
  }

  @Override
  public FireBody render(String name, Map<String, ?> para) {
    SqlPara sp = this.esql.getSqlPara(name, para);
    if (sp == null)
      throw new StoveException("Template name not found => " + name);
    return FireBody.create(sp.getSql(), sp.getPara());
  }
}
