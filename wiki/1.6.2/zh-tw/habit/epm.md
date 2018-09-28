
# EPM & USER

Enoa 的元件中, 基本都會提供 `epm` 以及 `use` 的靜態方法, 用於元件的初始化. 並且大部分元件都支援多個例項, 提供一個不同的名稱用於獲取不同的例項. 通過 `epm` 方法進行初始化, `use` 方法進行使用.

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

:::warning
特別說明, 並非所有的情況下都需要使用 EPM 進行初始化, 特別是第三方庫加入時, 通常 EPM 不會允許有相同的名稱存在, 如果有特殊情況不做檢查, 那麼多次 install 之後, 相同名稱的例項會變成最後一個, 造成程式中不可被察覺的錯誤.
因此某些情況下使用相應元件提供的介面即可 (一個案例是 Redis 元件中使用了 Serialization 元件, 但是並沒有進行 EPM 設定. 而只是作為配置將 Serialization 放入 Redis 中).
:::

