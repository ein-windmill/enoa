package io.enoa.example.yosart.anost.thr;

import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Response;
import io.enoa.yosart.ext.anost.valid.ValidException;
import io.enoa.yosart.kernel.ext.YmExceptionExt;
import io.enoa.yosart.kernel.http.YoRequest;
import io.enoa.yosart.kit.yo.ResponseKit;
import io.enoa.yosart.resp.Renderer;
import io.enoa.yosart.resp.Resp;

public class AnostCatch implements YmExceptionExt {

  @Override
  public Response handle(YoRequest request, Resp resp, Throwable throwable) {
    Response response;
    if (throwable instanceof ValidException) {
      ValidException ve = (ValidException) throwable;
//      IMarkIx mark = (IMarkIx) ve.mark(); // convert your mark interface
      response = Renderer.with(request).renderText(ve.getMessage()).end();
      return resp == null ? response : ResponseKit.merge(response, resp.end());
    }
    response = Renderer.with(request).renderError(HttpStatus.INTERNAL_ERROR, throwable.getMessage(), throwable).end();
    return resp == null ? response : ResponseKit.merge(response, resp.end());
  }

  @Override
  public String name() {
    return "AnostCatch";
  }

  @Override
  public String version() {
    return "1";
  }
}
