package io.enoa.docker.parser.docker;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.ret.docker.DResp;
import io.enoa.docker.ret.docker.common.EUser;
import io.enoa.docker.ret.docker.plugin.*;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

class EPluginParser extends AbstractParser<EPlugin> {

  private static class Holder {
    private static final EPluginParser INSTANCE = new EPluginParser();
  }

  public static EPluginParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public EPlugin ok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    EPlugin plugin = this.plugin(kv);
    CollectionKit.clear(kv);
    return plugin;
  }


  EPlugin plugin(Kv kv) {
    if (CollectionKit.isEmpty(kv))
      return null;
    EPlugin.Builder builder = new EPlugin.Builder()
      .id(kv.string("ID"))
      .name(kv.string("Name"))
      .enabled(kv.bool("Enabled"))
      .reference(kv.string("PluginReference"))
      .setting(this.setting(kv))
      .config(this.config(kv));
    return builder.build();
  }

  private EPluginSetting setting(Kv kv) {
    EPluginSetting.Builder builder = new EPluginSetting.Builder()
      .args(AEExtra.stringarray(kv, "Args"))
      .env(AEExtra.stringarray(kv, "Env"));
    Object mts = kv.get("Mounts");
    if (mts instanceof Collection) {
      Collection _m = (Collection) mts;
      List<EPluginMount> mounts = new ArrayList<>(_m.size());
      _m.forEach(m -> {
        Kv mk = Kv.by((Map) m);
        mounts.add(this.mount(mk));
        CollectionKit.clear(mk);
      });
      builder.mounts(mounts);
    }
    Object des = kv.get("Devices");
    if (des instanceof Collection) {
      Collection _d = (Collection) des;
      List<EPluginDevice> devices = new ArrayList<>(_d.size());
      _d.forEach(d -> {
        Kv dk = Kv.by((Map) d);
        devices.add(this.device(dk));
        CollectionKit.clear(dk);
      });
      builder.devices(devices);
    }
    return builder.build();
  }

  private EPluginConfig config(Kv kv) {
    EPluginConfig.Builder builder = new EPluginConfig.Builder()
      .dockerversion(kv.string("DockerVersion"))
      .description(kv.string("Description"))
      .documentation(kv.string("Documentation"))
      .entrypoint(AEExtra.stringarray(kv, "Entrypoint"))
      .workdir(kv.string("WorkDir"))
      .propagatedmount(kv.string("PropagatedMount"))
      .ipchost(kv.bool("IpcHost"))
      .pidhost(kv.bool("PidHost"));

    Object ito = kv.get("Interface");
    if (ito instanceof Map) {
      Kv itk = Kv.by((Map) ito);
      builder.interfacex(this.interfacex(itk));
      CollectionKit.clear(itk);
    }
    Object ut = kv.get("User");
    if (ut instanceof Map) {
      Kv uk = Kv.by((Map) ut);
      builder.user(this.user(uk));
      CollectionKit.clear(uk);
    }
    Object nt = kv.get("Network");
    if (ut instanceof Map) {
      Kv nk = Kv.by((Map) nt);
      builder.network(this.network(nk));
      CollectionKit.clear(nk);
    }
    Object lx = kv.get("Linux");
    if (lx instanceof Map) {
      Kv lk = Kv.by((Map) lx);
      builder.linux(this.linux(lk));
      CollectionKit.clear(lk);
    }
    Object mtt = kv.get("Mounts");
    if (mtt instanceof Collection) {
      Collection mts = (Collection) mtt;
      List<EPluginMount> mounts = new ArrayList<>(mts.size());
      mts.forEach(mt -> {
        if (!(mt instanceof Map))
          return;
        Kv mk = Kv.by((Map) mt);
        mounts.add(this.mount(mk));
        CollectionKit.clear(mk);
      });
      builder.mounts(mounts);
    }
    Object ent = kv.get("Env");
    if (ent instanceof Collection) {
      Collection ents = (Collection) ent;
      List<EPluginEnv> mounts = new ArrayList<>(ents.size());
      ents.forEach(mt -> {
        if (!(mt instanceof Map))
          return;
        Kv mk = Kv.by((Map) mt);
        mounts.add(this.env(mk));
        CollectionKit.clear(mk);
      });
      builder.env(mounts);
    }
    Object arg = kv.get("Arg");
    if (arg instanceof Collection) {
      Collection args = (Collection) arg;
      List<EPluginArg> mounts = new ArrayList<>(args.size());
      args.forEach(mt -> {
        if (!(mt instanceof Map))
          return;
        Kv mk = Kv.by((Map) mt);
        mounts.add(this.arg(mk));
        CollectionKit.clear(mk);
      });
      builder.arg(mounts);
    }
    Object rfs = kv.get("rootfs");
    if (rfs instanceof Map) {
      Kv rk = Kv.by((Map) rfs);
      builder.rootfs(this.rootfs(rk));
      CollectionKit.clear(rk);
    }
    return builder.build();
  }

  private EPluginRootfs rootfs(Kv kv) {
    EPluginRootfs.Builder builder = new EPluginRootfs.Builder()
      .diffids(AEExtra.stringarray(kv, " diff_ids"))
      .type(kv.string(" type"));
    return builder.build();
  }

  private EPluginEnv env(Kv kv) {
    EPluginEnv.Builder builder = new EPluginEnv.Builder()
      .description(kv.string("Description"))
      .name(kv.string("Name"))
      .settable(AEExtra.stringarray(kv, "Settable"))
      .value(kv.string("Value"));
    return builder.build();
  }

  private EPluginArg arg(Kv kv) {
    EPluginArg.Builder builder = new EPluginArg.Builder()
      .description(kv.string("Description"))
      .name(kv.string("Name"))
      .settable(AEExtra.stringarray(kv, "Settable"))
      .value(AEExtra.stringarray(kv, "Value"));
    return builder.build();
  }

  private EPluginLinux linux(Kv kv) {
    EPluginLinux.Builder builder = new EPluginLinux.Builder()
      .capabilities(AEExtra.stringarray(kv, " Capabilities"))
      .allowalldevices(kv.bool("AllowAllDevices"));
    Object dt = kv.get("Devices");
    if (dt instanceof Collection) {
      Collection ds = (Collection) dt;
      List<EPluginDevice> devices = new ArrayList<>(ds.size());
      ds.forEach(d -> {
        if (!(d instanceof Map))
          return;
        Kv dk = Kv.by((Map) d);
        devices.add(this.device(dk));
        CollectionKit.clear(dk);
      });
      builder.devices(devices);
    }
    return builder.build();
  }

  private EPluginNetwork network(Kv kv) {
    EPluginNetwork.Builder builder = new EPluginNetwork.Builder()
      .type(kv.string("Network"));
    return builder.build();
  }

  private EUser user(Kv kv) {
    EUser.Builder builder = new EUser.Builder()
      .gid(kv.integer("GID"))
      .uid(kv.integer("UID"));
    return builder.build();
  }

  private EPluginInterface interfacex(Kv kv) {
    EPluginInterface.Builder builder = new EPluginInterface.Builder()
      .socket(kv.string("Socket"));
    Object tt = kv.get("Types");
    if (tt instanceof Collection) {
      Collection ts = (Collection) tt;
      List<EInterfaceType> tts = new ArrayList<>(ts.size());
      ts.forEach(t -> {
        Kv tk = Kv.by((Map) t);
        EInterfaceType.Builder ttb = new EInterfaceType.Builder()
          .capability(tk.string("Capability"))
          .prefix(tk.string("Prefix"))
          .version(tk.string("Version"));
        tts.add(ttb.build());
      });
      builder.types(tts);
    }
    return builder.build();
  }


  private EPluginMount mount(Kv kv) {
    if (CollectionKit.isEmpty(kv))
      return null;
    EPluginMount.Builder builder = new EPluginMount.Builder()
      .name(kv.string("Name"))
      .description(kv.string("Description"))
      .settable(AEExtra.stringarray(kv, "Settable"))
      .source(kv.string("Source"))
      .destination(kv.string("Destination"))
      .type(kv.string("Type"))
      .options(AEExtra.stringarray(kv, "Options"));
    return builder.build();
  }

  private EPluginDevice device(Kv kv) {
    if (CollectionKit.isEmpty(kv))
      return null;
    EPluginDevice.Builder builder = new EPluginDevice.Builder()
      .name(kv.string("Name"))
      .description(kv.string(" Description"))
      .settable(AEExtra.stringarray(kv, "Settable"))
      .path(kv.string("Path"));
    return builder.build();
  }

}
