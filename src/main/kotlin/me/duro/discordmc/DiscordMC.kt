package me.duro.discordmc

import me.duro.discordmc.commands.DiscordMCCommand
import me.duro.discordmc.listeners.*
import me.duro.discordmc.utils.Config
import me.duro.discordmc.utils.ConfigLoader
import me.duro.discordmc.utils.Webhook
import org.bukkit.plugin.java.JavaPlugin

class DiscordMC : JavaPlugin() {
    companion object {
        lateinit var instance: DiscordMC
    }
    lateinit var toml: Config

    override fun onEnable() {
        instance = this
        toml = ConfigLoader.loadConfig(this)!!
        val events =
                arrayOf(
                        ChatListener(),
                        CommandListener(),
                        DeathListener(),
                        JoinQuitListener(),
                        ServerLoadEvent(),
                        ScheduledRestart(this)
                )
        val commands = arrayOf("discordmc" to DiscordMCCommand())

        events.forEach { server.pluginManager.registerEvents(it, this) }

        commands.forEach { this.getCommand(it.first)?.setExecutor(it.second) }
    }

    override fun onDisable() {
        if (!this.server.isStopping) return

        Webhook.sendWebhook(toml.stop)
    }
}
