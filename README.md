# Temporary Game Mode Switch

一个用于 Minecraft Java 版服务器的插件，允许玩家临时切换游戏模式，并在指定时间后自动切换回生存模式。

## 功能特性

- 🎮 临时切换游戏模式（生存、创造、冒险、旁观者）
- ⏱️ 可自定义的自动切换时长（默认 5 分钟）
- 🌍 支持中文和英文两种语言
- 🎨 支持颜色代码的消息提示
- 🔧 管理员可配置默认时长和可用的游戏模式
- ✨ 智能命令 Tab 补全功能
- 🛡️ 完整的权限系统

## 支持的 Minecraft 版本

- Minecraft Java 版 1.21.1
- 兼容 Paper、Purpur、Spigot 服务器

## 安装说明

1. 下载最新版本的 `TemporaryGameModeSwitch-1.0.0.jar`
2. 将文件放入服务器的 `plugins` 文件夹
3. 重启服务器或使用插件管理器加载插件
4. 插件会自动创建配置文件

## 使用方法

### 玩家命令

#### `/tempgm <游戏模式> [时长]`
临时切换游戏模式，可选指定时长（秒）。

**游戏模式参数：**
- `survival` 或 `0` - 生存模式
- `creative` 或 `1` - 创造模式
- `adventure` - 冒险模式
- `spectator` - 旁观者模式

**示例：**
```
/tempgm creative          # 切换到创造模式 5 分钟
/tempgm survival 60       # 切换到生存模式 60 秒
/tempgm 1 300             # 切换到创造模式 5 分钟
```

### 管理员命令

#### `/optempgm time <时长>`
设置默认的临时游戏模式时长（秒）。

**示例：**
```
/optempgm time 600        # 设置默认时长为 10 分钟
```

#### `/optempgm gamemode enable <游戏模式>`
启用指定的游戏模式，允许玩家使用。

**示例：**
```
/optempgm gamemode enable creative    # 启用创造模式
```

#### `/optempgm gamemode disable <游戏模式>`
禁用指定的游戏模式，禁止玩家使用。

**示例：**
```
/optempgm gamemode disable spectator  # 禁用旁观者模式
```

## 配置文件

插件配置文件位于 `plugins/TemporaryGameModeSwitch/config.yml`

```yaml
# 语言设置：en (英文) 或 zh (中文)
language: zh

# 默认临时游戏模式时长（秒）
default-duration: 300

# /tempgm 命令可用的游戏模式
enabled-gamemodes:
  - survival
  - creative
  - adventure
  - spectator

# 消息配置
messages:
  en:
    # 英文消息...
  zh:
    # 中文消息...
```

## 权限说明

| 权限节点 | 描述 | 默认 |
|---------|------|------|
| `tempgm.use` | 使用 `/tempgm` 命令 | 所有玩家 |
| `tempgm.admin` | 使用 `/optempm` 命令 | 仅 OP |

## 开发信息

- **版本**: 1.0.0
- **开发语言**: Java 21
- **构建工具**: Maven
- **API**: Paper API 1.21.1-R0.1-SNAPSHOT

## 更新日志

### v1.0.0 (2026-01-09)
- 🎉 首次发布
- ✅ 实现临时游戏模式切换功能
- ✅ 添加中英文语言支持
- ✅ 实现命令 Tab 补全
- ✅ 添加颜色代码支持
- ✅ 完善权限系统

## 许可证

本项目采用 MIT 许可证。详见 [LICENSE](LICENSE) 文件。

## 支持

如有问题或建议，请提交 Issue 或 Pull Request。

## 致谢

- PaperMC 团队提供的 Paper API
- ViaVersion 项目提供的参考实现