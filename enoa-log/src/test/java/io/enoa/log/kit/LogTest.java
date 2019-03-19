/*
 * Copyright (c) 2018, enoa (fewensa@enoa.io)
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

import io.enoa.log.Log;
import io.enoa.log.provider.jdk.JdkLogProvider;
import io.enoa.log.provider.slf4j.Slf4JLogProvider;
import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 * Created by ein on 2017/8/14.
 */
public class LogTest {

  @Test
  public void testLog() {

    Log.debug("Debug log");
    Log.info("Info log");
    Log.warn("Warn log");
    Log.error("Error log");
    Log.debug(Log.name());
    Log.use("TEST").debug("test");
    Log.debug("============");
    Log.debug("Debug log");
    Log.info("Info log");
    Log.warn("Warn log");
    Log.error("Error log");
    Log.debug(Log.name());
    Log.use(LogTest.class).debug("Log test");
    Log.debug("============");
    Log.debug("Debug log");
    Log.info("Info log");
    Log.warn("Warn log");
    Log.error("Error log");
    Log.debug(Log.name());

    Log.epm().install(new Slf4JLogProvider());

//    EMgrLog.instance().loggerFactory(new JdkLogProvider());
//    Log.debug("TEST JDK");
//
//    EMgrLog.instance().loggerFactory(new Log4JProvider());
//    Log.debug("TEST Log4j");
//
//    EMgrLog.instance().loggerFactory(new Log4J2Provider());
//    Log.debug("TEST Log4j2");
//
//    EMgrLog.instance().loggerFactory(new LogbackProvider());
//    Log.debug("TEST Logback");
//
//    EMgrLog.instance().loggerFactory(new Slf4JLogProvider());
//    Log.debug("TEST Slf4j");
  }

}
