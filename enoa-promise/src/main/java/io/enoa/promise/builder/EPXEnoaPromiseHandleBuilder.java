package io.enoa.promise.builder;

import io.enoa.promise.arg.*;

import java.util.List;

public class EPXEnoaPromiseHandleBuilder {

  private static class Holder {
    private static final EPXEnoaPromiseHandleBuilder INSTANCE = new EPXEnoaPromiseHandleBuilder();
  }

  static EPXEnoaPromiseHandleBuilder instance() {
    return Holder.INSTANCE;
  }

  private EPXEnoaPromiseHandleBuilder() {
  }


  public void handleCapture(EPEoPromiseBuilder builder, Throwable throwable) {
    builder.captures().forEach(capture -> capture.execute(throwable));
    if (builder.always() != null)
      builder.always().execute();
  }

  public void handleCapture(EPAssetPromiseBuilder builder, Throwable throwable) {
    this.handleCapture((EPEoPromiseBuilder) builder, throwable);
  }

  public void handleCapture(EPDoneArgPromiseBuilder builder, Throwable throwable) {
    this.handleCapture((EPEoPromiseBuilder) builder, throwable);
  }

  public void handleCapture(EPDonePromiseBuilder builder, Throwable throwable) {
    this.handleCapture((EPEoPromiseBuilder) builder, throwable);
  }

  public void handleCapture(EPThenPromiseBuilder builder, Throwable throwable) {
    this.handleCapture((EPEoPromiseBuilder) builder, throwable);
  }

  public <T> void handleDoneArg(EPDoneArgPromiseBuilder<T> builder, T result) {
    builder.dones().forEach(done -> done.execute(result));
    if (builder.always() != null)
      builder.always().execute();
  }

  public void handleDone(EPDonePromiseBuilder builder) {
    builder.dones().forEach(PromiseVoid::execute);
    if (builder.always() != null)
      builder.always().execute();
  }

  public void handleThen(EPThenPromiseBuilder builder, Object result) {
    try {
      List<PromiseThen> thens = builder.thens();
      for (PromiseThen then : thens) {
        result = then.execute(result);
      }

      List<PromiseArg> executers = builder.executers();
      for (PromiseArg executer : executers) {
        executer.execute(result);
      }
    } catch (Exception e) {
      builder.captures().forEach(capture -> capture.execute(e));
    } finally {
      if (builder.always() != null)
        builder.always().execute();
    }
  }

  public void handleAsset(EPAssetPromiseBuilder builder, Object result) {
    try {
      List<PromiseBool> assets = builder.assets();
      boolean pass = Boolean.TRUE;
      for (PromiseBool asset : assets) {
        if (asset.execute(result))
          continue;
        pass = Boolean.FALSE;
        break;
      }
      if (!pass) {
        List<PromiseArg> failthrows = builder.failthrows();
        for (PromiseArg failthrow : failthrows) {
          failthrow.execute(result);
        }
        return;
      }

      List<PromiseThen> thens = builder.thens();
      for (PromiseThen then : thens) {
        result = then.execute(result);
      }

      List<PromiseArg> executes = builder.executers();
      for (PromiseArg execute : executes) {
        execute.execute(result);
      }
    } catch (Exception e) {
      List<PromiseCapture> captures = builder.captures();
      for (PromiseCapture capture : captures) {
        capture.execute(e);
      }
    } finally {
      if (builder.always() != null)
        builder.always().execute();
    }
  }


}
