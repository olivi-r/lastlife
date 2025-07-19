package com.github.olivi_r.lastlife;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetExecutor implements CommandExecutor {
	private LastLifePlugin plugin;

	public ResetExecutor(LastLifePlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.getPersistentDataContainer().remove(plugin.livesKey);
		}
		plugin.refreshTeams();
		return true;
	}
}
