package me.duro.discordmc.listeners

import me.duro.discordmc.DiscordMC
import me.duro.discordmc.utils.Webhook
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class JoinQuitListener : Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        Webhook.sendWebhook(
            DiscordMC.instance.toml.join, mapOf(
                *Webhook.playerPlaceholders(event.player)
            )
        )
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onPlayerQuit(event: PlayerQuitEvent) {
        Webhook.sendWebhook(
            DiscordMC.instance.toml.leave, mapOf(
                *Webhook.playerPlaceholders(event.player)
            )
        )
    }
}