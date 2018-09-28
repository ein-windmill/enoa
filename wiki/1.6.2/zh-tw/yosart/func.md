

# [Yosart](https://github.com/fewensa/enoa/blob/master/enoa-yosart/src/main/java/io/enoa/yosart/Yosart.java "Yosart.java")

要說明 Yosart, 先從 Yosart 介面開始說起. 下面列出 Yosart 的所有方法:

## provider

Web 伺服器提供者, 受支援的提供者可在此處查閱 [Provider](#Provider), 使用方式於 Repeater 相同.

## log

Yosart 所使用的 Log 框架. [Logger](#Logger)

## accessor

為 Repeater 提供訪問器, Yosart 內部有一個預設的實現, 並且不要更改該實現, 如果你作出了更改, 那麼 Yosart 的整個流程將會失效, 除非你有特殊的需求.

## rule

Repeater 檔案上傳後為檔案重新命名的策略, 預設採用原始檔名名稱.

## capture

Repeater 異常統一處理, 攔截 Repeater 的所有異常, Yosart 中有預設的實現, 通常無需更改, 而且 Yosart 自身也存在一個異常攔截系統.

## plugin

Yosart 外掛體系, Yosart 啟動外掛方法

## ext

Yosart 擴充套件體系, Yosart 啟動擴充套件方法.

## ssl

是否開啟 SSL 支援, 支援與否參照你所使用的 Web 伺服器在 Repeater 中是否支援.

## handle

路由表定義, 建立路由.

## name

專案名稱, 預設 Yosart

## context

上下文路徑, 將會傳遞給 Repeater, 預設 /

## config

Yosart 配置資訊

## assets

靜態資源設定

## before

Yosart 啟動之前會執行的介面

## after

Yosart 啟動之後會執行的介面

## listen

開啟服務監聽.

