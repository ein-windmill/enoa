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
package io.enoa.docker.parser.docker;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.common.ECreated;
import io.enoa.docker.dket.docker.common.ECreatedWithWarning;
import io.enoa.docker.dket.docker.config.EConfig;
import io.enoa.docker.dket.docker.container.*;
import io.enoa.docker.dket.docker.distribution.EDistribution;
import io.enoa.docker.dket.docker.dockerinfo.EDockerInfo;
import io.enoa.docker.dket.docker.exec.EExecInspect;
import io.enoa.docker.dket.docker.image.*;
import io.enoa.docker.dket.docker.network.ENetworPrune;
import io.enoa.docker.dket.docker.network.ENetwork;
import io.enoa.docker.dket.docker.node.ENode;
import io.enoa.docker.dket.docker.plugin.EPlugin;
import io.enoa.docker.dket.docker.plugin.EPluginPrivilege;
import io.enoa.docker.dket.docker.secret.ESecret;
import io.enoa.docker.dket.docker.service.EServiceUpdate;
import io.enoa.docker.dket.docker.swarm.ESwarmInspect;
import io.enoa.docker.dket.docker.swarm.ESwarmUnlockKey;
import io.enoa.docker.dket.docker.system.*;
import io.enoa.docker.dket.docker.volume.EVolume;
import io.enoa.docker.dket.docker.volume.EVolumeLs;
import io.enoa.docker.dket.docker.volume.EVolumePrune;
import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.binary.EnoaBinary;
import io.enoa.toolkit.value.Void;

import java.util.List;

@FunctionalInterface
public interface DIParser<T> {

  static DIParser<Void> voidx() {
    return EVoidParser.instance();
  }

  static DIParser<EnoaBinary> binary() {
    return EBinaryParser.instance();
  }

  static DIParser<String> string() {
    return EStringParser.instance();
  }

  static DIParser<ECreatedWithWarning> createdwithwarning() {
    return ECreatedWithWarningParser.instance();
  }

  static DIParser<ECreated> created() {
    return ECreateParser.instance();
  }

  static DIParser<EDockerInfo> dockerinfo() {
    return EDockerInfoParser.instance();
  }

  static DIParser<List<EContainer>> containerlist() {
    return EContainerListParser.instance();
  }

  static DIParser<ECInspect> containerinspect() {
    return EContainerInspectParser.instance();
  }

  static DIParser<EProcesses> top() {
    return EProcessParser.instance();
  }

  static DIParser<List<EChange>> changes() {
    return EChangesParser.instance();
  }

  static DIParser<EStatistics> statistics() {
    return EStatisticsParser.instance();
  }

  static DIParser<EUpdate> update() {
    return EUpdateParser.instance();
  }

  static DIParser<ECWait> waitx() {
    return EWaitParser.instance();
  }

  static DIParser<ECPrune> containerprune() {
    return ECPruneParser.instance();
  }

  static DIParser<List<EImage>> imagelist() {
    return EImageListParser.instance();
  }

  static DIParser<EIPrune> buildprune() {
    return EIPruneParser.instance();
  }

  static DIParser<EIInspect> imageinspect() {
    return EImageInspectParser.instance();
  }

  static DIParser<List<EHistory>> imagehistory() {
    return EImageHistoryParser.instance();
  }

  static DIParser<List<EIRemove>> imageremove() {
    return EImageRemoveParser.instance();
  }

  static DIParser<List<EISearch>> imagesearch() {
    return EImageSearchParser.instance();
  }

  static DIParser<EImagePrune> imageprune() {
    return EImagePruneParser.instance();
  }

  static DIParser<EICommit> imagecommit() {
    return EImageCommitParser.instance();
  }

  static DIParser<List<ENetwork>> networklist() {
    return ENetworkListParser.instance();
  }

  static DIParser<ENetwork> networkinspect() {
    return ENetworkInspectParser.instance();
  }

  static DIParser<ENetworPrune> networkprune() {
    return ENetworkPruneParser.instance();
  }

  static DIParser<EVolumeLs> volumelist() {
    return EVolumeListParser.instance();
  }

  static DIParser<EVolume> volume() {
    return EVolumeParser.instance();
  }

  static DIParser<EVolumePrune> volumeprune() {
    return EVolumePruneParser.instance();
  }

  static DIParser<EExecInspect> execinspect() {
    return EExecInspectParser.instance();
  }

  static DIParser<ESwarmInspect> swarminspect() {
    return ESwarmInspectParser.instance();
  }

  static DIParser<ESwarmUnlockKey> swarmunlockkey() {
    return ESwarmUnlockKeyParser.instance();
  }

  static DIParser<List<ENode>> nodelist() {
    return ENodeListParser.instance();
  }

  static DIParser<ENode> node() {
    return ENodeParser.instance();
  }

  static DIParser<EServiceUpdate> serviceupdate() {
    return EServiceUpdateParser.instance();
  }

  static DIParser<List<ESecret>> secretlist() {
    return ESecretListParser.instance();
  }

  static DIParser<ESecret> secret() {
    return ESecretParser.instance();
  }

  static DIParser<List<EConfig>> configlist() {
    return EConfigListParser.instance();
  }

  static DIParser<EConfig> config() {
    return EConfigParser.instance();
  }

  static DIParser<List<EPlugin>> pluginlist() {
    return EPluginListParser.instance();
  }

  static DIParser<EPlugin> plugin() {
    return EPluginParser.instance();
  }

  static DIParser<List<EPluginPrivilege>> pluginprivilege() {
    return EPluginPrivilegeParser.instance();
  }

//  static DIParser<EInfo> info() {
//    return EInfoParser.instance();
//  }

  static DIParser<EYVersion> eyversion() {
    return EYVersionParser.instance();
  }

  static DIParser<EMonitor> monitor() {
    return EMonitorParser.instance();
  }

  static DIParser<Edf> edfparser() {
    return EdfParser.instance();
  }

  static DIParser<EAuth> auth() {
    return EAuthParser.instance();
  }

  static DIParser<EDistribution> distribution() {
    return EDistributionParser.instance();
  }

  /**
   * only use not create dresp object
   * <p>
   * todo a better way
   *
   * @param config config
   * @param text   text
   * @return DRet
   */
  @Deprecated
  default DRet<T> parse(DockerConfig config, String text) {
    return this.parse(config, DResp.create(100, "application/json", text.getBytes(EoConst.CHARSET)));
  }

  DRet<T> parse(DockerConfig config, DResp resp);

}
