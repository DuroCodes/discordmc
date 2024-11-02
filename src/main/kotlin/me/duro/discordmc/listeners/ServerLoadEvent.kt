package me.duro.discordmc.listeners

import me.duro.discordmc.DiscordMC
import me.duro.discordmc.utils.Webhook
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerLoadEvent

class ServerLoadEvent : Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    fun onServerLoad(event: ServerLoadEvent) {
        if (event.type != ServerLoadEvent.LoadType.STARTUP) return

        Webhook.sendWebhook(DiscordMC.instance.toml.start)
    }
}