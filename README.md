# QuestSP

Modern Minecraft quest plugin for Paper servers.

## Features

* Simple quest system
* Configurable quests
* Reward support
* Lightweight and optimized
* Easy setup

## Requirements

* Java 21
* Paper / Spigot 1.20.6+
* Vault (optional)

## Installation

1. Download the latest `.jar`
2. Put the file into your `plugins` folder
3. Restart the server

## Commands

| Command         | Description          |
| --------------- | -------------------- |
| `/quest`        | Open quest menu      |
| `/quest reload` | Reload plugin config |

## Permissions

| Permission      | Description           |
| --------------- | --------------------- |
| `questsp.use`   | Use the plugin        |
| `questsp.admin` | Access admin commands |

## Configuration

Config file location:

```txt
plugins/QuestSP/config.yml
```
## Features

* Easy configurable quest system
* Questions can be edited directly in `config.yml`
* Beginner-friendly setup
* Reward support
* Lightweight and optimized

## Example Configuration

```yml
questions:
  "2 + 2 = ?": "4"
  "Ibukota Indonesia?": "jakarta"
```


## Build

Build using Maven:

```bash
mvn clean package
```

Compiled jar location:

```txt
target/QuestSP-1.0.jar
```

## Dependencies

* Paper API
* Vault API

## Support

If you find bugs or want to suggest features, open an issue on GitHub.

## Author
Made by Radianza

## Special Thanks

<p align="left">
  <img src="https://static.wikia.nocookie.net/minecraft_gamepedia/images/2/20/Grass_Block_JE7_BE6.png" width="50" alt="Minecraft"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/windows8/windows8-original.svg" width="50" alt="Microsoft"/>
  <img src="https://upload.wikimedia.org/wikipedia/commons/0/04/ChatGPT_logo.svg" width="50" alt="ChatGPT"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/vscode/vscode-original.svg" width="50" alt="VS Code"/>
</p>

Thanks to:

* Minecraft
* Microsoft
* ChatGPT
* VS Code


