# DiscordMC

a simple minecraft -> discord minecraft plugin made with Kotlin

## Features

- `.toml` configuration file
  - reloadable with the `/discordmc reload` command (requires `discordmc.reload` permission)
- webhook support
- (mostly) fully customizable messages
  - embed support (if you want to enable it)
  - placeholders for player name, message, etc.

## Default Configuration

> [!TIP]
> You can disable events by removing (or commenting out) their section from the config

```toml
# DiscordMC Configuration
# This file is written in TOML format (https://toml.io/en/)
# You can also use placeholder values, for each event specifically

# This gets triggered whenever a player sends a chat message
# The placeholders available are:
# - {player} - Player's name
# - {x} - Player's X coordinate
# - {y} - Player's Y coordinate
# - {z} - Player's Z coordinate
# - {world} - Player's world
# - {message} - Chat message
[chat]
webhook = "https://discord.com/api/webhooks/123456/abcdef" # remove if you don't want a webhook
content = "" # remove if you don't want content
[chat.embed] # remove if you don't want an embed
title = "chat"
description = ""
color = "#FFFFFF"
timestamp = true
[chat.embed.author]
name = "{player}"
iconUrl = "https://minotar.net/helm/{player}/512.png"
[chat.embed.footer]
text = "chat"
iconUrl = "https://cdn.discordapp.com/embed/avatars/0.png"

# This gets triggered whenever a player joins the server
# The placeholders available are:
# - {player} - Player's name
# - {x} - Player's X coordinate
# - {y} - Player's Y coordinate
# - {z} - Player's Z coordinate
# - {world} - Player's world
[join]
webhook = "https://discord.com/api/webhooks/123456/abcdef" # remove if you don't want a webhook
content = "" # remove if you don't want content
[join.embed] # remove if you don't want an embed
title = "join"
description = ""
color = "#FFFFFF"
timestamp = true
[join.embed.author]
name = "{player}"
iconUrl = "https://minotar.net/helm/{player}/512.png"
[join.embed.footer]
text = "join"
iconUrl = "https://cdn.discordapp.com/embed/avatars/0.png"

# This gets triggered whenever a player leaves the server
# The placeholders available are:
# - {player} - Player's name
# - {x} - Player's X coordinate
# - {y} - Player's Y coordinate
# - {z} - Player's Z coordinate
# - {world} - Player's world
[leave]
webhook = "https://discord.com/api/webhooks/123456/abcdef" # remove if you don't want a webhook
content = "" # remove if you don't want content
[leave.embed] # remove if you don't want an embed
title = "leave"
description = ""
color = "#FFFFFF"
timestamp = true
[leave.embed.author]
name = "{player}"
iconUrl = "https://minotar.net/helm/{player}/512.png"
[leave.embed.footer]
text = "leave"
iconUrl = "https://cdn.discordapp.com/embed/avatars/0.png"

# This gets triggered whenever a player dies
# The placeholders available are:
# - {player} - Player's name
# - {x} - Player's X coordinate
# - {y} - Player's Y coordinate
# - {z} - Player's Z coordinate
# - {world} - Player's world
# - {message} - Death message
[death]
webhook = "https://discord.com/api/webhooks/123456/abcdef" # remove if you don't want a webhook
content = "" # remove if you don't want content
[death.embed] # remove if you don't want an embed
title = "death"
description = ""
color = "#FFFFFF"
timestamp = true
[death.embed.author]
name = "{player}"
iconUrl = "https://minotar.net/helm/{player}/512.png"
[death.embed.footer]
text = "death"
iconUrl = "https://cdn.discordapp.com/embed/avatars/0.png"

# This gets triggered whenever a player runs a command
# The placeholders available are:
# - {player} - Player's name
# - {x} - Player's X coordinate
# - {y} - Player's Y coordinate
# - {z} - Player's Z coordinate
# - {world} - Player's world
# - {message} - Command message
[playerCommand]
webhook = "https://discord.com/api/webhooks/123456/abcdef" # remove if you don't want a webhook
content = "" # remove if you don't want content
[playerCommand.embed] # remove if you don't want an embed
title = "playerCommand"
description = ""
color = "#FFFFFF"
timestamp = true
[playerCommand.embed.author]
name = "{player}"
iconUrl = "https://minotar.net/helm/{player}/512.png"
[playerCommand.embed.footer]
text = "playerCommand"
iconUrl = "https://cdn.discordapp.com/embed/avatars/0.png"

# This gets triggered whenever CONSOLE runs a command
# The placeholders available are:
# - {player} - Player's name (always CONSOLE)
# - {message} - Command message
[serverCommand]
webhook = "https://discord.com/api/webhooks/123456/abcdef" # remove if you don't want a webhook
content = "" # remove if you don't want content
[serverCommand.embed] # remove if you don't want an embed
title = "serverCommand"
description = ""
color = "#FFFFFF"
timestamp = true
[serverCommand.embed.author]
name = "{player}"
iconUrl = "https://minotar.net/helm/{player}/512.png"
[serverCommand.embed.footer]
text = "serverCommand"
iconUrl = "https://cdn.discordapp.com/embed/avatars/0.png"

# This gets triggered whenever the server starts
# No placeholders available
[start]
webhook = "https://discord.com/api/webhooks/123456/abcdef" # remove if you don't want a webhook
content = "" # remove if you don't want content
[start.embed] # remove if you don't want an embed
title = "start"
description = ""
color = "#FFFFFF"
timestamp = true
[start.embed.author]
name = "{player}"
iconUrl = "https://minotar.net/helm/{player}/512.png"
[start.embed.footer]
text = "start"
iconUrl = "https://cdn.discordapp.com/embed/avatars/0.png"

# This gets triggered whenever the server stops
# No placeholders available
[stop]
webhook = "https://discord.com/api/webhooks/123456/abcdef" # remove if you don't want a webhook
content = "" # remove if you don't want content
[stop.embed] # remove if you don't want an embed
title = "stop"
description = ""
color = "#FFFFFF"
timestamp = true
[stop.embed.author]
name = "{player}"
iconUrl = "https://minotar.net/helm/{player}/512.png"
[stop.embed.footer]
text = "stop"
iconUrl = "https://cdn.discordapp.com/embed/avatars/0.png"
```
