package io.enoa.promise.builder;

import io.enoa.promise.DoneArgPromise;
import io.enoa.promise.EoPromise;
import io.enoa.promise.arg.PromiseArg;
import io.enoa.promise.arg.PromiseCapture;
import io.enoa.promise.arg.PromiseVoid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EGraenodPromiseBuilder extends EOePromiseBuilder {

  private EOePromiseBuilder oe;

  public EGraenodPromiseBuilder() {
    this.oe = new EOePromiseBuilder();
  }

  private List<PromiseArg> dones;


  public List<PromiseArg> dones() {
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
  public DoneArgPromise build() {
    EoPromise promise = oe.build();
    return new DoneArgPromise() {
      @Override
      public <T> DoneArgPromise done(PromiseArg<T> done0) {
        if (dones == null)
          dones = new ArrayList<>();
        dones.add(done0);
        return this;
      }

      @Override
      public DoneArgPromise capture(PromiseCapture capture0) {
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
