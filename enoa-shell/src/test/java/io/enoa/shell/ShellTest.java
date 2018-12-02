package io.enoa.shell;

import io.enoa.chunk.Chunk;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.charset.Charset;

@Ignore
public class ShellTest {

  @Before
  public void ibe() {
    Shell.epm().install(new EnoaShell());
  }

  @Test
  public void testExec() {
    Shell.use().command("cmd.exe /C java -version", Chunk.string(System.out::println, Charset.forName("BIG5")));
    Shell.use().command("cmd.exe /C dir", Chunk.string(System.out::println, Charset.forName("BIG5")));
  }

}
