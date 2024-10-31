package me.duro.discordmc.utils

import org.bukkit.entity.Player
import org.json.simple.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URI
import java.time.OffsetDateTime

object Webhook {
    data class Embed(
        val title: String? = null,
        val description: String,
        val color: String? = null,
        val author: Author? = null,
        val footer: Footer? = null,
        val timestamp: Boolean? = false
    ) {
        data class Footer(
            val text: String? = null, val iconUrl: String? = null, val proxyIconUrl: String? = null
        )

        data class Author(
            val name: String? = null,
            val url: String? = null,
            val iconUrl: String? = null,
            val proxyIconUrl: String? = null
        )
    }

    private fun escapeFormatting(input: String) =
        input.replace("""([*_|~>`])|(^#{1,3})""".toRegex(RegexOption.MULTILINE)) {
            "\\${it.value}"
        }

    private fun substitutePlaceholders(input: String, placeholders: Map<String, String>) =
        placeholders.entries.fold(input) { acc, (key, value) -> acc.replace(key, value) }

    fun playerPlaceholders(player: Player) = mapOf(
        "{player}" to player.name,
        "{x}" to player.location.blockX.toString(),
        "{y}" to player.location.blockY.toString(),
        "{z}" to player.location.blockZ.toString(),
        "{world}" to player.world.name
    ).toList().toTypedArray()

    fun sendWebhook(
        webhookUrl: String,
        embed: Embed,
        embedEnabled: Boolean,
        content: String?,
        placeholders: Map<String, String> = emptyMap()
    ) {
        val url = URI(webhookUrl).toURL()
        val connection = url.openConnection() as HttpURLConnection

        try {
            connection.apply {
                requestMethod = "POST"
                doOutput = true
                setRequestProperty("Content-Type", "application/json")
            }

            val embedJson = JSONObject().apply {
                put("title", substitutePlaceholders(embed.title ?: "", placeholders))
                put("description", escapeFormatting(substitutePlaceholders(embed.description, placeholders)))
                embed.color?.let {
                    put(
                        "color", substitutePlaceholders(it, placeholders).removePrefix("#").toIntOrNull(16)
                    )
                }

                embed.author?.let { author ->
                    put("author", JSONObject().apply {
                        author.name?.let { put("name", substitutePlaceholders(it, placeholders)) }
                        author.url?.let { put("url", substitutePlaceholders(it, placeholders)) }
                        author.iconUrl?.let { put("icon_url", substitutePlaceholders(it, placeholders)) }
                        author.proxyIconUrl?.let { put("proxy_icon_url", substitutePlaceholders(it, placeholders)) }
                    })
                }

                embed.footer?.let { footer ->
                    put("footer", JSONObject().apply {
                        footer.text?.let { put("text", substitutePlaceholders(it, placeholders)) }
                        footer.iconUrl?.let { put("icon_url", substitutePlaceholders(it, placeholders)) }
                        footer.proxyIconUrl?.let { put("proxy_icon_url", substitutePlaceholders(it, placeholders)) }
                    })
                }

                if (embed.timestamp == true) put("timestamp", OffsetDateTime.now().toString())
            }

            val payload = JSONObject().apply {
                if (embedEnabled) put("embeds", listOf(embedJson))
                content?.let { put("content", escapeFormatting(substitutePlaceholders(it, placeholders))) }
            }

            val writer = OutputStreamWriter(connection.outputStream)
            writer.use { it.write(payload.toString()) }

            if (connection.responseCode != HttpURLConnection.HTTP_NO_CONTENT) {
                println("Failed to send webhook")
                println("Response code: ${connection.responseCode}")
                println("Response message: ${connection.responseMessage}")
            }
        } finally {
            connection.disconnect()
        }
    }
}
