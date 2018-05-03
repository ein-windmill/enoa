/*
 * Copyright (c) 2018, enoa (ein.windmill@outlook.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.enoa.log.kit;

import org.junit.Test;

/**
 * Created by ein on 2017/8/14.
 */
public class LogKitTest {

  @Test
  public void testLog() {

    LogKit.debug("Debug log");
    LogKit.info("Info log");
    LogKit.warn("Warn log");
    LogKit.error("Error log");
    LogKit.debug(LogKit.name());
    LogKit.use("TEST");
    LogKit.debug("============");
    LogKit.debug("Debug log");
    LogKit.info("Info log");
    LogKit.warn("Warn log");
    LogKit.error("Error log");
    LogKit.debug(LogKit.name());
    LogKit.use(LogKitTest.class);
    LogKit.debug("============");
    LogKit.debug("Debug log");
    LogKit.info("Info log");
    LogKit.warn("Warn log");
    LogKit.error("Error log");
    LogKit.debug(LogKit.name());


//    EnoaLogMgr.instance().loggerFactory(new JdkLogProvider());
//    LogKit.debug("TEST JDK");
//
//    EnoaLogMgr.instance().loggerFactory(new Log4JProvider());
//    LogKit.debug("TEST Log4j");
//
//    EnoaLogMgr.instance().loggerFactory(new Log4J2Provider());
//    LogKit.debug("TEST Log4j2");
//
//    EnoaLogMgr.instance().loggerFactory(new LogbackProvider());
//    LogKit.debug("TEST Logback");
//
//    EnoaLogMgr.instance().loggerFactory(new Slf4JLogProvider());
//    LogKit.debug("TEST Slf4j");
  }

}
