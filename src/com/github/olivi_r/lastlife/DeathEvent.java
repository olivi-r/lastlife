package com.github.olivi_r.lastlife;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataType;

public class DeathEvent implements Listener {
	private LastLifePlugin plugin;
	
	public DeathEvent(LastLifePlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Integer lives = event.getEntity().getPersistentDataContainer().get(plugin.livesKey, PersistentDataType.INTEGER);
		if (lives != null) {
			if (lives > 1) {
				event.getEntity().getPersistentDataContainer().set(plugin.livesKey, PersistentDataType.INTEGER, lives - 1);
			} else {
				event.getEntity().getPersistentDataContainer().set(plugin.livesKey, PersistentDataType.INTEGER, 0);
				event.getEntity().setGameMode(GameMode.SPECTATOR);
			}
			plugin.refreshTeams();
		}
	}
}
