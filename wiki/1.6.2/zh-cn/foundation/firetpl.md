

# Firetpl

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>stove-firetpl</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

Firetpl 是一个标记模板, 可以理解成是一个基于文件或其他形式的 Map. 作用是用于将部分配置脱离出硬编码, 统一化管理. 典型的应用场景是将 Sql 语句从 Java 代码中拿出来.

目前 Firetpl 仅有一个第三方实现, 基于 enjoy 模板; 后续将会提供一个默认的实现, 不依赖第三方. 无论是使用第三方还是后续的默认实现, 书写的代码都是相同的, 因此先来看下 Firetpl 如何使用.

Firetpl 实质上, 属于 Stove 项目中的一部分, Stove 是 Enoa 提供的模板框架, 目前完工的部分只有 Firetpl.

Firetpl 提供两个接口

- Firetpl
- FireBody

Firetpl 接口是模板接口, FireBody 就是从 Firetpl 接口中获取的结果接口.

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

Enjoy Firetpl 的一个重要的作用是用于 Sql 模板的管理, 具体指令如何使用可参照 Enjoy 的文档: [5.13 SQL 管理与动态生成](http://www.jfinal.com/doc/5-13), 至于配置则无需关心, 由 Firetpl 完成. 了解指令如何使用即可.


## 其他

由于 Firetpl 仅仅是工具嵌入到其他项目中, 因此暂未提供 epm 方法进行统一设置, 此外模板的形式也并非是文件存储, 只要符合 Firetpl 的接口定义即可.

