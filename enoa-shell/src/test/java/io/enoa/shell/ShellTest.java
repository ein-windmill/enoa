package io.enoa.shell;

import io.enoa.chunk.Chunk;
import io.enoa.shell.ret.ShellResult;
import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.path.PathKit;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ShellTest {

  @Test
  public void testExec() {
    String[] commands = {"cmd.exe", "/C", "dir"};
//    commands = new String[]{"cmd.exe", "/c", "git", "clone", "https://gitee.com/rushmore/zbus.git", "_tmp"};
    commands = new String[]{"cmd.exe", "/c", "curl", "-v", "cip.cc"};
//    commands = new String[]{"cmd.exe", "/c", "mvn", "-X", "clean", "compile", "-Dmaven.test=true"};
//    commands = new String[]{"cmd.exe", "/c", "echo", "%WORK_PATH%"};

    ShellResult result = Shell.actuator()
      .command(commands)
      .chunk(Chunk.string(System.out::println, EoConst.CHARSET))
      .charset(EoConst.CHARSET)
      .env("WORK_PATH", PathKit.debugPath().toString())
      .emit();
    System.out.println(result.exitvalue());
    System.out.println(result.string());

  }

}
