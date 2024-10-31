package me.duro.discordmc.utils

import cc.ekblad.toml.decode
import org.bukkit.plugin.java.JavaPlugin
import cc.ekblad.toml.tomlMapper
import java.io.File
import java.nio.file.Path

data class EventConfig(
    val webhook: String, val embed: Webhook.Embed, val embedEnabled: Boolean, val content: String?
)

data class Config(
    val chat: EventConfig,
    val join: EventConfig,
    val leave: EventConfig,
    val death: EventConfig,
    val playerCommand: EventConfig,
    val serverCommand: EventConfig
)

object ConfigLoader {
    fun loadConfig(plugin: JavaPlugin) = try {
        val filePath = File(plugin.dataFolder, "config.toml")

        if (!filePath.exists()) {
            filePath.parentFile.mkdirs()
            filePath.writeText(
                """
                    # DiscordMC Configuration
                    # This file is written in TOML format (https://toml.io/en/)
                    # You can also use placeholder values, specified in the comments below for each event specifically
                    
                    # This gets ran whenever a player sends a chat message
                    # The placeholders available are:
                    # - {player} - Player's name
                    # - {message} - Chat message
                    # - {x} | {y} | {z} - Player's coordinates
                    # - {world} - Player's world
                    [chat]
                    webhook = "https://discord.com/api/webhooks/123456/abcdef"
                    content = "{player}: {message}" # optional, remove if you don't want a content
                    embedEnabled = true
                    [chat.embed]
                    title = "Chat Message"
                    description = "{message}"
                    color = "#FFC0CB"
                    timestamp = true
                    [chat.embed.author]
                    name = "{player}"
                    iconUrl = "https://minotar.net/helm/{player}/512.png"
                    [chat.embed.footer]
                    text = "Chat"
                    iconUrl = "https://cdn.discordapp.com/embed/avatars/0.png"
                    
                    # This gets ran whenever a player joins the server
                    # The placeholders available are:
                    # - {player} - Player's name
                    # - {x} | {y} | {z} - Player's coordinates
                    # - {world} - Player's world
                    [join]
                    webhook = "https://discord.com/api/webhooks/123456/abcdef"
                    content = "{player} joined the server" # optional, remove if you don't want a content
                    embedEnabled = true
                    [join.embed]
                    title = "Player Join"
                    description = "Joined the server"
                    color = "#FFC0CB"
                    timestamp = true
                    [join.embed.author]
                    name = "{player}"
                    iconUrl = "https://minotar.net/helm/{player}/512.png"
                    [join.embed.footer]
                    text = "Join"
                    iconUrl = "https://cdn.discordapp.com/embed/avatars/0.png"
                    
                    # This gets ran whenever a player leaves the server
                    # The placeholders available are:
                    # - {player} - Player's name
                    # - {x} | {y} | {z} - Player's coordinates
                    # - {world} - Player's world
                    [leave]
                    webhook = "https://discord.com/api/webhooks/123456/abcdef"
                    content = "{player} left the server" # optional, remove if you don't want a content
                    embedEnabled = true
                    [leave.embed]
                    title = "Player Leave"
                    description = "Left the server"
                    color = "#FFC0CB"
                    timestamp = true
                    [leave.embed.author]
                    name = "{player}"
                    iconUrl = "https://minotar.net/helm/{player}/512.png"
                    [leave.embed.footer]
                    text = "Leave"
                    iconUrl = "https://cdn.discordapp.com/embed/avatars/0.png"
                    
                    # This gets ran whenever a player dies
                    # The placeholders available are:
                    # - {player} - Player's name
                    # - {message} - Death message
                    # - {x} | {y} | {z} - Player's coordinates
                    # - {world} - Player's world
                    [death]
                    webhook = "https://discord.com/api/webhooks/123456/abcdef"
                    content = "{message}" # optional, remove if you don't want a content
                    embedEnabled = true
                    [death.embed]
                    title = "Player Death"
                    description = "{message}"
                    color = "#FFC0CB"
                    timestamp = true
                    [death.embed.author]
                    name = "{player}"
                    iconUrl = "https://minotar.net/helm/{player}/512.png"
                    [death.embed.footer]
                    text = "Death"
                    iconUrl = "https://cdn.discordapp.com/embed/avatars/0.png"
                    
                    # This gets ran whenever a player runs a command
                    # The placeholders available are:
                    # - {player} - Command sender's name
                    # - {message} - Command message
                    # - {x} | {y} | {z} - Player's coordinates
                    # - {world} - Player's world
                    [playerCommand]
                    webhook = "https://discord.com/api/webhooks/123456/abcdef"
                    content = "{player}: {message}" # optional, remove if you don't want a content
                    embedEnabled = true
                    [playerCommand.embed]
                    title = "Command"
                    description = "{message}"
                    color = "#FFC0CB"
                    timestamp = true
                    [playerCommand.embed.author]
                    name = "{player}"
                    iconUrl = "https://minotar.net/helm/{player}/512.png"
                    [playerCommand.embed.footer]
                    text = "Command"
                    iconUrl = "https://cdn.discordapp.com/embed/avatars/0.png"
                    
                    # This gets ran whenever CONSOLE runs a command
                    # The placeholders available are:
                    # - {player} - Command sender's name (although it's always "CONSOLE")
                    # - {message} - Command message
                    [serverCommand]
                    webhook = "https://discord.com/api/webhooks/123456/abcdef"
                    content = "{player}: {message}" # optional, remove if you don't want a content
                    embedEnabled = true
                    [serverCommand.embed]
                    title = "Server Command"
                    description = "{message}"
                    color = "#FFC0CB"
                    timestamp = true
                    [serverCommand.embed.author]
                    name = "{player}"
                    iconUrl = "https://minotar.net/helm/{player}/512.png"
                    [serverCommand.embed.footer]
                    text = "Server Command"
                    iconUrl = "https://cdn.discordapp.com/embed/avatars/0.png"
                """.trimIndent()
            )
        }

        tomlMapper { }.decode<Config>(Path.of(filePath.toURI()))
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}