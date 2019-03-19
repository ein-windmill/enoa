

# EoxConfig

EoxConfig 是 Repeater 啟動的配置專案, 如果只是簡單的測試 Repeater 那完全可以不用進行配置, 使用預設的配置即可. 但是對其進行配置會最佳化的使用 Repeater.

下表中列出 Repeater 所支援的配置:


| Name          | Default        | Description |
| ------------- | -------------- | ----------- |
| debug         | false          | 是否除錯模式  |
| context       | /              | 上下文路徑   |
| charset       | UTF-8          | 字元編碼     |
| info          | true           | 是否列印請求日誌 |
| infoUseLog    | false          | 請求日誌列印是否使用日誌元件 |
| soTimeout     | 5000ms         | 請求超時時間 |
| tmp           | java.io.tmpdir | 臨時檔案儲存位置 |
| maxUploadSize | 2MB            | 上傳檔案最大長度 |
| ssl           | false          | 是否開啟 ssl |
| other         |                | 其他設定 |
| holdFile      | false          | 保持上傳檔案在記憶體 |

建立一個配置

```java
EoxConfig config = new EoxConfig.Builder()
  .debug(true) // open debug mode
  .info(true) // print request info
  .infoUseLog(false) // not use Log.debug()
  .context("/example") // set context path is '/example'
  .tmp(PathKit.debugPath().resolve("tmp")) // set tmp dir to projectdir/tmp (only debug mode)
  .build();
```

