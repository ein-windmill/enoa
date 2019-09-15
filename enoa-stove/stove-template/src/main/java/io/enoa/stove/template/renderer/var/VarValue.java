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
package io.enoa.stove.template.renderer.var;

import io.enoa.stove.template.SPM;
import io.enoa.stove.template.StoveConfig;
import io.enoa.stove.template.ast.tree.Ast;
import io.enoa.stove.template.pipeline.Pipeline;
import io.enoa.stove.template.thr.SyntaxException;
import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.is.Is;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class VarValue {

  private VarValue() {
  }

  private static class Holder {
    private static final VarValue INSTANCE = new VarValue();
  }

  /**
   * 提取变量值
   *
   * @param var  变量名
   * @param attr 传递参数属性
   * @return String
   */
  public static String parse(SPM spm, StoveConfig config, Ast ast, String var, Map<String, ?> attr) {
    List<VarBlock> blocks = Holder.INSTANCE.analysis(ast, Holder.INSTANCE.major(var));
    return Holder.INSTANCE.parse(spm, ast, config, blocks, attr);
  }

  private String major(String var) {
    StringBuilder p = new StringBuilder(var);
    int ix = 0;
    boolean can = true;
    while (p.length() > ix) {
      char c = p.charAt(ix);
      if (c == '\'' || c == '"') {
        can = !can;
      }
      if (c == ' ' && can)
        p.deleteCharAt(ix);
      ix += 1;
    }
    return p.toString();
  }

  private String parse(SPM spm, Ast ast, StoveConfig config, List<VarBlock> blocks, Map<String, ?> attr) {
    if (Is.empty(blocks))
      return null;

    VarBlock first = blocks.get(0);
    Object ret = null;
    switch (first.type()) {
      case INC:
        ret = this.parseVarValueInc(spm, ast, config, blocks, attr);
        break;
      case COND_3:
        ret = this.parseVarValueCond3(spm, ast, config, blocks, attr);
        break;
      case NORMAL:
        ret = this.parseVarValueNormal(spm, ast, config, first.code(), attr);
        break;
    }
    return ret == null ? null : Objects.toString(ret);
  }

  /**
   * 变量获取语法分析
   *
   * @param var 变量
   * @return List
   */
  private List<VarBlock> analysis(Ast ast, String var) {
    char[] chars = var.toCharArray();

    List<VarBlock> blocks = new ArrayList<>();
    VarBlock block = null;
    int spL = 0;
    int mode = 0;

    int prevIx;
    for (int i = 0; i < chars.length; i++) {
      char c = chars[i];
      prevIx = i - 1;
      switch (mode) {
        case 0:
          if (c == '?') {
            mode = 2;
            if (block != null) {
              block.type(VarBlock.Type.COND_3);
              blocks.add(block);
            }
            block = new VarBlock(VarBlock.Type.COND_3);
            break;
          }
          if (c == '(' && (prevIx == -1 || (chars[prevIx] == '?' || chars[prevIx] == ':'))) {
            mode = 1;
            spL += 1;
            if (block != null)
              blocks.add(block);
            block = new VarBlock(VarBlock.Type.INC);
//            block.append(c);
            break;
          }
          if (block == null)
            block = new VarBlock(VarBlock.Type.NORMAL);
          block.append(c);
          break;
        case 1:
          if (c == '(') {
            spL += 1;
          }
          if (c == ')') {
            spL -= 1;
            if (spL == 0) {
              mode = 0;
//              block.append(c);
              blocks.add(block);
              block = new VarBlock(VarBlock.Type.NORMAL);
              break;
            }
          }
          block.append(c);
          break;
        case 2:
          if (c == ':') {
            blocks.add(block);
            block = new VarBlock(VarBlock.Type.COND_3);
            break;
          }
          if (c == '(' && (prevIx == -1 || (chars[prevIx] == '?' || chars[prevIx] == ':'))) {
            mode = 1;
            spL += 1;
//            block.append(c);
            break;
          }

          block.append(c);
          break;
      }
    }
    blocks.add(block);
    if (spL != 0)
      throw new SyntaxException("语法错误 -> {0}", ast);
    return blocks;
  }

  private Object parseVarValueInc(SPM spm, Ast ast, StoveConfig config, List<VarBlock> blocks, Map<String, ?> attr) {
    Object _val = null;
    VarBlock first = blocks.get(0);
    switch (first.type()) {
      case INC:
        List<VarBlock> blocks0 = this.analysis(ast, first.code());
        VarBlock block0 = blocks0.get(0);
        switch (block0.type()) {
          case NORMAL:
            _val = this.parseVarValueNormal(spm, ast, config, block0.code(), attr);
            break;
          case COND_3:
            _val = this.parseVarValueCond3(spm, ast, config, blocks0, attr);
            break;
          case INC:
            _val = this.parseVarValueInc(spm, ast, config, blocks0, attr);
            break;
        }
        break;
      case COND_3:
        _val = this.parseVarValueCond3(spm, ast, config, blocks, attr);
        break;
      case NORMAL:
        _val = this.parseVarValueNormal(spm, ast, config, first.code(), attr);
        break;
    }
    if (blocks.size() == 1)
      return _val;
    if (_val != null)
      return _val;

    VarBlock block = blocks.get(1);

    String text = block.code(),
      def = null,
      var = null;
    int dix = text.indexOf("!");
    if (dix != -1) {
      def = text.substring(dix + 1);
      var = text.substring(0, dix);
    }
    Pipeline pipeline = Pipeline.parse(var, config.tokenPipeline());
    if (Is.not().empty(pipeline.pipelines()))
      _val = this.parsePipeline(spm, config, ast, _val, pipeline.pipelines());
    return _val == null ? def : _val;
  }

  private Object parsePipeline(SPM spm, StoveConfig config, Ast ast, Object val, String[] pipelines) {

//    // todo pipeline
//    for (String pipe : pipelines) {
//      StoveCommand command = spm.command(pipe);
//      if (command == null)
//        throw new CommandNotFoundException("在管道中未发现命令 => {0}", pipe);
//      StoveResult sret = command.execute(config, EnoaValue.with(val), ast);
//      val = sret.value();
//    }
    return val;
  }

  private Object parseVarValueCond3(SPM spm, Ast ast, StoveConfig config, List<VarBlock> blocks, Map<String, ?> attr) {
    if (blocks.size() < 3)
      throw new SyntaxException("错误的三元表达式 => {}", ast.code());
    VarBlock cond = blocks.get(0),
      _ret0 = blocks.get(1),
      _ret1 = blocks.get(2);

    Object _val0 = this.cond3Value(spm, ast, config, cond, attr);
    Object _val1 = this.cond3Value(spm, ast, config, _ret0, attr);
    Object _val2 = this.cond3Value(spm, ast, config, _ret1, attr);

    return ConvertKit.bool(String.valueOf(_val0)) ? _val1 : _val2;
  }

  private Object cond3Value(SPM spm, Ast ast, StoveConfig config, VarBlock block, Map<String, ?> attr) {
    Object _val = null;
    List<VarBlock> blocks0 = this.analysis(ast, block.code());
    if (Is.empty(blocks0))
      return null;

    VarBlock bck0 = blocks0.get(0);
    switch (bck0.type()) {
      case NORMAL:
        _val = this.parseVarValueNormal(spm, ast, config, bck0.code(), attr);
        break;
      case COND_3:
        _val = this.parseVarValueCond3(spm, ast, config, blocks0, attr);
        break;
      case INC:
        break;
    }
    return _val;
  }

  /**
   * 正常变量取值
   *
   * @param text 变量
   * @return Object
   */
  private Object parseVarValueNormal(SPM spm, Ast ast, StoveConfig config, String text, Map<String, ?> attr) {
    String var = text, def = null;
    int dix = text.indexOf("!");
    if (dix != -1) {
      def = text.substring(dix + 1);
      var = text.substring(0, dix);
    }
    Pipeline pipeline = Pipeline.parse(var, config.tokenPipeline());
    Object _val = attr.get(pipeline.mainly());
    if (_val != null)
      return _val;
    String[] mais = pipeline.mainly().split("\\.");

    for (int i = 0; i < mais.length; i++) {
      String item = mais[i];
      if (i == 0) {
        if (VarValueArray.instance().isArrVar(ast, item)) {
          _val = VarValueArray.instance().parseArrayValueFromAttr(ast, item, attr);
          continue;
        }
        _val = attr.get(item);
        continue;
      }

      if (_val == null)
        return def;

      if (VarValueArray.instance().isArrVar(ast, item)) {
        _val = VarValueArray.instance().parseArrayValueFromObject(ast, item, _val, attr);
        continue;
      }
      _val = VarValueObject.instance().parseObjectValue(ast, item, _val, attr);
    }
    _val = this.parsePipeline(spm, config, ast, _val, pipeline.pipelines());
    return _val == null ? def : _val;
  }

}
