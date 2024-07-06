package com.github.olivi_r.lastlife;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
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
		NamespacedKey key = new NamespacedKey(plugin, "lives");
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.getPersistentDataContainer().remove(key);
		}
		plugin.refreshTeams();
		return true;
	}
}
