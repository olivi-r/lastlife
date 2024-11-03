package com.github.olivi_r.lastlife;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.persistence.PersistentDataType;

public class StartExecutor implements CommandExecutor {
	private LastLifePlugin plugin;

	public StartExecutor(LastLifePlugin plugin) {
		this.plugin = plugin;
	}

	String colorize(int num) {
		String strNum = Integer.toString(num);
		if (num == 2) {
			return ChatColor.YELLOW + strNum;
		} else if (num == 3) {
			return ChatColor.GREEN + strNum;
		} else {
			return ChatColor.DARK_GREEN + strNum;
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		new Thread(new Runnable() {
			public void run() {
				try {
					Bukkit.getOnlinePlayers().forEach(p -> {
						p.sendTitle(ChatColor.GRAY + "You will have...", "", 20, 40, 0);
					});
					Thread.sleep(3000);
					for (int i = 0; i < 30; i++) {
						String roll = colorize(ThreadLocalRandom.current().nextInt(2, 7));
						Bukkit.getOnlinePlayers().forEach(p -> {
							p.playSound(p.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 10, 1);
							p.sendTitle(roll, "", 1, 2, 1);
						});
						Thread.sleep(100);
					}
					for (int i = 0; i < 15; i++) {
						String roll = colorize(ThreadLocalRandom.current().nextInt(2, 7));
						Bukkit.getOnlinePlayers().forEach(p -> {
							p.playSound(p.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 10, 1);
							p.sendTitle(roll, "", 1, 5, 1);
						});
						Thread.sleep(250);
					}
					for (int i = 0; i < 3; i++) {
						String roll = colorize(ThreadLocalRandom.current().nextInt(2, 7));
						Bukkit.getOnlinePlayers().forEach(p -> {
							p.playSound(p.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 10, 1);
							p.sendTitle(roll, "", 1, 10, 1);
						});
						Thread.sleep(500);
					}
					for (int i = 0; i < 2; i++) {
						String roll = colorize(ThreadLocalRandom.current().nextInt(2, 7));
						Bukkit.getOnlinePlayers().forEach(p -> {
							p.playSound(p.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 10, 1);
							p.sendTitle(roll, "", 1, 15, 1);
						});
						Thread.sleep(750);
					}
					Bukkit.getOnlinePlayers().forEach(p -> {
						new Thread(() -> {
							try {
								int lives = ThreadLocalRandom.current().nextInt(2, 7);
								String roll = colorize(lives);
								p.playSound(p.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 10, 1);
								p.sendTitle(roll, "", 1, 20, 1);
								Thread.sleep(1000);
								p.playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 10, 1);
								p.sendTitle(roll + ChatColor.GREEN + " lives.", "", 1, 20, 10);
								p.getPersistentDataContainer().set(plugin.livesKey, PersistentDataType.INTEGER, lives);
							} catch (InterruptedException e) {
								Thread.currentThread().interrupt();
							}
						}).start();
					});
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}).start();
		return true;
	}
}
