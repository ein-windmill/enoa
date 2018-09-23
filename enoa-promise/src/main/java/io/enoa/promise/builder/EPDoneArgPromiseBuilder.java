package io.enoa.promise.builder;

import io.enoa.promise.DoneArgPromise;
import io.enoa.promise.EoPromise;
import io.enoa.promise.arg.PromiseArg;
import io.enoa.promise.arg.PromiseCapture;
import io.enoa.promise.arg.PromiseVoid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class EPDoneArgPromiseBuilder<PARA> extends EPEoPromiseBuilder {

  private EPEoPromiseBuilder oe;

  EPDoneArgPromiseBuilder() {
    this.oe = new EPEoPromiseBuilder();
  }

  private List<PromiseArg<PARA>> dones;

  public List<PromiseArg<PARA>> dones() {
    return this.dones == null ? Collections.emptyList() : this.dones;
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
  public DoneArgPromise<PARA> build() {
    EoPromise promise = this.oe.build();
    return new DoneArgPromise<PARA>() {
      @Override
      public DoneArgPromise<PARA> done(PromiseArg<PARA> done0) {
        if (EPDoneArgPromiseBuilder.this.dones == null)
          EPDoneArgPromiseBuilder.this.dones = new LinkedList<>();
        EPDoneArgPromiseBuilder.this.dones.add(done0);
        return this;
      }

      @Override
      public DoneArgPromise<PARA> capture(PromiseCapture capture0) {
        promise.capture(capture0);
        return this;
      }

      @Override
      public void always(PromiseVoid always0) {
        promise.always(always0);
      }
    };
  }


}
