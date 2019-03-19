
# EPM & USER

Enoa 的组件中, 基本都会提供 `epm` 以及 `use` 的静态方法, 用于组件的初始化. 并且大部分组件都支持多个实例, 提供一个不同的名称用于获取不同的实例. 通过 `epm` 方法进行初始化, `use` 方法进行使用.

e.g.

```java
// install redis instance with name is 'main'
Redis.epm().install("localhost", 6379, new JdkSerializeProvider());

// install redis instance with name is 'other'
Redis.epm().install("other", "localhost", 6379, new JdkSerializeProvider());

// use redis instance with name is 'main'
Redis.set("key", "value");
Redis.use().set("key", "value");
Redis.use("main").set("key", "value");

// use redis instance with name is 'other'
String value = Redis.use("other").get("key");
```

## 声明

并非所有的情况下都需要使用 EPM 进行初始化, 特别是第三方库加入时, 通常 EPM 不会允许有相同的名称存在, 如果有特殊情况不做检查, 那么多次 install 之后, 相同名称的实例会变成最后一个, 造成程序中不可被察觉的错误.
因此某些情况下使用相应组件提供的接口即可 (一个案例是 Redis 组件中使用了 Serialization 组件, 但是并没有进行 EPM 设定. 而只是作为配置将 Serialization 放入 Redis 中).


