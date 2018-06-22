package io.enoa.stove.firetpl.enjoy;

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
