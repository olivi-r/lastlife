package com.github.olivi_r.lastlife;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
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
				Player killer = event.getEntity().getKiller();
				if (killer != null && plugin.boogeymen.contains(killer)) {
					plugin.boogeymen.remove(killer);
					killer.sendTitle("", "You have completed the task", 1, 40, 10);
				}
				event.getEntity().getPersistentDataContainer().set(plugin.livesKey, PersistentDataType.INTEGER,
						lives - 1);
			} else {
				event.getEntity().getPersistentDataContainer().set(plugin.livesKey, PersistentDataType.INTEGER, 0);
				event.getEntity().setGameMode(GameMode.SPECTATOR);
			}
			plugin.refreshTeams();
		}
	}
}
