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
content = "The `chat` event was triggered..." # remove if you don't want content
[chat.embed] # remove if you don't want an embed
title = "Chat"
description = "..."
color = "#FFFFFF"
timestamp = true
[chat.embed.author]
name = "You can also supply authors"
iconUrl = "https://images.pexels.com/photos/3772623/pexels-photo-3772623.jpeg?cs=srgb&dl=pexels-olly-3772623.jpg&fm=jpg"
[chat.embed.footer]
text = "And footers"
iconUrl = "https://cdn-prod.medicalnewstoday.com/content/images/articles/324/324336/bones-of-the-foot-infographic-em-image-credit-stephen-kelly-2019-br.jpg"

# This gets triggered whenever a player joins the server
# The placeholders available are:
# - {player} - Player's name
# - {x} - Player's X coordinate
# - {y} - Player's Y coordinate
# - {z} - Player's Z coordinate
# - {world} - Player's world
[join]
webhook = "https://discord.com/api/webhooks/123456/abcdef" # remove if you don't want a webhook
content = "The `join` event was triggered..." # remove if you don't want content
[join.embed] # remove if you don't want an embed
title = "Join"
description = "..."
color = "#FFFFFF"
timestamp = true
[join.embed.author]
name = "You can also supply authors"
iconUrl = "https://images.pexels.com/photos/3772623/pexels-photo-3772623.jpeg?cs=srgb&dl=pexels-olly-3772623.jpg&fm=jpg"
[join.embed.footer]
text = "And footers"
iconUrl = "https://cdn-prod.medicalnewstoday.com/content/images/articles/324/324336/bones-of-the-foot-infographic-em-image-credit-stephen-kelly-2019-br.jpg"

# This gets triggered whenever a player leaves the server
# The placeholders available are:
# - {player} - Player's name
# - {x} - Player's X coordinate
# - {y} - Player's Y coordinate
# - {z} - Player's Z coordinate
# - {world} - Player's world
[leave]
webhook = "https://discord.com/api/webhooks/123456/abcdef" # remove if you don't want a webhook
content = "The `leave` event was triggered..." # remove if you don't want content
[leave.embed] # remove if you don't want an embed
title = "Leave"
description = "..."
color = "#FFFFFF"
timestamp = true
[leave.embed.author]
name = "You can also supply authors"
iconUrl = "https://images.pexels.com/photos/3772623/pexels-photo-3772623.jpeg?cs=srgb&dl=pexels-olly-3772623.jpg&fm=jpg"
[leave.embed.footer]
text = "And footers"
iconUrl = "https://cdn-prod.medicalnewstoday.com/content/images/articles/324/324336/bones-of-the-foot-infographic-em-image-credit-stephen-kelly-2019-br.jpg"

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
content = "The `death` event was triggered..." # remove if you don't want content
[death.embed] # remove if you don't want an embed
title = "Death"
description = "..."
color = "#FFFFFF"
timestamp = true
[death.embed.author]
name = "You can also supply authors"
iconUrl = "https://images.pexels.com/photos/3772623/pexels-photo-3772623.jpeg?cs=srgb&dl=pexels-olly-3772623.jpg&fm=jpg"
[death.embed.footer]
text = "And footers"
iconUrl = "https://cdn-prod.medicalnewstoday.com/content/images/articles/324/324336/bones-of-the-foot-infographic-em-image-credit-stephen-kelly-2019-br.jpg"

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
content = "The `playerCommand` event was triggered..." # remove if you don't want content
[playerCommand.embed] # remove if you don't want an embed
title = "Player Command"
description = "..."
color = "#FFFFFF"
timestamp = true
[playerCommand.embed.author]
name = "You can also supply authors"
iconUrl = "https://images.pexels.com/photos/3772623/pexels-photo-3772623.jpeg?cs=srgb&dl=pexels-olly-3772623.jpg&fm=jpg"
[playerCommand.embed.footer]
text = "And footers"
iconUrl = "https://cdn-prod.medicalnewstoday.com/content/images/articles/324/324336/bones-of-the-foot-infographic-em-image-credit-stephen-kelly-2019-br.jpg"

# This gets triggered whenever CONSOLE runs a command
# The placeholders available are:
# - {player} - Player's name (always CONSOLE)
# - {message} - Command message
[serverCommand]
webhook = "https://discord.com/api/webhooks/123456/abcdef" # remove if you don't want a webhook
content = "The `serverCommand` event was triggered..." # remove if you don't want content
[serverCommand.embed] # remove if you don't want an embed
title = "Server Command"
description = "..."
color = "#FFFFFF"
timestamp = true
[serverCommand.embed.author]
name = "You can also supply authors"
iconUrl = "https://images.pexels.com/photos/3772623/pexels-photo-3772623.jpeg?cs=srgb&dl=pexels-olly-3772623.jpg&fm=jpg"
[serverCommand.embed.footer]
text = "And footers"
iconUrl = "https://cdn-prod.medicalnewstoday.com/content/images/articles/324/324336/bones-of-the-foot-infographic-em-image-credit-stephen-kelly-2019-br.jpg"

# This gets triggered whenever the server starts
# No placeholders available
[start]
webhook = "https://discord.com/api/webhooks/123456/abcdef" # remove if you don't want a webhook
content = "The `start` event was triggered..." # remove if you don't want content
[start.embed] # remove if you don't want an embed
title = "Start"
description = "..."
color = "#FFFFFF"
timestamp = true
[start.embed.author]
name = "You can also supply authors"
iconUrl = "https://images.pexels.com/photos/3772623/pexels-photo-3772623.jpeg?cs=srgb&dl=pexels-olly-3772623.jpg&fm=jpg"
[start.embed.footer]
text = "And footers"
iconUrl = "https://cdn-prod.medicalnewstoday.com/content/images/articles/324/324336/bones-of-the-foot-infographic-em-image-credit-stephen-kelly-2019-br.jpg"

# This gets triggered whenever the server stops
# No placeholders available
[stop]
webhook = "https://discord.com/api/webhooks/123456/abcdef" # remove if you don't want a webhook
content = "The `stop` event was triggered..." # remove if you don't want content
[stop.embed] # remove if you don't want an embed
title = "Stop"
description = "..."
color = "#FFFFFF"
timestamp = true
[stop.embed.author]
name = "You can also supply authors"
iconUrl = "https://images.pexels.com/photos/3772623/pexels-photo-3772623.jpeg?cs=srgb&dl=pexels-olly-3772623.jpg&fm=jpg"
[stop.embed.footer]
text = "And footers"
iconUrl = "https://cdn-prod.medicalnewstoday.com/content/images/articles/324/324336/bones-of-the-foot-infographic-em-image-credit-stephen-kelly-2019-br.jpg"

# This gets triggered whenever the server is restarting
# The placeholders available are:
# - {message} - Restart message
[restart]
webhook = "https://discord.com/api/webhooks/123456/abcdef" # remove if you don't want a webhook
times = ["00:00", "06:00", "12:00", "18:00"] # HH:MM format, in the server's timezone
periods = [30, 10, 5, 4, 3, 2, 1] # minutes before restart
content = "The `restart` event was triggered..." # remove if you don't want content
cron = "* * * * *"
[restart.embed] # remove if you don't want an embed
title = "Restart"
description = "..."
color = "#FFFFFF"
timestamp = true
[restart.embed.author]
name = "You can also supply authors"
iconUrl = "https://images.pexels.com/photos/3772623/pexels-photo-3772623.jpeg?cs=srgb&dl=pexels-olly-3772623.jpg&fm=jpg"
[restart.embed.footer]
text = "And footers"
iconUrl = "https://cdn-prod.medicalnewstoday.com/content/images/articles/324/324336/bones-of-the-foot-infographic-em-image-credit-stephen-kelly-2019-br.jpg"
```
