package me.duro.discordmc.listeners

import me.duro.discordmc.DiscordMC
import me.duro.discordmc.utils.Webhook
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class JoinQuitListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val config = DiscordMC.instance.toml.join

        Webhook.sendWebhook(
            config.webhook, config.embed, config.embedEnabled, config.content, mapOf(
                *Webhook.playerPlaceholders(event.player)
            )
        )
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val config = DiscordMC.instance.toml.leave

        Webhook.sendWebhook(
            config.webhook, config.embed, config.embedEnabled, config.content, mapOf(
                *Webhook.playerPlaceholders(event.player)
            )
        )
    }
}