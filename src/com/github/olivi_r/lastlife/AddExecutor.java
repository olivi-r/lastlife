package com.github.olivi_r.lastlife;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class AddExecutor implements CommandExecutor {
	private LastLifePlugin plugin;

	public AddExecutor(LastLifePlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			Player p = Bukkit.getPlayer(args[0]);
			if (p != null) {
				int lives = ThreadLocalRandom.current().nextInt(2, 7);
				p.getPersistentDataContainer().set(plugin.livesKey, PersistentDataType.INTEGER, lives);
				plugin.refreshTeams();
				return true;
			}
		}
		return false;
	}
}
