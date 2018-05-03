package io.enoa.example.yosart.anost.hook;

import io.enoa.repeater.http.Header;
import io.enoa.yosart.ext.anost.hook.HookException;
import io.enoa.yosart.ext.anost.hook.IHook;
import io.enoa.yosart.kernel.http.YoRequest;
import io.enoa.yosart.resp.Resp;

public class GlobalHook implements IHook {
  @Override
  public void hook(YoRequest request, Resp resp) throws HookException {
    resp.header(new Header("name", "111"));
  }
}
