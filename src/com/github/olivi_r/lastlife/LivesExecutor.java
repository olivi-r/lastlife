package com.github.olivi_r.lastlife;

import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class LivesExecutor implements CommandExecutor {
	private LastLifePlugin plugin;
	
	public LivesExecutor(LastLifePlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			NamespacedKey key = new NamespacedKey(plugin, "lives");
			Integer lives = p.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
			
			if (lives == null) {
				p.sendMessage("No lives set.");
			} else if (lives == 1) {
				p.sendMessage("You have 1 life remaining.");
			} else {
				p.sendMessage(String.format("You have %d lives remaining.", lives));
			}
			return true;
		}
		return false;
	}
}
