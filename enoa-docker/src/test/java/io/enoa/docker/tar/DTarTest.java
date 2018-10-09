package io.enoa.docker.tar;

import io.enoa.toolkit.binary.EnoaBinary;
import io.enoa.toolkit.file.FileKit;
import org.junit.Test;

import java.nio.file.Paths;

public class DTarTest {

  @Test
  public void testCvf() {
    EnoaBinary binary = DTar.cvf("Dockerfile", "content");
    System.out.println(1);
    FileKit.write(Paths.get("/tmp/Dockerfile.tgz"), binary.bytebuffer());
  }

}
