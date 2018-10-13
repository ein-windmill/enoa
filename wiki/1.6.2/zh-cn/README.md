# Introduction


Enoa 是一个体积轻巧的 Java 框架, 鉴于当下的多数 Java 框架, 使用上相对较繁琐, 过多的配置文件, 过多繁琐的环境设定. 而 Enoa 则没有这些.

Enoa 包含了众多子项目, 从工具库到 Web 框架以及网关, 大数据搜索引擎的包装等等, 为了更简单的使用, Enoa 提供统一, 最简单的 API 接口.

但是比较可惜的是, 因为项目之间功能分得很细导致组合使用时内部依赖相对较多, 这是一种取舍, 是选择将所有功能组合在一个项目或者是将不同的功能区分开. 明显的分开将是一个不错的决定, 实际中并不一定会用到所有的功能, 仅仅是部分而已, Enoa 的所有模块都可以独立使用到其他任何系统中. 尽管如此, Enoa 依然保持着轻巧的体积, 简单易用的本质.

> > 忽略:
> 
> 事实上当你阅读了文章之后, 你可能会觉得 Enoa 很多余, 譬如 Json 我可以直接用 Jackson/Fastjson/Gson 就可以, 再如 Log 可以直接使用 Log4j/Logback 等等, 为什么用 Enoa 提供的方案了.
> 
> 是的, 就如你所想到的, 你完全可以直接使用, 而没必要通过 Enoa, 然而你可以看下面的一些案例.
> 
> ```java
> // Jackson
> ObjectMapper mapper = new ObjectMapper();
> try {
>   mapper.writeValueAsString(object);
> } catch (JsonProcessingException e) {
>   e.printStackTrace();
> }
> 
> // Gson
> Gson gson = new GsonBuilder().create();
> gson.toJson(object);
> 
> // Fastjson
> JSON.toJSONString(object);
> ```
> 
> 不同的框架实现的方式并不相同, 这些就是差异. 当然, 你会说在工程开发中, 只会用一种, 不会产生混淆的问题.
> 没错, 但是不同的框架, 都有相应的学习成本, 如果未能完全掌握可能会在使用中发生诸多问题, 使用 Enoa 则不必担心, 因为不管你使用的是哪一个, 这些都由 Enoa 去实现, 你只需要知道 Enoa 的接口是如何使用, 而最终的实现由 Enoa 去驱动.
> 此外, 来看看这个.
> 
> ```java
> StringUtils.isNull(object)
> ```
> 
> 如果, 你的工程是以 Spring 为基础的项目, 你可以看一下, 你的 IDE 会给你多少个 `StringUtils`. 再想以下, 如果是多人研发的团队, 一整个项目里面到底用了多少个不同包下面的 `StringUtils`.
> 
> 以上, 只是 Enoa 存在的一部分目的. 另外一个重要的也是直接目的是为了简化开发流程.
> 至于怎么简化, 现在请你的脑海里想象一下, 你最熟悉的 Java 语言框架中创建一个 Web 服务的工程结构要怎么搭建; 然后和下面的代码进行对比.
> 
> ```java
> Yosart.createServer()
>   .handle(Method.GET, "/", (req) -> Resp.with(req).render("Hello world").end())
>   .listen();
> ```
> 
> [example-yosart-simple](https://github.com/fewensa/enoa/blob/master/enoa-example/example-yosart-simple/src/main/java/io/enoa/example/yosart/simple/YosartSimpleBoot.java "example-yosart-simple")
> 
> 好, 到此为止, 相信你已经发现了 Enoa 的诸多便利之处.
> 
> Enoa 的目的很简单: 消除不同框架之间的歧义, 统一接口实现, 书写简单优美的代码.


