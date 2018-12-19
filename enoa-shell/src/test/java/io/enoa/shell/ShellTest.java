package io.enoa.shell;

import io.enoa.chunk.Chunk;
import io.enoa.shell.ret.ShellResult;
import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.path.PathKit;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

@Ignore
public class ShellTest {

  @Test
  public void testExec() {
    String[] commands = {"cmd.exe", "/C", "dir"};
//    commands = new String[]{"cmd.exe", "/c", "git", "clone", "https://gitee.com/rushmore/zbus.git", "_tmp"};
    commands = new String[]{"cmd.exe", "/c", "curl", "-v", "cip.cc"};
//    commands = new String[]{"cmd.exe", "/c", "mvn", "-X", "clean", "compile", "-Dmaven.test=true"};
//    commands = new String[]{"cmd.exe", "/c", "echo", "%WORK_PATH%"};


    ShellResult result0 = Shell.actuator()
      .command(commands)
      .chunk(Chunk.string((text, linebreak) -> System.out.println(text), EoConst.CHARSET))
      .charset(EoConst.CHARSET)
      .directory(PathKit.debugPath())
      .env("WORK_PATH", PathKit.debugPath().toString())
      .emit();
    System.out.println(result0.exitvalue());
    System.out.println(result0.string());


    Shell.actuator()
      .command(commands)
      .chunk(Chunk.string((text, linebreak) -> System.out.println(text), EoConst.CHARSET))
      .charset(EoConst.CHARSET)
      .directory(PathKit.debugPath())
      .env("WORK_PATH", PathKit.debugPath().toString())
      .enqueue()
      .done(ret -> System.out.println(ret.string()))
      .capture(Throwable::printStackTrace)
      .always(() -> System.out.println("always"));


    try {
      TimeUnit.SECONDS.sleep(3L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
