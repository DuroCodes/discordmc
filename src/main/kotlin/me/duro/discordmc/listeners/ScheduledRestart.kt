package me.duro.discordmc.listeners

import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import me.duro.discordmc.DiscordMC
import me.duro.discordmc.utils.Webhook
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.entity.Player



class ScheduledRestart(private val plugin: Plugin) : Listener {
    val config = DiscordMC.instance.toml.restart
    private var restartTimes: List<String>? = config?.times
    private var nextRestartTime: LocalDateTime = calculateNextRestartTime(restartTimes!!)
    private var lastCountdownMessageTime: Duration? = null
    private var isBackupTriggered = false 

    init {

        startPeriodicCheck() // Start the periodic restart check
    }

    private fun calculateNextRestartTime(restartTimes: List<String>): LocalDateTime {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm") // Define the format for parsing
    
        // Convert List<String> to List<LocalTime>
        val parsedRestartTimes: List<LocalTime> = restartTimes.map { timeString ->
            LocalTime.parse(timeString, formatter)
        }
    
        // Find the next restart time
        val nextRestart = parsedRestartTimes.find { time ->
            now.toLocalTime().isBefore(time)
        } ?: parsedRestartTimes.first() // Fallback to the first restart time if none are found
    
        return now.with(nextRestart) // Use the found time
    }

    private fun startPeriodicCheck() {
        object : BukkitRunnable() {
            override fun run() {
                checkForUpcomingRestart() // Regular restart check
            }
        }.runTaskTimer(plugin, 0L, 20L * 60) // Run every 60 seconds
    }

    private fun performBackup() {
        val backupRunnable = Runnable {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ebackup backup")
        }

        Bukkit.getScheduler().runTask(plugin, backupRunnable)
    }

    private fun stopServer() {
        val stopRunnable = Runnable {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop")
        }

        Bukkit.getScheduler().runTask(plugin, stopRunnable)
    }

    private fun sendWarning(message: String) {
        Bukkit.getOnlinePlayers().forEach { player ->
            player.sendMessage("§c[Server Notice] $message")
        }
    }

    private fun sendHook(msg: String) {
        
        Webhook.sendWebhook(
            DiscordMC.instance.toml.restart,
            mapOf("{message}" to msg),
          )
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        checkForUpcomingRestart(event.player)
    }
    
    private fun checkForUpcomingRestart(player: org.bukkit.entity.Player? = null) {
        val now = LocalDateTime.now()
        val timeToRestart = Duration.between(now, nextRestartTime)

        val minutesLeft = timeToRestart.toMinutes().toInt()

        var msg = "Server will restart in $minutesLeft minutes!"
        when {
            minutesLeft >= 31 -> {
                player?.sendMessage("§aNo imminent restarts. Next restart is in $minutesLeft minutes.")
            }            
            minutesLeft in listOf(30, 20, 10, 5) -> {
                player?.sendMessage(msg)
                sendWarning(msg)
                sendHook(msg)
                lastCountdownMessageTime = timeToRestart
            }
            minutesLeft == 2 -> {
                if (!isBackupTriggered) {
                    sendWarning("Server backup is being performed now.")
                    performBackup()
                    isBackupTriggered = true
                }
            }
            minutesLeft in 1..4 -> {
                if (lastCountdownMessageTime == null || lastCountdownMessageTime != timeToRestart) {
                    player?.sendMessage(msg)
                    sendWarning(msg)
                    sendHook(msg)
                    lastCountdownMessageTime = timeToRestart
                }
            }

            minutesLeft == 0 -> {
                sendWarning("Server is restarting now!")
                sendHook("Server is restarting now!")
                stopServer()
                nextRestartTime = calculateNextRestartTime(restartTimes!!)
            }
        }
    }
}
