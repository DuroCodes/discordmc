package me.duro.discordmc.listeners

import me.duro.discordmc.DiscordMC
import me.duro.discordmc.utils.Webhook
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class DeathListener : Listener {
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val config = DiscordMC.instance.toml.death
        val deathMessage = event.deathMessage()?.let {
            PlainTextComponentSerializer.plainText().serialize(it)
        } ?: "${event.entity.name} died"

        Webhook.sendWebhook(
            config.webhook, config.embed, config.embedEnabled, config.content, mapOf(
                *Webhook.playerPlaceholders(event.entity),
                "{message}" to deathMessage,
            )
        )
    }
}