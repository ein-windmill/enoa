package io.enoa.index.elasticsearch.types.error;

public class EErrorResource {

  private String type;
  private String id;

  public String getType() {
    return type;
  }

  public EErrorResource setType(String type) {
    this.type = type;
    return this;
  }

  public String getId() {
    return id;
  }

  public EErrorResource setId(String id) {
    this.id = id;
    return this;
  }

  @Override
  public String toString() {
    return "EErrorResource{" +
      "type='" + type + '\'' +
      ", id='" + id + '\'' +
      '}';
  }
}
