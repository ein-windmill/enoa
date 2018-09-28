

# 其他

## session

在看完 Repeater 的內容後, 你可能會覺得是否少了 Session 的支援; 是的沒錯, Repeater 不提供 Session 的支援; Repeater 僅僅是 Web 伺服器的中繼器, 而 Session 屬於應用層的範疇, Repeater 並不參與, 雖然部分 Web 伺服器是支援 Session 的, 例如 Tomcat, Jetty, 要知道, 這只是部分. FastCGI, Netty 就不提供 Session 的支援, 因此也無法進行統一; Repeater 不提供 Session 的原因也在於此.
此外如果你理解 Session 的實際原理就會發現, 只要能獲取到 Cookie, 或者其他的方式存取 Key, 完全可以自己實現一個 Session 的機制.

