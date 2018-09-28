

# Firetpl

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>stove-firetpl</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

Firetpl 是一個標記模板, 可以理解成是一個基於檔案或其他形式的 Map. 作用是用於將部分配置脫離出硬編碼, 統一化管理. 典型的應用場景是將 Sql 語句從 Java 程式碼中拿出來.

目前 Firetpl 僅有一個第三方實現, 基於 enjoy 模板; 後續將會提供一個預設的實現, 不依賴第三方. 無論是使用第三方還是後續的預設實現, 書寫的程式碼都是相同的, 因此先來看下 Firetpl 如何使用.

Firetpl 實質上, 屬於 Stove 專案中的一部分, Stove 是 Enoa 提供的模板框架, 目前完工的部分只有 Firetpl.

Firetpl 提供兩個介面

- Firetpl
- FireBody

Firetpl 介面是模板介面, FireBody 就是從 Firetpl 介面中獲取的結果介面.

## Enjoy Firetpl

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>stove-firetpl-enjoy</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

```java
Firetpl firetpl = new EnjoyFiretpl(PathKit.debugPath().resolve("src/test/resources/sqls"), "template.sql", true);
FireBody body = firetpl.render();
String tpl = body.tpl();
Object[] paras = body.paras();
```

Enjoy Firetpl 的一個重要的作用是用於 Sql 模板的管理, 具體指令如何使用可參照 Enjoy 的文件: [5.13 SQL 管理與動態生成](http://www.jfinal.com/doc/5-13), 至於配置則無需關心, 由 Firetpl 完成. 瞭解指令如何使用即可.


## 其他

由於 Firetpl 僅僅是工具嵌入到其他專案中, 因此暫未提供 epm 方法進行統一設定, 此外模板的形式也並非是檔案儲存, 只要符合 Firetpl 的介面定義即可.

