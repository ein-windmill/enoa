package io.enoa.promise;

import io.enoa.promise.arg.PromiseArg;
import io.enoa.promise.arg.PromiseThen;

public interface ThenPromise extends EoPromise<ThenPromise> {

  <R, P> ThenPromise then(PromiseThen<R, P> then);

  <T> ThenPromise execute(PromiseArg<T> arg);

}
