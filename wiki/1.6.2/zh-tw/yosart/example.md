

# Example

## Simple

```java
Yosart.createServer()
  .handle(Method.GET, "/", (req) -> Resp.with(req).render("yosart").end())
  .listen();
```

## Anost

```java
AnostExt anost = new AnostExt();
AnostHookMgr mgr = anost.hookMgr();
mgr.unload(GlobalHook.class)
  .unload(ManagerHook.class, "/manager")
  .unload(PreHook.class, AnostHookMgr.Mode.REGEX, "/manager/pre_*");

Yosart.createServer()
  .ext(anost)
  .handle(Method.GET, "/", (req) -> Resp.with(req).render("yosart").end())
  .listen(9001);
```

## More

[example-yosart](https://github.com/fewensa/enoa/tree/master/enoa-example/example-yosart)


