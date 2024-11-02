package me.duro.discordmc.utils

import cc.ekblad.toml.decode
import org.bukkit.plugin.java.JavaPlugin
import cc.ekblad.toml.tomlMapper
import java.io.File
import java.nio.file.Path

data class EventConfig(
    val webhook: String? = null, val embed: Webhook.Embed? = null, val content: String? = null
)

data class Config(
    val chat: EventConfig?,
    val join: EventConfig?,
    val leave: EventConfig?,
    val death: EventConfig?,
    val playerCommand: EventConfig?,
    val serverCommand: EventConfig?,
    val start: EventConfig?,
    val stop: EventConfig?
)

object ConfigLoader {
    fun loadConfig(plugin: JavaPlugin) = try {
        val filePath = File(plugin.dataFolder, "config.toml")

        if (!filePath.exists()) {
            filePath.parentFile.mkdirs()
            filePath.writeText(generateConfig())
        }

        tomlMapper { }.decode<Config>(Path.of(filePath.toURI()))
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    private fun generateConfig(): String {
        val configBuilder = StringBuilder().apply {
            appendLine("# DiscordMC Configuration")
            appendLine("# This file is written in TOML format (https://toml.io/en/)")
            appendLine("# You can also use placeholder values, for each event specifically")
        }

        val playerPlaceholders = mapOf(
            "{player}" to "Player's name",
            "{x}" to "Player's X coordinate",
            "{y}" to "Player's Y coordinate",
            "{z}" to "Player's Z coordinate",
            "{world}" to "Player's world"
        )

        val events = arrayOf(
            Triple("chat", "a player sends a chat message", playerPlaceholders + mapOf("{message}" to "Chat message")),
            Triple("join", "a player joins the server", playerPlaceholders),
            Triple("leave", "a player leaves the server", playerPlaceholders),
            Triple("death", "a player dies", playerPlaceholders + mapOf("{message}" to "Death message")),
            Triple(
                "playerCommand", "a player runs a command", playerPlaceholders + mapOf("{message}" to "Command message")
            ),
            Triple(
                "serverCommand", "CONSOLE runs a command", mapOf(
                    "{player}" to "Player's name (always CONSOLE)", "{message}" to "Command message"
                )
            ),
            Triple("start", "the server starts", null),
            Triple("stop", "the server stops", null)
        )

        events.forEach {
            configBuilder.appendLine(generateEventConfig(it.first, it.second, it.third))
        }

        return configBuilder.toString()
    }

    private fun generateEventConfig(
        eventName: String, description: String, placeholders: Map<String, String>?
    ): String {
        val placeholdersComment = placeholders?.let { p ->
            "# The placeholders available are:\n${p.entries.joinToString("\n") { "# - ${it.key} - ${it.value}" }}"
        } ?: "# No placeholders available"

        return arrayOf(
            "",
            "# This gets triggered whenever $description",
            placeholdersComment,
            "[$eventName]",
            "webhook = \"https://discord.com/api/webhooks/123456/abcdef\" # remove if you don't want a webhook",
            "content = \"\" # remove if you don't want content",
            "[$eventName.embed] # remove if you don't want an embed",
            "title = \"$eventName\"",
            "description = \"\"",
            "color = \"#FFFFFF\"",
            "timestamp = true",
            "[$eventName.embed.author]",
            "name = \"{player}\"",
            "iconUrl = \"https://minotar.net/helm/{player}/512.png\"",
            "[$eventName.embed.footer]",
            "text = \"$eventName\"",
            "iconUrl = \"https://cdn.discordapp.com/embed/avatars/0.png\""
        ).joinToString("\n")
    }
}