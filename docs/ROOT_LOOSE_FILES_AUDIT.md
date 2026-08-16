# Root Loose Files Audit

更新时间: 2026-04-26

## 1. 范围
本次只审查仓库根目录下的“松散文件”，不包含目录内部内容。

## 2. 审查结果总览

### 建议保留
- `README.md`
- `STARTUP.SKILL.md`
- `reindex_vscode.bat`
- `start_app.bat`
- `.editorconfig`
- `.gitattributes`
- `.gitignore`

### 已迁移到更规范位置
- `test_entity_generator.java` -> `server/core/src/test/java/ly/EntityToSqlGeneratorSmokeTest.java`
- `test_new_types.xlsx` -> `server/tool/src/test/resources/test_new_types.xlsx`

### 建议保留但标记为历史/待收敛
- `nacos-config.txt`

### 建议清理
- `HikariPing.class`
- `HikariPing2.class`
- `JdbcPing.class`
- `JdbcPing5s.class`

## 3. 逐项判断

### `HikariPing.class`
- 状态：未跟踪
- 类型：Java 编译产物（class 文件）
- 依据：`file` 显示为 Java 21 class；`.gitignore` 已忽略 `*.class`
- 判断：应视为临时产物
- 建议：删除

### `HikariPing2.class`
- 状态：未跟踪
- 类型：Java 编译产物
- 判断：同上
- 建议：删除

### `JdbcPing.class`
- 状态：未跟踪
- 类型：Java 编译产物
- 判断：同上
- 建议：删除

### `JdbcPing5s.class`
- 状态：未跟踪
- 类型：Java 编译产物
- 判断：同上
- 建议：删除

### `test_entity_generator.java`
- 状态：已跟踪
- 内容：调用 `ly.EntityToSqlGenerator`，生成 `generated-sql/create-tables.sql`
- 判断：这是一个明显的手工验证/冒烟测试入口，不是纯垃圾文件
- 风险：文件名风格与主工程不一致，放在根目录也不够规范
- 建议：短期保留；后续可考虑迁移到：
  - `server/core/src/test/java/...`
  - 或 `server/tool/src/test/java/...`
  - 或 `scripts/` / `scratch/` 目录

### `test_new_types.xlsx`
- 状态：已跟踪
- 实际观察：虽然扩展名是 `.xlsx`，但内容并不是标准 Office ZIP 包，而是 CSV 风格纯文本数据
- 判断：这大概率是给 Excel/类型解析链路用的轻量测试样例
- 风险：扩展名会误导人，以为它是真正的 Excel 文件
- 建议：短期保留；后续可考虑：
  - 改成更明确的测试资源名
  - 或迁移到 `server/tool/src/test/resources/`
  - 若工具链强依赖 `.xlsx` 后缀，则至少在文档中注明“它其实是文本测试样例”

### `reindex_vscode.bat`
- 状态：已跟踪
- 内容：清理 VS Code Java workspaceStorage、临时缓存、各模块 target、以及本地 Maven 中 `ly` 相关产物
- 判断：对 Java 索引损坏或 VS Code 无法正确识别模块时有实际用途
- 建议：保留

### `Rescan Java Projects`
- 状态：已删除
- 原因：内容不完整，且已收敛到 `docs/DEV_WORKFLOW.md` 与 `reindex_vscode.bat`
- 结论：不再需要单独保留

### `start_app.bat`
- 状态：已跟踪
- 内容：切到 `server/` 后执行 `mvn clean package -DskipTests`
- 判断：虽然简单，但作为 Windows 下快速构建入口仍有价值
- 建议：保留

### `nacos-config.txt`
- 状态：已跟踪
- 内容：记录 localhost:8848 的 Nacos 场景
- 风险：与 `STARTUP.SKILL.md` 当前远程 Nacos `118.25.76.117:8848` 冲突
- 判断：有历史参考价值，但不能作为当前默认启动配置
- 处理结果：已在文件顶部标记“历史说明 / 非当前默认配置”，当前启动参数以 `STARTUP.SKILL.md` 为准

## 4. 推荐清理动作
本轮已经完成的收口动作：
1. 已删除 4 个根目录 `.class` 文件
2. 已将 `Rescan Java Projects` 内容并入文档并删除原文件
3. 已给 `nacos-config.txt` 加“历史说明 / 非当前默认配置”标记
4. 已将 `test_entity_generator.java`、`test_new_types.xlsx` 迁移到更规范的测试/样例目录

如果还要继续收口，下一步建议：
- 把 `ROOT_LOOSE_FILES_AUDIT.md` 中仍保留的历史审计小节改成“已迁移记录”风格
- 视团队实际需要，未来可以把 `nacos-config.txt` 重命名为 `nacos-config.local-legacy.txt`

## 5. 当前结论
- 目前真正“应立即清理”的，只有 4 个未跟踪 `.class` 文件
- 其余松散文件大多不是垃圾，而是：
  - 历史说明
  - 快捷脚本
  - 临时但有价值的测试样例
- 更合适的处理方式不是直接删，而是“迁移 + 标注 + 收敛位置”
