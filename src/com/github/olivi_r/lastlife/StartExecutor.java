package com.github.olivi_r.lastlife;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class StartExecutor implements CommandExecutor {
	private LastLifePlugin plugin;
	
	public StartExecutor(LastLifePlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			int lives = ThreadLocalRandom.current().nextInt(2, 7);
			p.getPersistentDataContainer().set(plugin.livesKey, PersistentDataType.INTEGER, lives);
		}
		plugin.refreshTeams();
		return true;
	}
}
