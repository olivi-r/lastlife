package com.github.olivi_r.lastlife;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class BoogeymanExecutor implements CommandExecutor {
	private LastLifePlugin plugin;

	public BoogeymanExecutor(LastLifePlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		ArrayList<Player> available = new ArrayList<Player>();
		Bukkit.getOnlinePlayers().forEach(p -> {
			Integer lives = p.getPersistentDataContainer().get(plugin.livesKey, PersistentDataType.INTEGER);
			if (lives != null && lives > 1) {
				available.add(p);
			}
			;
		});

		plugin.boogeymen.clear();

		if (available.size() > 1) {
			int boogey0 = ThreadLocalRandom.current().nextInt(available.size());
			plugin.boogeymen.add(available.get(boogey0));
			available.get(boogey0).sendTitle(ChatColor.RED + "Boogeyman!", "Kill 1 Non-Red Player", 1, 40, 10);

			if (ThreadLocalRandom.current().nextInt(8) == 0 && available.size() > 3) {
				int boogey1 = -1;
				do {
					boogey1 = ThreadLocalRandom.current().nextInt(available.size());
				} while (boogey1 == boogey0);
				plugin.boogeymen.add(available.get(boogey1));
				available.get(boogey1).sendTitle(ChatColor.RED + "Boogeyman!", "Kill 1 Non-Red Player", 1, 40, 10);
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Not enough non-red players online");
		}
		return true;
	}
}
