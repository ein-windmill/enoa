

# Template

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-template</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

Template 项目用于整合现有的很多模板框架, 受支持的包括

- beetl
- enjoy
- freemarker
- velocity

> 与其他组件相同, 需要使用哪个框架单独引入, Enoa 并没有将所有的框架打包在一起; Template 中有个例外, 考虑到 freemarker 以及 velocity 相对应用的较广泛, Template 中已内建其 Provider, 但是使用仍然需要引入 freemarker 以及 velocity 包.

### 声明

> 当前文档提供的代码不一定能运行, 具体参考 [example-template](https://github.com/fewensa/enoa/tree/master/enoa-example/example-template)


## EPM

Template 组件支持 epm() 方法, 同时支持 Template 多实例, 并且是多种不同框架的多实例.

```java
// install freemarker template with name 'main'
Template.epm().install(new FreemarkerProvider(),
  new FreemarkerConfig.Builder()
  .viewPath(PathKit.debugPath().resolve("src/test/tpl").toString())
  .build());
// install velocity template with name 'velocity'
Template.epm().install("velocity", new VelocityProvider(),
  new VelocityConfig.Builder()
  .viewPath(PathKit.debugPath().resolve("src/test/tpl").toString())
  .build());

// render template by main => freemarker
Template.use().template("/freemarker/tpl.html").render();
// render template by main => freemarker
Template.use("main").template("/freemarker/tpl.html").render();
// render template by velocity => velocity
Template.use("velocity").template("/velocity/tpl.html").render();
```



## freemarker

```xml
<dependency>
  <groupId>org.freemarker</groupId>
  <artifactId>freemarker</artifactId>
  <version>2.3.23</version>
</dependency>
```

```java
EnoaEngine engine = new FreemarkerProvider()
  .engine(new FreemarkerConfig.Builder()
  .viewPath(PathKit.debugPath().resolve("src/test/tpl").toString())
  .build());
String tpl = engine.template("/freemarker/tpl.html").render();
```

## velocity

```xml
<dependency>
  <groupId>org.apache.velocity</groupId>
  <artifactId>velocity</artifactId>
  <version>1.7</version>
</dependency>
```

```java
EnoaEngine engine = new VelocityProvider()
  .engine(new VelocityConfig.Builder()
  .viewPath(PathKit.debugPath().resolve("src/test/tpl").toString())
  .build());
String tpl = engine.template("/velocity/tpl.html").render();
```


## Beetl

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-template-beetl</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

```java
EnoaEngine engine = new BeetlProvider()
  .engine(new BeetlConfig.Builder()
  .viewPath(PathKit.debugPath().resolve("src/test/tpl").toString())
  .build());
String tpl = engine.template("/beetl/tpl.html").render();
```

## Enjoy

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-template-enjoy</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

```java
EnoaEngine engine = new EnjoyProvider()
  .engine(new EnjoyConfig.Builder()
  .viewPath(PathKit.debugPath().resolve("src/test/tpl").toString())
  .build());
String tpl = engine.template("/enjoy/tpl.html").render();
```

