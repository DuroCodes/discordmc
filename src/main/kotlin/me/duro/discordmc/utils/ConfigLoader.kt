package me.duro.discordmc.utils

import cc.ekblad.toml.decode
import org.bukkit.plugin.java.JavaPlugin
import cc.ekblad.toml.tomlMapper
import java.io.File
import java.io.IOException
import java.nio.file.Path

interface WebhookConfig {
    val webhook: String?
    val embed: Webhook.Embed?
    val content: String?
}

data class EventConfig(
    override val webhook: String? = null,
    override val embed: Webhook.Embed? = null,
    override val content: String? = null,
) : WebhookConfig

data class RestartConfig(
    val times: List<String>? = emptyList(),
    val periods: List<Int>? = emptyList(),
    override val webhook: String? = null,
    override val embed: Webhook.Embed? = null,
    override val content: String? = null
) : WebhookConfig

data class Config(
    val chat: EventConfig?,
    val join: EventConfig?,
    val leave: EventConfig?,
    val death: EventConfig?,
    val playerCommand: EventConfig?,
    val serverCommand: EventConfig?,
    val start: EventConfig?,
    val stop: EventConfig?,
    val restart: RestartConfig?
)

object ConfigLoader {
    fun loadConfig(plugin: JavaPlugin) = try {
        val filePath = File(plugin.dataFolder, "config.toml")

        if (!filePath.exists()) {
            filePath.parentFile.mkdirs()
            filePath.writeText(generateConfig())
        }

        tomlMapper { }.decode<Config>(Path.of(filePath.toURI()))
    } catch (e: IOException) {
        plugin.logger.severe("Failed to load config.toml: ${e.message}")
        null
    } catch (e: Exception) {
        plugin.logger.severe("An unexpected error occurred while loading config.toml: ${e.message}")
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
            Triple("stop", "the server stops", null),
            Triple("restart", "the server is restarting", mapOf("{message}" to "Restart message"))
        )

        events.forEach {
            configBuilder.appendLine(generateEventConfig(it.first, it.second, it.third))
        }

        return configBuilder.toString()
    }

    fun titleCase(str: String) = str.split(Regex("(?=[A-Z])"))
        .joinToString(" ") { s -> s.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } }

    private fun generateEventConfig(
        eventName: String, description: String, placeholders: Map<String, String>?
    ): String {
        val placeholdersComment = placeholders?.let { p ->
            "# The placeholders available are:\n${p.entries.joinToString("\n") { "# - ${it.key} - ${it.value}" }}"
        } ?: "# No placeholders available"

        val options = mutableListOf(
            "",
            "# This gets triggered whenever $description",
            placeholdersComment,
            "[$eventName]",
            "webhook = \"https://discord.com/api/webhooks/123456/abcdef\" # remove if you don't want a webhook",
            "content = \"The `$eventName` event was triggered...\" # remove if you don't want content",
            "[$eventName.embed] # remove if you don't want an embed",
            "title = \"${titleCase(eventName)}\"",
            "description = \"...\"",
            "color = \"#FFFFFF\"",
            "timestamp = true",
            "[$eventName.embed.author]",
            "name = \"You can also supply authors\"",
            "iconUrl = \"https://images.pexels.com/photos/3772623/pexels-photo-3772623.jpeg?cs=srgb&dl=pexels-olly-3772623.jpg&fm=jpg\"",
            "[$eventName.embed.footer]",
            "text = \"And footers\"",
            "iconUrl = \"https://cdn-prod.medicalnewstoday.com/content/images/articles/324/324336/bones-of-the-foot-infographic-em-image-credit-stephen-kelly-2019-br.jpg\""
        )

        if (eventName == "restart") {
            val webhookIdx = options.indexOfFirst { it.startsWith("webhook =") }
            if (webhookIdx != -1) {
                options.add(
                    webhookIdx + 1,
                    "times = [\"00:00\", \"06:00\", \"12:00\", \"18:00\"] # HH:MM format, in the server's timezone"
                )
                options.add(webhookIdx + 2, "periods = [30, 10, 5, 4, 3, 2, 1] # minutes before restart")
            }
        }

        return options.joinToString("\n")
    }
}