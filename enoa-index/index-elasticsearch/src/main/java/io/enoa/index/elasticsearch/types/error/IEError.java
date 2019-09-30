package io.enoa.index.elasticsearch.types.error;

import java.util.Collections;
import java.util.List;

public interface IEError<I> {

  default Integer getTook() {
    return 0;
  }

  default EError getError() {
    return null;
  }

  default Integer getStatus() {
    return 0;
  }

  default List<I> getItems() {
    return Collections.emptyList();
  }

  default boolean esok() {
    return this.getError() == null;
  }

}
