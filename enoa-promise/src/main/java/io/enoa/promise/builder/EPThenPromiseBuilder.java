package io.enoa.promise.builder;

import io.enoa.promise.EoPromise;
import io.enoa.promise.ThenPromise;
import io.enoa.promise.arg.PromiseArg;
import io.enoa.promise.arg.PromiseCapture;
import io.enoa.promise.arg.PromiseThen;
import io.enoa.promise.arg.PromiseVoid;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class EPThenPromiseBuilder extends EPEoPromiseBuilder {

  private EPEoPromiseBuilder oe;

  private List<PromiseThen> thens;
  private List<PromiseArg> executes;

  EPThenPromiseBuilder() {
    this.oe = new EPEoPromiseBuilder();
  }

  public List<PromiseThen> thens() {
    return this.thens == null ? Collections.emptyList() : this.thens;
  }

  public List<PromiseArg> executes() {
    return this.executes == null ? Collections.emptyList() : this.executes;
  }

  @Override
  public List<PromiseCapture> captures() {
    return this.oe.captures();
  }

  @Override
  public PromiseVoid always() {
    return this.oe.always();
  }

  @Override
  public ThenPromise build() {
    EoPromise promise = this.oe.build();
    return new ThenPromise() {
      @Override
      public <R, P> ThenPromise then(PromiseThen<R, P> then) {
        if (thens == null)
          thens = new LinkedList<>();
        thens.add(then);
        return this;
      }

      @Override
      public <T> ThenPromise execute(PromiseArg<T> arg) {
        if (executes == null)
          executes = new LinkedList<>();
        executes.add(arg);
        return this;
      }

      @Override
      public ThenPromise capture(PromiseCapture capture) {
        promise.capture(capture);
        return this;
      }

      @Override
      public void always(PromiseVoid always) {
        promise.always(always);
      }
    };
  }

}
