package com.github.olivi_r.lastlife;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class SetLivesExecutor implements CommandExecutor {
	private LastLifePlugin plugin;
	
	public SetLivesExecutor(LastLifePlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 2) {
			Player p = Bukkit.getPlayer(args[0]);
			Integer lives = Integer.parseInt(args[1]);
			if (p != null && lives != null && lives >= 0) {
				p.getPersistentDataContainer().set(plugin.livesKey, PersistentDataType.INTEGER, lives);
				plugin.refreshTeams();
				return true;
			}
		}
		return false;
	}
}
