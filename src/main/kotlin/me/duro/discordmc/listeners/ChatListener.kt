package me.duro.discordmc.listeners

import io.papermc.paper.event.player.AsyncChatEvent
import me.duro.discordmc.DiscordMC
import me.duro.discordmc.utils.Webhook
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class ChatListener : Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    fun onChat(event: AsyncChatEvent) {
        val message = PlainTextComponentSerializer.plainText().serialize(event.message())

        Webhook.sendWebhook(
            DiscordMC.instance.toml.chat, mapOf(
                *Webhook.playerPlaceholders(event.player), "{message}" to message
            )
        )
    }
}