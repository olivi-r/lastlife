package com.github.olivi_r.lastlife;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class LastLifePlugin extends JavaPlugin {
	Team darkGreenTeam, greenTeam, yellowTeam, redTeam, blackTeam;
	NamespacedKey livesKey;
	
	public void refreshTeams() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			Integer lives = p.getPersistentDataContainer().get(livesKey, PersistentDataType.INTEGER);
			
			if (lives == null) {
				for (Team t : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()) {
					if (t.hasEntry(p.getName())) {
						t.removeEntry(p.getName());
					}
				}
			} else if (lives == 0) {
				this.blackTeam.addEntry(p.getName());
			} else if (lives == 1) {
				this.redTeam.addEntry(p.getName());
			} else if (lives == 2) {
				this.yellowTeam.addEntry(p.getName());
			} else if (lives == 3) {
				this.greenTeam.addEntry(p.getName());
			} else {
				this.darkGreenTeam.addEntry(p.getName());
			}
		}
	}
	
	@Override
	public void onEnable() {
		livesKey = new NamespacedKey(this, "lives");
		getServer().getPluginManager().registerEvents(new DeathEvent(this), this);
		
		// remove existing teams
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		
		// create teams
		darkGreenTeam = board.getTeam("darkGreen");
		greenTeam = board.getTeam("green");
		yellowTeam = board.getTeam("yellow");
		redTeam = board.getTeam("red");
		blackTeam = board.getTeam("black");
		if (darkGreenTeam == null) {
			darkGreenTeam = board.registerNewTeam("darkGreen");
			darkGreenTeam.setColor(ChatColor.DARK_GREEN);
		}
		if (greenTeam == null) {
			greenTeam = board.registerNewTeam("green");
			greenTeam.setColor(ChatColor.GREEN);
		}
		if (yellowTeam == null) {
			yellowTeam = board.registerNewTeam("yellow");
			yellowTeam.setColor(ChatColor.YELLOW);
		}
		if (redTeam == null) {
			redTeam = board.registerNewTeam("red");
			redTeam.setColor(ChatColor.RED);
		}
		if (blackTeam == null) {
			blackTeam = board.registerNewTeam("black");
			blackTeam.setColor(ChatColor.BLACK);
		}
		
		// register command executors and tab completers
		getCommand("add").setExecutor(new AddExecutor(this));
		getCommand("add").setTabCompleter(new AddTabCompleter());
		getCommand("givelife").setExecutor(new GiveLifeExecutor(this));
		getCommand("givelife").setTabCompleter(new GiveLifeTabCompleter());
		getCommand("lives").setExecutor(new LivesExecutor(this));
		getCommand("lives").setTabCompleter(new NoArgsTabCompleter());
		getCommand("reset").setExecutor(new ResetExecutor(this));
		getCommand("reset").setTabCompleter(new NoArgsTabCompleter());
		getCommand("setlives").setExecutor(new SetLivesExecutor(this));
		getCommand("setlives").setTabCompleter(new SetLivesTabCompleter());
		getCommand("start").setExecutor(new StartExecutor(this));
		getCommand("start").setTabCompleter(new NoArgsTabCompleter());
	}
}
