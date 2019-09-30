package io.enoa.index.elasticsearch;

public interface IEs {


  default String emit() {
    return this.emit(EParser.json());
  }

  <T> T emit(EParser<T> parser);

}
