package io.enoa.http.provider.httphelper.conn.ssl;

public enum TLSv {

  V_1_1("TLSv1.1"),
  V_1_2("TLSv1.2"),
  V_1_3("TLSv1.3"),

  //
  ;

  private final String val;

  TLSv(String val) {
    this.val = val;
  }

  public String val() {
    return this.val;
  }

}
