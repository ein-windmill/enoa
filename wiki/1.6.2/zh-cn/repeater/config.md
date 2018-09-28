

# EoxConfig

EoxConfig 是 Repeater 启动的配置项目, 如果只是简单的测试 Repeater 那完全可以不用进行配置, 使用默认的配置即可. 但是对其进行配置会最佳化的使用 Repeater.

下表中列出 Repeater 所支持的配置:


| Name          | Default        | Description |
| ------------- | -------------- | ----------- |
| debug         | false          | 是否调试模式  |
| context       | /              | 上下文路径   |
| charset       | UTF-8          | 字符编码     |
| info          | true           | 是否打印请求日志 |
| infoUseLog    | false          | 请求日志打印是否使用日志组件 |
| soTimeout     | 5000ms         | 请求超时时间 |
| tmp           | java.io.tmpdir | 临时文件存储位置 |
| maxUploadSize | 2MB            | 上传文件最大长度 |
| ssl           | false          | 是否开启 ssl |
| other         |                | 其他设置 |
| holdFile      | false          | 保持上传文件在内存 |

创建一个配置

```java
EoxConfig config = new EoxConfig.Builder()
  .debug(true) // open debug mode
  .info(true) // print request info
  .infoUseLog(false) // not use Log.debug()
  .context("/example") // set context path is '/example'
  .tmp(PathKit.debugPath().resolve("tmp")) // set tmp dir to projectdir/tmp (only debug mode)
  .build();
```

