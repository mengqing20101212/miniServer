# Java 25 Migration

## Goal

MiniServer targets Java 25 so its virtual-thread workloads include the monitor
unpinning implementation delivered by JEP 491. A virtual thread that blocks while
holding or waiting for a `synchronized` monitor can release its carrier thread.

JEP 491 was delivered in JDK 24 and is therefore included in JDK 25. Native calls,
class initialization, and a few VM-internal operations can still pin a carrier, so
long blocking operations should still be kept out of critical sections where practical.

## Build contract

- Required runtime and compiler: JDK 25
- Required Maven: 3.9 or newer
- Bytecode target: `--release 25`
- Spring Boot modules: Spring Boot 3.5.x
- CI distribution: Temurin 25

The root build fails early when Maven runs with a Java version outside `[25, 26)`.

## Local setup

Set `JAVA_HOME` before running Maven:

```powershell
$env:JAVA_HOME = "D:\Soft\env\Java\jdk-25"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
java -version
cd server
mvn clean verify
```

Build one service and all of its local dependencies from the reactor root:

```powershell
cd server
mvn -pl GameServer -am clean verify
```

## Verification

`VirtualThreadMonitorCompatibilityTest` starts a child JVM with one virtual-thread
carrier. One virtual thread blocks inside a `synchronized` block while a second must
still run. This times out on the Java 21 monitor-pinning behavior and succeeds with
the JEP 491 implementation.

For production diagnosis, use JFR's `jdk.VirtualThreadPinned` event. The old
`-Djdk.tracePinnedThreads` property is obsolete after JEP 491 and has no effect.

## Rollback

Rollback requires reverting the Java release, Spring Boot parent versions, CI JDK,
and editor/runtime paths together. Running Java 25 bytecode on Java 21 is not supported.
