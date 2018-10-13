
# Enoa Docker Client


A docker client by enoa.


## Usage

### epm

epm install

```java
DockerConfig config = new DockerConfig.Builder()
  .host("tcp://localhost:2375")
  .tls(Boolean.FALSE)
  .version("v1.35")
  .json(new GsonProvider())
  .build();
Docker.epm().install(config);
```


### Docker info

```java
DRet<EInfo> ret = Docker.system().info();
```

### Container list

```java
DRet<List<EContainer>> ret = Docker.container().list();
```

### Image list

```java
DRet<List<EImage>> ret = Docker.image().list(DQPImageList.create().all());
```
