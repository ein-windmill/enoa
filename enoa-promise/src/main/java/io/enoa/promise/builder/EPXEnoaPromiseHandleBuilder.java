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

  public void handleAlways(EPEoPromiseBuilder builder) {
    if (builder.always() == null)
      return;
    try {
      builder.always().execute();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void handleAlways(EPAssetPromiseBuilder builder) {
    this.handleAlways((EPEoPromiseBuilder) builder);
  }

  public void handleAlways(EPDoneArgPromiseBuilder builder) {
    this.handleAlways((EPEoPromiseBuilder) builder);
  }

  public void handleAlways(EPDonePromiseBuilder builder) {
    this.handleAlways((EPEoPromiseBuilder) builder);
  }

  public void handleAlways(EPThenPromiseBuilder builder) {
    this.handleAlways((EPEoPromiseBuilder) builder);
  }

  public void handleCapture(EPEoPromiseBuilder builder, Throwable throwable) {
    for (PromiseCapture capture : builder.captures()) {
      try {
        capture.execute(throwable);
      } catch (Exception e) {
        e.printStackTrace();
        break;
      }
    }
    handleAlways(builder);
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
    boolean needalways = true;
    for (PromiseArg<T> done : builder.dones()) {
      try {
        done.execute(result);
      } catch (Exception e) {
        this.handleCapture(builder, e);
        needalways = false;
        break;
      }
    }
    if (!needalways)
      return;
    this.handleAlways(builder);
  }

  public void handleDone(EPDonePromiseBuilder builder) {
    boolean needalways = true;
    for (PromiseVoid done : builder.dones()) {
      try {
        done.execute();
      } catch (Exception e) {
        this.handleCapture(builder, e);
        needalways = false;
        break;
      }
    }
    if (!needalways)
      return;
    this.handleAlways(builder);
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
      for (PromiseCapture capture : builder.captures()) {
        try {
          capture.execute(e);
        } catch (Exception ex) {
          ex.printStackTrace();
          break;
        }
      }
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
        try {
          capture.execute(e);
        } catch (Exception ex) {
          ex.printStackTrace();
          break;
        }
      }
    } finally {
      if (builder.always() != null)
        builder.always().execute();
    }
  }


}
