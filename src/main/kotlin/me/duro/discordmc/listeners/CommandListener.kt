package me.duro.discordmc.listeners

import me.duro.discordmc.DiscordMC
import me.duro.discordmc.utils.Webhook
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.server.ServerCommandEvent

class CommandListener : Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    fun onServerCommand(event: ServerCommandEvent) {

        Webhook.sendWebhook(
            DiscordMC.instance.toml.serverCommand, mapOf(
                "{message}" to event.command,
                "{sender}" to event.sender.name,
            )
        )
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onPlayerCommand(event: PlayerCommandPreprocessEvent) {

        Webhook.sendWebhook(
            DiscordMC.instance.toml.playerCommand, mapOf(
                *Webhook.playerPlaceholders(event.player).toList().toTypedArray(),
                "{message}" to event.message,
            )
        )
    }
}