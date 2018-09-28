

# 其他

## session

在看完 Repeater 的内容后, 你可能会觉得是否少了 Session 的支持; 是的没错, Repeater 不提供 Session 的支持; Repeater 仅仅是 Web 服务器的中继器, 而 Session 属于应用层的范畴, Repeater 并不参与, 虽然部分 Web 服务器是支持 Session 的, 例如 Tomcat, Jetty, 要知道, 这只是部分. FastCGI, Netty 就不提供 Session 的支持, 因此也无法进行统一; Repeater 不提供 Session 的原因也在于此.
此外如果你理解 Session 的实际原理就会发现, 只要能获取到 Cookie, 或者其他的方式存取 Key, 完全可以自己实现一个 Session 的机制.

