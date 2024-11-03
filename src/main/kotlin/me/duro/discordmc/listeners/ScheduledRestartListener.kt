package me.duro.discordmc.listeners

import me.duro.discordmc.DiscordMC
import me.duro.discordmc.utils.Webhook
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable
import java.time.Duration
import java.time.LocalDateTime


class ScheduledRestartListener() : Listener {
    init {
        object : BukkitRunnable() {
            override fun run() {
                checkForRestart()
            }
        }.runTaskTimer(DiscordMC.instance, 0L, 20L * 60)
    }

    private fun checkForRestart() {
        val now = LocalDateTime.now()
        val restartTimes = (DiscordMC.instance.toml.restart?.times ?: emptyList()).map {
            val (hours, minutes) = it.split(":").map(String::toInt)
            LocalDateTime.of(now.year, now.month, now.dayOfMonth, hours, minutes)
        }.filter { it.isAfter(now) }

        if (restartTimes.isEmpty()) return

        val nextRestartTime = restartTimes.first()
        val minutesLeft = Duration.between(now, nextRestartTime).toMinutes().toInt()

        if (DiscordMC.instance.toml.restart?.periods?.contains(minutesLeft) == true) {
            val message = when (minutesLeft) {
                0 -> "Server is restarting!"
                else -> "Server restarting in $minutesLeft minutes!"
            }

            Webhook.sendWebhook(
                DiscordMC.instance.toml.restart, mapOf("{message}" to message)
            )
        }
    }
}
