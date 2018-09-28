

# before && after

before 以及 after 方法, 用于在启动前后的回调方法. 二者调用的时机分别是:

- before
  会在插件以及扩展启动之前调用
- after
  插件扩展启动完成之后调用


:::warning
因 before after 于启动扩展有重复的功能作用, 后续将会对此作出权衡. 目前是可用的状态.
:::

