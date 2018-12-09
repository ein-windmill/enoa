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
//    Shell.use().command("cmd.exe /C java -version", Chunk.string(System.out::println, Charset.forName("BIG5")));
    String[] commands = {"cmd.exe", "/C", "dir"};
//    commands = new String[]{"cmd.exe", "/c", "git", "clone", "https://gitee.com/rushmore/zbus.git", "_tmp"};
    commands = new String[]{"cmd.exe", "/c", "curl", "-v", "cip.cc"};
//    commands = new String[]{"cmd.exe", "/c", "mvn", "-X", "clean", "compile", "-Dmaven.test=true"};

    Shell.use().command(commands, Chunk.string(text -> {
      System.out.println(text);
    }, Charset.forName("BIG5")));

  }

}
