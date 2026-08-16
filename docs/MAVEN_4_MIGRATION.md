# Maven 4 Migration

## Build Contract

- Maven is pinned to `4.0.0-rc-5` by the wrapper under `server/`.
- Java remains pinned to JDK 25.
- The reactor POM keeps `modelVersion` at `4.0.0` during the compatibility phase.
- Build and lifecycle plugin versions are locked explicitly.
- The root enforcer rule rejects Maven 3 and Maven 4 versions older than RC5.

Maven 4 is still a release candidate. The wrapper makes that choice reproducible
for local development and CI, without requiring a global Maven installation.

## Commands

Run all commands from `server/`.

```powershell
.\mvnw.cmd --version
.\mvnw.cmd -DskipTests install
.\mvnw.cmd -pl GameServer -am -DskipTests package
```

On macOS and Linux:

```bash
./mvnw --version
./mvnw -DskipTests install
```

The repository scripts and CI use this wrapper. Do not replace them with a
machine-specific Maven path.

## Upgrade Policy

When Maven 4 GA is released:

1. Update `distributionUrl` in both wrapper property files.
2. Update the root `requireMavenVersion` lower bound.
3. Run a clean full-reactor package and the targeted test suites.
4. Upgrade to POM model `4.1.0` only as a separate change after IDE and plugin
   compatibility has been verified.

Parallel builds are not enabled globally yet. Generated sources and packaging
plugins must first be verified as thread-safe; developers can benchmark `-T`
locally without changing the default build contract.
