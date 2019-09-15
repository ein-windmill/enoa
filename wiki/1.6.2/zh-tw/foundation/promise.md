


# Promise

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-promise</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

## 說明

Promise 是 Enoa 中一個比較特別的元件, 其作用和 Javascript 中的 Promise 相似. 為了提供更直觀的非同步呼叫語法. Promise 通常不會被單獨使用, 而是為其他專案提供非同步 Promise 支援. 例如 [Http Client](#Http-Client).

在說明 Promise 之前, 我們先看看 Javascript 的 Promise, 如果你本來就瞭解那最好了. 因為於其非常相似.
Javascript 中 Promise 的一個代表的庫是 [Fetch](https://github.github.io/fetch/ "Fetch").
一個富有代表性的 Fetch 請求案例.

```javascript
// request a url
fetch('https://httpbin.org/get')
  // respoonse to json
  .then(resp => resp.json())
  // print json
  .then(ret => console.log('response: ', ret))
  // catch exception
  .catch(e => console.log('parsing failed', ex))
  // finally block
  .finally(() => console.log('finally'));
```

雖然這篇文章不是想要告訴你 Fetch 怎麼用, 但是, 如果能理解 Fetch 的用法, 對 Promise 的理解會很有幫助, 就簡單的來分析下 Fetch 的用法吧.

這段程式碼共 5 行, 第一行 `fetch('https://httpbin.org/get')` 傳送一個請求, 接下來有兩個 `.then`, 第一個 then 將請求返回的 `resp` 轉換為 json 資料, 表明該請求返回的資料是 json 結構, 第二個 then 列印這個 json, 接下來是 catch, 以及 finally, 簡單來說就是 catch 會捕捉請求以及 then 中的程式碼執行錯誤丟擲的 Exception, 並進行列印, 如果沒有丟擲將不會被執行, finally 的作用和 try 語句塊的 finally 的作用相同, 無論執行成功或失敗, 最終都會執行的語句.

好, 相信你已經知道 Fetch 怎麼用, 那麼來看看 Enoa 提供的 Http Client 的用法, 當然這裡只是提出用法, 具體分析可見 [Http Client](#Http-Client).

```java
Http.request("https://httpbin.org/get")
  .method(HttpMethod.GET)
  .enqueue()
  .then(HttpResponse::body)
  .execute(System.out::println)
  .capture(System.err::println)
  .always(() -> System.out.println("finally"));
```

先不要執著於這段程式碼, 只需要知道, 這段程式碼於 Fetch 的目的相同, 為了更直觀的體現出非同步請求的程式碼實現.

事實上, Promise 出現的目的就是為了解決非同步操作上的回撥地獄問題, 其在 Javascript 中已經表現的很出色. 當然在 Java 中也是可以實現的.
那麼我們來看看 Enoa 中 Promise 應該怎麼用.

Promise 提供了幾種不同介面, 根據合適的情況選擇, 當然也可以自己進行擴充套件.


## Builder

![Promise Builder](https://raw.githubusercontent.com/iaceob/gallery/master/enoa/promise-builder.svg?sanitize=true)

- EoPromise
  - capture
  - always
- DonePromise
  - capture
  - always
  - done
- DoneArgPromise
  - capture
  - always
  - done
- ThenPromise
  - 下個版本開放

## Arguments

![Promise Arguments](https://raw.githubusercontent.com/iaceob/gallery/master/enoa/promise-args.svg?sanitize=true)



## 使用

要實現 Promise 的功能, 在上方圖表中列出的 Builder 以及 Arguments 介面進行組合即可. 這裡實現一個簡單的 DonePromise:

```java
public class Writer {

  private static ExecutorService ENQUEUE = PromiseBuilder
    .executor()
    .enqueue("DonePromise Enqueue Dispatcher");

  public static DonePromise write(EnoaBinary binary) {
    EPDonePromiseBuilder builder = PromiseBuilder.done();
    ENQUEUE.execute(() -> {
      try {
        FileKit.write(Paths.get("/tmp/test.txt"), binary.bytebuffer());

        if (Is.not().empty(builder.dones())) {
          for (PromiseVoid done : builder.dones()) {
            done.execute();
          }
        }
      } catch (Exception e) {
        if (Is.not().empty(builder.captures())) {
          for (PromiseCapture capture : builder.captures()) {
            capture.execute(e);
          }
        }
      } finally {
        if (builder.always() != null)
          builder.always().execute();
      }
    });
    return builder.build();
  }

}
```

呼叫

```java
Writer.write(EnoaBinary.create("Hello World".getBytes()))
  .done(() -> System.out.println("Write file done."))
  .done(() -> System.out.println("And do other thing."))
  .capture(e -> System.err.println(e.getMessage()))
  .capture(e -> System.err.println(ThrowableKit.string(e)))
  .always(() -> System.out.println("finally"));
```

這樣, 我們就完成了一個返回 Promise 的非同步檔案寫入方法.

實現 Promise 的關鍵點在於 Builder 中, 對 Arguments 的不同型別組裝, 以及方法中如何呼叫, Promise 包中提供的幾種 Builder 僅僅是比較普遍的運用情況, 如果有特殊的需求, 你可以自己去實現一個 Builder 來建立 Promise, 一個比較完善的自定義 Promise 案例是 HttpPromise

[HttpPromise](https://github.com/fewensa/enoa/blob/master/enoa-http/src/main/java/io/enoa/http/protocol/HttpPromise.java "HttpPromise.java")
[HttpHelper implement](https://github.com/fewensa/enoa/tree/master/enoa-http/src/main/java/io/enoa/http/provider/httphelper/async)

HttpPromise 提供了很多有趣的用法, 具體用法參見 [Http Client](#Http-Client)

