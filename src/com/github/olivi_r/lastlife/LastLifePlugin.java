package com.github.olivi_r.lastlife;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
//import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
//import org.bukkit.inventory.ShapedRecipe;
//import org.bukkit.inventory.ShapelessRecipe;
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
		
		// remove recipes
		List<Material> removed = new ArrayList<>();
		removed.add(Material.BOOKSHELF);
		removed.add(Material.ENCHANTING_TABLE);
		
		Iterator<Recipe> recipes = getServer().recipeIterator();
		Recipe recipe;
		while (recipes.hasNext()) {
			recipe = recipes.next();
			if (recipe != null && removed.contains(recipe.getResult().getType())) {
				recipes.remove();
			}
		}
//		
//		// tnt
//		ItemStack tntStack = new ItemStack(Material.TNT);
//		NamespacedKey tntKey = new NamespacedKey(this, "tnt");
//		ShapedRecipe tntRecipe = new ShapedRecipe(tntKey, tntStack);
//		tntRecipe.shape("ABA", "BCB", "ABA");
//		tntRecipe.setIngredient('A', Material.PAPER);
//		tntRecipe.setIngredient('B', Material.SAND);
//		tntRecipe.setIngredient('C', Material.GUNPOWDER);
//		Bukkit.addRecipe(tntRecipe);
//		
//		// name tag
//		ItemStack nametagStack = new ItemStack(Material.NAME_TAG);
//		NamespacedKey nametagKey = new NamespacedKey(this, "nametag");
//		ShapelessRecipe nametagRecipe = new ShapelessRecipe(nametagKey, nametagStack);
//		nametagRecipe.addIngredient(Material.PAPER);
//		nametagRecipe.addIngredient(Material.STRING);
//		Bukkit.addRecipe(nametagRecipe);
//		
//		// moss
//		ItemStack mossStack = new ItemStack(Material.MOSS_BLOCK);
//		NamespacedKey mossKey = new NamespacedKey(this, "moss");
//		ShapelessRecipe mossRecipe = new ShapelessRecipe(mossKey, mossStack);
//		mossRecipe.addIngredient(Material.BONE_MEAL);
//		mossRecipe.addIngredient(Material.WHEAT_SEEDS);
//		Bukkit.addRecipe(mossRecipe);
	}
}
