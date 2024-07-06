package com.github.olivi_r.lastlife;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class GiveLifeExecutor implements CommandExecutor {
	private LastLifePlugin plugin;
	
	public GiveLifeExecutor(LastLifePlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1 && sender instanceof Player) {
			Player giver = (Player) sender;
			Player receiver = Bukkit.getPlayer(args[0]);
			if (receiver != null) {
				Integer giverLives = giver.getPersistentDataContainer().get(plugin.livesKey, PersistentDataType.INTEGER);
				Integer receiverLives = receiver.getPersistentDataContainer().get(plugin.livesKey, PersistentDataType.INTEGER);
				
				if (giver.equals(receiver) || receiverLives == null) {
					return false;
				}
				
				if (giverLives == null || giverLives < 2) {
					giver.sendMessage(ChatColor.RED + "You don't have enough lives left to give them to someone else.");
					return true;
				}
				
				giver.getPersistentDataContainer().set(plugin.livesKey, PersistentDataType.INTEGER, giverLives - 1);
				receiver.getPersistentDataContainer().set(plugin.livesKey, PersistentDataType.INTEGER, receiverLives + 1);
				giver.playEffect(EntityEffect.TOTEM_RESURRECT);
				receiver.playEffect(EntityEffect.TOTEM_RESURRECT);
				plugin.refreshTeams();
				return true;
			}
		}
		return false;
	}
}
