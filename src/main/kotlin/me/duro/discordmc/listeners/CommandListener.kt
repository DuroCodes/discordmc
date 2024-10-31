package me.duro.discordmc.listeners

import me.duro.discordmc.DiscordMC
import me.duro.discordmc.utils.Webhook
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.server.ServerCommandEvent

class CommandListener : Listener {
    @EventHandler
    fun onServerCommand(event: ServerCommandEvent) {
        val config = DiscordMC.instance.toml.serverCommand

        Webhook.sendWebhook(
            config.webhook, config.embed, config.embedEnabled, config.content, mapOf(
                "{message}" to event.command,
                "{sender}" to event.sender.name,
            )
        )
    }

    @EventHandler
    fun onPlayerCommand(event: PlayerCommandPreprocessEvent) {
        val config = DiscordMC.instance.toml.playerCommand

        Webhook.sendWebhook(
            config.webhook, config.embed, config.embedEnabled, config.content, mapOf(
                *Webhook.playerPlaceholders(event.player).toList().toTypedArray(),
                "{message}" to event.message,
            )
        )
    }
}