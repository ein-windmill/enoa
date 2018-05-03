package io.enoa.trydb.tsql.generate;

import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.trydb.dialect.IDialect;

class TSqlCorrect {

  private TSqlCorrect() {

  }

  static String columnName(IDialect dialect, String columnName) {
    if (dialect == null)
      return columnName;
    if (!CollectionKit.contains(dialect.keywords(), columnName))
      return columnName;
    return TextKit.union(dialect.identifierQuoteStringLeft(), columnName, dialect.identifierQuoteStringRight());
  }

  static String safeValue(Object value) {
    if (value == null)
      return null;
    if (value instanceof Number)
      return String.valueOf(value);
    String val = String.valueOf(value);
    val = val.replace("'", "\\'");
    return TextKit.union("'", val, "'");
  }

}
