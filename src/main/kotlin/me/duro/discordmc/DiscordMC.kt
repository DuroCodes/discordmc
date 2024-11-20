package me.duro.discordmc

import me.duro.discordmc.commands.DiscordMCCommand
import me.duro.discordmc.listeners.*
import me.duro.discordmc.utils.Config
import me.duro.discordmc.utils.ConfigLoader
import me.duro.discordmc.utils.Webhook
import org.bukkit.plugin.java.JavaPlugin

class DiscordMC : JavaPlugin() {
    lateinit var toml: Config

    override fun onEnable() {
        instance = this

        toml = try {
            ConfigLoader.loadConfig(this)!!
        } catch (e: Exception) {
            logger.severe("Failed to load config.toml: ${e.message}")
            e.printStackTrace()
            return
        }

        val commands = arrayOf("discordmc" to DiscordMCCommand())
        val events = arrayOf(
            ChatListener(),
            CommandListener(),
            DeathListener(),
            JoinQuitListener(),
            ServerLoadListener(),
            ScheduledRestartListener()
        )

        commands.forEach { getCommand(it.first)?.setExecutor(it.second) }
        events.forEach { server.pluginManager.registerEvents(it, this) }
        this.logger.info("Enabled Plugin")
    }

    override fun onDisable() {
        if (!this.server.isStopping) return

        Webhook.sendWebhook(toml.stop)
    }

    companion object {
        lateinit var instance: DiscordMC
    }
}
