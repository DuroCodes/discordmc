package me.duro.discordmc.commands

import me.duro.discordmc.DiscordMC
import me.duro.discordmc.utils.ConfigLoader
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

class DiscordMCCommand : CommandExecutor, TabExecutor {
    private val GREEN = "\u001B[32m"
    private val RESET = "\u001B[0m"
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (!sender.hasPermission("discordmc.reload")) {
            sender.sendMessage("§cYou do not have permission to use this command")
            return true
        }

        if (args == null || args.isEmpty()) {
            sender.sendMessage("§cUsage: /discordmc <reload>")
            return true
        }

        when (args[0]) {
            "reload" -> {
                DiscordMC.instance.toml = try {
                    ConfigLoader.loadConfig(DiscordMC.instance)
                        ?: throw Exception("Invalid config.toml. Please check the console for the stack trace.")
                } catch (e: Exception) {
                    sender.sendMessage("§c${e.message}")
                    e.printStackTrace()
                    return true
                }
                sender.sendMessage("§aReloaded DiscordMC config")
                DiscordMC.instance.logger.info("${GREEN}Reloaded DiscordMC config${RESET}")
            }

            else -> {
                sender.sendMessage("§cUsage: /discordmc <reload>")
            }
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender, command: Command, label: String, args: Array<out String>?
    ): List<String> {
        return mutableListOf("reload")
    }
}