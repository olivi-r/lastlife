package com.github.olivi_r.lastlife;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class AddTabCompleter implements TabCompleter {
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> completions = new ArrayList<>();
		if (args.length == 1) {
			for (Player p : Bukkit.matchPlayer(args[0])) {
				completions.add(p.getName());
			}
		}
		Collections.sort(completions);
		return completions;
	}
}
