package io.enoa.toolkit.text;

import org.junit.Test;

public class TextReaderTest {


  @Test
  public void testReader() {
    TextReader reader = TextReader.with("abc\ndef");
    while (reader.hasNext()) {
      char ch = reader.next();
      System.out.println(ch);
      System.out.println(reader);
      if (ch == '\n') {
        reader.back();
        System.out.println(reader.peek());
        System.out.println(reader);
        reader.next();
      }
    }

  }

}
