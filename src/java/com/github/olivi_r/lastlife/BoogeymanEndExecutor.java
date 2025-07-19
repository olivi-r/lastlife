package com.github.olivi_r.lastlife;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.persistence.PersistentDataType;

public class BoogeymanEndExecutor implements CommandExecutor {
	private LastLifePlugin plugin;

	public BoogeymanEndExecutor(LastLifePlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		plugin.boogeymen.forEach(p -> {
			int lives = p.getPersistentDataContainer().get(plugin.livesKey, PersistentDataType.INTEGER);
			if (lives > 0) {
				p.getPersistentDataContainer().set(plugin.livesKey, PersistentDataType.INTEGER, 1);
				p.playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 10, 1);
				p.sendTitle(ChatColor.RED + "You failed your task", "You are now a red life", 1, 40, 10);
			}
		});
		plugin.refreshTeams();
		plugin.boogeymen.clear();
		return true;
	}
}
