

# [Yosart](https://github.com/fewensa/enoa/blob/master/enoa-yosart/src/main/java/io/enoa/yosart/Yosart.java "Yosart.java")

要说明 Yosart, 先从 Yosart 接口开始说起. 下面列出 Yosart 的所有方法:

## provider

Web 服务器提供者, 受支持的提供者可在此处查阅 [Provider](#Provider), 使用方式于 Repeater 相同.

## log

Yosart 所使用的 Log 框架. [Logger](#Logger)

## accessor

为 Repeater 提供访问器, Yosart 内部有一个默认的实现, 并且不要更改该实现, 如果你作出了更改, 那么 Yosart 的整个流程将会失效, 除非你有特殊的需求.

## rule

Repeater 文件上传后为文件重新命名的策略, 默认采用原始文件名名称.

## capture

Repeater 异常统一处理, 拦截 Repeater 的所有异常, Yosart 中有默认的实现, 通常无需更改, 而且 Yosart 自身也存在一个异常拦截系统.

## plugin

Yosart 插件体系, Yosart 启动插件方法

## ext

Yosart 扩展体系, Yosart 启动扩展方法.

## ssl

是否开启 SSL 支持, 支持与否参照你所使用的 Web 服务器在 Repeater 中是否支持.

## handle

路由表定义, 创建路由.

## name

项目名称, 默认 Yosart

## context

上下文路径, 将会传递给 Repeater, 默认 /

## config

Yosart 配置信息

## assets

静态资源设置

## before

Yosart 启动之前会执行的接口

## after

Yosart 启动之后会执行的接口

## listen

开启服务监听.

