# GM 临时脚本

## 适用范围

该功能只用于临时线上 BUG 处理。当前支持 GMServer 向指定的
GameServer 或 GateServer 下发一次性 Groovy 脚本。LoginServer 不接入该功能，
需要修改时直接发布并闪断重启。

脚本在 GMServer 编译。目标服务器只接收编译后的 class bundle，不接收源码，
也不执行二次编译或业务校验。

## 执行流程

```text
GM 页面填写源码和参数
-> GM 使用当前 GameServer/GateServer 依赖编译
-> 生成 scripts.* class bundle
-> SHA-256 + HMAC-SHA256 签名
-> 内联 RPC 推送到指定 serverId
-> 目标服务器校验目标、有效期、签名、摘要和 executionId
-> 创建独立 ClassLoader 执行一次
-> 清理 Groovy MetaClass 和 ClassLoader 持有的 class/byte[] 引用
-> 返回结果并写入 gm_runtime_script 审计表
```

JVM 的实际类卸载由 GC 决定。框架不会缓存 class bundle 或 ClassLoader；脚本自身
不得创建无法关闭的线程、全局静态引用或向长期存活容器注册对象，否则会阻止卸载。

## 脚本约定

- 入口类必须位于 `scripts.*` 包并实现 `ly.script.GmRuntimeScript`。
- `GmScriptContext` 只提供 executionId、serverId、operator 和 JSON 参数。
- 脚本通过业务类的 `getInstance()`、静态入口或反射自行获取需要的 Service/Entity。
- 内存修改、数据库修改、重新加载等动作完全由脚本业务决定，没有预设执行模式枚举。
- 每次下发只允许执行一次。再次执行必须由 GM 重新编译并生成新的 executionId。
- 源码上限 256 KiB，参数上限 64 KiB，class bundle 上限 1 MiB。

## 共享密钥

GMServer、GameServer 和 GateServer 必须配置相同的临时脚本共享密钥，UTF-8 长度
至少 32 字节。优先级如下：

1. JVM 参数 `-Dminiserver.runtimeScriptSecret=...`
2. 环境变量 `MINISERVER_RUNTIME_SCRIPT_SECRET`
3. 各服务器 Nacos 启动配置字段 `runtimeScriptSecret`

缺少密钥或密钥长度不足时，GM 不会下发，目标服务器也会拒绝执行。密钥不得提交到 Git。

## GM 页面

菜单路径为 `配置管理 -> 临时脚本`，页面地址为 `/gm/runtime-script`。

页面支持：

- 获取当前在线且可用的 GameServer/GateServer 节点；
- 仅编译检测，不下发；
- 输入目标 serverId 二次确认后执行；
- 查看最近 100 条执行记录。

完整源码、参数、摘要、操作人和执行结果保存在 `gm_runtime_script`。通用操作日志只记录
接口和耗时，不重复记录源码。
