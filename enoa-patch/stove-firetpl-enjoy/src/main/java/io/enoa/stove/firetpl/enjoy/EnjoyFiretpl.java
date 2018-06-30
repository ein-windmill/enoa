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

  private SectionKit section;

  public EnjoyFiretpl(Path basePath, String template) {
    this(basePath, template, false);
  }

  public EnjoyFiretpl(Path basePath, String template, boolean debug) {
    this.section = new SectionKit(UUID.randomUUID().toString(), debug);
    this.section.setBaseSectionTemplatePath(basePath.toString());
    this.section.addSectionTemplate(template);
    this.section.parseSectionTemplate();
  }

  public EnjoyFiretpl(String template) {
    this(template, false);
  }

  public EnjoyFiretpl(String template, boolean debug) {
    this.section = new SectionKit(UUID.randomUUID().toString(), debug);
    this.engine().setSourceFactory(new ClassPathSourceFactory());
    this.section.addSectionTemplate(template);
    this.section.parseSectionTemplate();
  }

  public Engine engine() {
    return this.section.getEngine();
  }

  @Override
  public FireBody render(String name) {
    String section = this.section.getSection(name);
    if (section == null)
      throw new StoveException("Template name not found => " + name);
    return FireBody.create(section);
  }

  @Override
  public FireBody render(String name, Map<String, ?> para) {
    SectionPara sp = this.section.getSectionPara(name, para);
    if (sp == null)
      throw new StoveException("Template name not found => " + name);
    return FireBody.create(sp.getSection(), sp.getPara());
  }
}
