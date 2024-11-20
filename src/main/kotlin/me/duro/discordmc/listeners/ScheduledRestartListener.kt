package me.duro.discordmc.listeners

import me.duro.discordmc.DiscordMC
import me.duro.discordmc.utils.Webhook
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import com.cronutils.model.definition.CronDefinitionBuilder
import com.cronutils.model.time.ExecutionTime
import com.cronutils.parser.CronParser
import com.cronutils.model.CronType

class ScheduledRestartListener : Listener {

    private val config = DiscordMC.instance.toml.restart

    init {
        object : BukkitRunnable() {
            override fun run() {
                checkForRestart()
            }
        }.runTaskTimer(DiscordMC.instance, 0L, 20L * 60)
    }

    private fun checkForRestart() {
        val now = LocalDateTime.now()
        val zoneId = ZoneId.systemDefault()
        val cronExpression = config?.cron

        cronExpression?.takeIf { it != "* * * * *" }?.let {
            try {
                val cron = CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX)).parse(it)
                ExecutionTime.forCron(cron).nextExecution(now.atZone(zoneId))?.ifPresent { next ->
                    handleRestart(now, next.toLocalDateTime())
                }
            } catch (e: Exception) {
                println("Error parsing cron: ${e.message}")
            }
        }

        (config?.times ?: emptyList()).mapNotNull { parseTime(it, now) }
            .firstOrNull { it.isAfter(now) || Duration.between(now, it).toMinutes().toInt() == 0 }
            ?.let { handleRestart(now, it) }
    }

    private fun parseTime(time: String, now: LocalDateTime): LocalDateTime? {
        return try {
            val (hours, minutes) = time.split(":").map(String::toInt)
            LocalDateTime.of(now.year, now.month, now.dayOfMonth, hours, minutes)
        } catch (e: Exception) {
            null
        }
    }

    private fun handleRestart(now: LocalDateTime, time: LocalDateTime) {
        val minutesLeft = Duration.between(now, time).toMinutes().toInt()
        if (config?.periods?.contains(minutesLeft) == true || minutesLeft == 0) {
            val message = when (minutesLeft) {
                0 -> "Server is restarting now!"
                5 -> "Detected 5 minutes until restart, initiating server backup..."
                else -> "Server restarting in $minutesLeft minutes!"
            }
            Webhook.sendWebhook(config, mapOf("{message}" to message))
        }
    }
}
