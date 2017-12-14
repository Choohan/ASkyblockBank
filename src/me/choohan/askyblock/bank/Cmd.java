package me.choohan.askyblock.bank;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.plugins.Economy_Essentials;
import net.milkbowl.vault.economy.plugins.Economy_TAEcon.EconomyServerListener;


public class Cmd implements CommandExecutor {
	
	// Define main and plugin variable
	Main plugin;

	public Cmd(Main instance) {
		plugin = instance;
		}
	
	public boolean isInt(String str) {
	    try {
	        Integer.parseInt(str);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandlabel, String[] args) {

		Player p = (Player) sender;
		if(args.length == 0){
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8-=[ &a&lx &8]=- &8[ &6<server> &8] -=[ &a&lx &8]=-").replaceAll("<server>", Main.plugin.getConfig().getString("ServerName")));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', " "));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank help &f- &7Command List"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank balance &f- &7Check island balance"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank withdraw &f- &7Withdraw money from island"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank deposit &f- &7Top up island bank"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', " "));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8-=[ &a&lx &8]=- &8[ &6BANK &8] -=[ &a&lx &8]=-"));
	    return true;
		}
		
		if (args[0].equalsIgnoreCase("balance")) {
			String puuid = p.getUniqueId().toString();
			if (Main.plugin.getIslandConfig().get(puuid) == null){
				String mowner =  Main.plugin.getPlayerConfig().getString(puuid);
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l] &fYour island balance:&6 " + Main.plugin.getIslandConfig().getDouble(mowner)));
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l]&f Your island balance:&6 " + Main.plugin.getIslandConfig().getDouble(puuid)));
			}
			return true; 
		}else
			if(args[0].equalsIgnoreCase("time")){
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss ");
				Date date = new Date();
				String dt = dateFormat.format(date);
				ChatUtilities.message(p, dt);
				String puuid = p.getUniqueId().toString();
				if (Main.plugin.getIslandConfig().get(puuid) == null){
					String mowner =  Main.plugin.getPlayerConfig().getString(puuid);
					Main.plugin.getLogConfig().set("Logs." + mowner + "." +  dt + "." +  p.getName(), 0);

					ChatUtilities.message(p, "setted");
			        Main.plugin.saveDefaultConfig();
			        
			        try {
			           Main.plugin.getLogConfig().save(Main.plugin.getLogConfigFile());
			        } catch (IOException fpe) {
			           //Whatever you want to print/etc for error purposes
			        }
				}else{
					ChatUtilities.message(p, "setted");

					Main.plugin.getLogConfig().set("Logs." + puuid + "." +  dt + "." +  p.getName(), 0);

			        Main.plugin.saveDefaultConfig();
			        
			        try {
			           Main.plugin.getLogConfig().save(Main.plugin.getLogConfigFile());
			        } catch (IOException fpe) {
			           //Whatever you want to print/etc for error purposes
			        }
				}
				
				return true;
			}else
		if (args[0].equalsIgnoreCase("withdraw")){
			
			if (args.length == 2){
			
			
			
			String puuid = p.getUniqueId().toString();
			if (isInt(args[1])){
			double money = Double.parseDouble(args[1]);
			if (Main.plugin.getIslandConfig().get(puuid) == null){
				String mowner =  Main.plugin.getPlayerConfig().getString(puuid);
				double omoney = Main.plugin.getIslandConfig().getDouble(mowner);
				if (omoney >= money){
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss ");
					Date date = new Date();
					String dt = dateFormat.format(date);
					

					Main.plugin.getLogConfig().set("Logs." + mowner + "." +  dt + "." +  p.getName() + ".withdraw", money);

					ChatUtilities.message(p, "setted");
			        Main.plugin.saveDefaultConfig();
			        
			        try {
			           Main.plugin.getLogConfig().save(Main.plugin.getLogConfigFile());
			        } catch (IOException fpe) {
			           //Whatever you want to print/etc for error purposes
			        }
					
					
				Main.economy.depositPlayer(p, money);
				Main.plugin.getIslandConfig().set(mowner, omoney - money);
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l] &fYou had withdraw &6$"+ money + " &ffrom your island bank"));

		        Main.plugin.saveDefaultConfig();
		        
		        try {
		           Main.plugin.getIslandConfig().save(Main.plugin.getIslandConfigFile());
		        } catch (IOException fpe) {
		           //Whatever you want to print/etc for error purposes
		        }
				return true;
				}else{

					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l] &4Your island did not have so much money!"));
					return true;
				}
			}else{
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss ");
				Date date = new Date();
				String dt = dateFormat.format(date);
				

				Main.plugin.getLogConfig().set("Logs." + puuid + "." +  dt + "." +  p.getName() + ".withdraw", money);

		        Main.plugin.saveDefaultConfig();
		        
		        try {
		           Main.plugin.getLogConfig().save(Main.plugin.getLogConfigFile());
		        } catch (IOException fpe) {
		           //Whatever you want to print/etc for error purposes
		        }
				
				
				
				Main.economy.depositPlayer(p, money);
				double omoney = Main.plugin.getIslandConfig().getDouble(puuid);

				if (omoney >= money){
				Main.plugin.getIslandConfig().set(puuid, omoney - money);
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l] &fYou had withdraw &6$"+ money + " &ffrom your island bank"));
		        Main.plugin.saveDefaultConfig();
		        
		        try {
		           Main.plugin.getIslandConfig().save(Main.plugin.getIslandConfigFile());
		        } catch (IOException fpe) {
		           //Whatever you want to print/etc for error purposes
		        }
				return true;
				}else{

					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l] &4Your island did not have so much money!"));
					return true;
				}
			}
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l] &4Please provide numbers!"));
				return true;
			}
			
			}else{

				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l] &fUsage: &a/sbbank withdraw <amount>"));
				return true;
			}
			
			
		}else
			if (args[0].equalsIgnoreCase("deposit")){
				if (args.length == 2){
				
				
				
				String puuid = p.getUniqueId().toString();
				if (isInt(args[1])){
				double money = Double.parseDouble(args[1]);
				
				if (money <= Main.economy.getBalance(p)){
				
				if (Main.plugin.getIslandConfig().get(puuid) == null){
					String mowner =  Main.plugin.getPlayerConfig().getString(puuid);
					double omoney = Main.plugin.getIslandConfig().getDouble(mowner);
					

					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss ");
					Date date = new Date();
					String dt = dateFormat.format(date);
					

					Main.plugin.getLogConfig().set("Logs." + mowner + "." +  dt + "." +  p.getName() + ".deposit", money);

					ChatUtilities.message(p, "setted");
			        Main.plugin.saveDefaultConfig();
			        
			        try {
			           Main.plugin.getLogConfig().save(Main.plugin.getLogConfigFile());
			        } catch (IOException fpe) {
			           //Whatever you want to print/etc for error purposes
			        }
					
					
					
					Main.economy.withdrawPlayer(p, money);
					Main.plugin.getIslandConfig().set(mowner, omoney + money);
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l]&f You had deposit &6$"+ money + " &fto your island bank"));
			        Main.plugin.saveDefaultConfig();
			        
			        try {
			           Main.plugin.getIslandConfig().save(Main.plugin.getIslandConfigFile());
			        } catch (IOException fpe) {
			           //Whatever you want to print/etc for error purposes
			        }
					return true;
				}else{
					

					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss ");
					Date date = new Date();
					String dt = dateFormat.format(date);
					

					Main.plugin.getLogConfig().set("Logs." + puuid + "." +  dt + "." +  p.getName() + ".deposit", money);

			        Main.plugin.saveDefaultConfig();
			        
			        try {
			           Main.plugin.getLogConfig().save(Main.plugin.getLogConfigFile());
			        } catch (IOException fpe) {
			           //Whatever you want to print/etc for error purposes
			        }
					
					
					
					Main.economy.withdrawPlayer(p, money);
					double omoney = Main.plugin.getIslandConfig().getDouble(puuid);
					Main.plugin.getIslandConfig().set(puuid, omoney + money);
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l] &fYou had deposit &6$"+ money + " &fto your island bank"));
			        Main.plugin.saveDefaultConfig();
			        
			        try {
			           Main.plugin.getIslandConfig().save(Main.plugin.getIslandConfigFile());
			        } catch (IOException fpe) {
			           //Whatever you want to print/etc for error purposes
			        }
					return true;
				}
				
				}else{
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l] &4You did not have so much money!"));
					return true;
				}
				
				
				
				}else{
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l] &4Please provide numbers!"));
					return true;
				}
				
				}else{
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l] &fUsage: &a/sbbank deposit <amount>"));
					return true;
					
				}
				
			}else
				if (args[0].equalsIgnoreCase("admin")){
					if(sender.hasPermission("bank.admin")){

					if (args.length == 1){
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8-=[ &a&lx &8]=- &8[ &6<server> &8] -=[ &a&lx &8]=-").replaceAll("<server>", Main.plugin.getConfig().getString("ServerName")));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', " "));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank admin add &f- &7Command List"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank admin take &f- &7Check island balance"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank admin reset &f- &7Withdraw money from island"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', " "));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8-=[ &a&lx &8]=- &8[ &6BANK &8] -=[ &a&lx &8]=-"));
				    return true;
					}else
						if(args[1].equalsIgnoreCase("add")){
							if(args.length == 2){
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[ &6&lBANK &f&l] &fUsage: &a/sbbank admin add <name> <amount>"));

								return true;
							}else{
								if(args.length == 3){
									p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[ &6&lBANK &f&l] &fUsage: &a/sbbank admin add <name> <amount>"));

									return true;
								}else{
									if(args.length == 4){
										Player player = Bukkit.getPlayer(args[2]);
										if (player != null){
										UUID puuid = player.getUniqueId();
										String uuid = puuid.toString();
										if (Main.plugin.getIslandConfig().getString(uuid) == null){
											String ouuid = Main.plugin.getPlayerConfig().getString(uuid);
											
										if (isInt(args[3])){
										double amount = Main.plugin.getIslandConfig().getDouble(ouuid);
										int arg = Integer.parseInt(args[3]);
										Main.plugin.getIslandConfig().set(ouuid, amount + arg);
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[ &6&lBANK &f&l] &fYou had added &6$" + args[3] + " &fto &a" + player.getName()));

								        Main.plugin.saveDefaultConfig();
								        
								        try {
								           Main.plugin.getIslandConfig().save(Main.plugin.getIslandConfigFile());
								        } catch (IOException fpe) {
								           //Whatever you want to print/etc for error purposes
								        }
										return true;
										}else{

											p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l] &4Please provide numbers!"));
											return true;
										}
											
										}else{
											
										if (isInt(args[3])){
										double amount = Main.plugin.getIslandConfig().getDouble(uuid);
										int arg = Integer.parseInt(args[3]);
										Main.plugin.getIslandConfig().set(uuid, amount + arg);
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[ &6&lBANK &f&l] &fYou had added &6$" + args[3] + " &fto &a" + player.getName()));

								        Main.plugin.saveDefaultConfig();
								        
								        try {
								           Main.plugin.getIslandConfig().save(Main.plugin.getIslandConfigFile());
								        } catch (IOException fpe) {
								           //Whatever you want to print/etc for error purposes
								        }
										return true;
										}else{

											p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l] &4Please provide numbers!"));
											return true;
										}
											
										}
									}else{

										p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[ &6&lBANK &f&l] &4Player not online"));
										return true;
									}
									}
								}
							}
						}else
							if(args[1].equalsIgnoreCase("take")){
								if(args.length == 2){
									p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[ &6&lBANK &f&l] &fUsage: &a/sbbank admin take <name> <amount>"));

									return true;
								}else{
									if(args.length == 3){
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[ &6&lBANK &f&l] &fUsage: &a/sbbank admin take <name> <amount>"));

										return true;
									}else{
										if(args.length == 4){
											Player player = Bukkit.getPlayer(args[2]);
											if (player != null){
											UUID puuid = player.getUniqueId();
											String uuid = puuid.toString();
											if (Main.plugin.getIslandConfig().getString(uuid) == null){
												String ouuid = Main.plugin.getPlayerConfig().getString(uuid);
												
											if (isInt(args[3])){
											double amount = Main.plugin.getIslandConfig().getDouble(ouuid);
											int arg = Integer.parseInt(args[3]);
											Main.plugin.getIslandConfig().set(ouuid, amount - arg);
											p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[ &6&lBANK &f&l] &fYou had take &6$" + args[3] + " &fto &a" + player.getName()));

									        Main.plugin.saveDefaultConfig();
									        
									        try {
									           Main.plugin.getIslandConfig().save(Main.plugin.getIslandConfigFile());
									        } catch (IOException fpe) {
									           //Whatever you want to print/etc for error purposes
									        }
											return true;
											}else{

												p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l] &4Please provide numbers!"));
												return true;
											}
												
											}else{
												
											if (isInt(args[3])){
											double amount = Main.plugin.getIslandConfig().getDouble(uuid);
											int arg = Integer.parseInt(args[3]);
											Main.plugin.getIslandConfig().set(uuid, amount - arg);
											p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[ &6&lBANK &f&l] &fYou had take &6$" + args[3] + " &fto &a" + player.getName()));

									        Main.plugin.saveDefaultConfig();
									        
									        try {
									           Main.plugin.getIslandConfig().save(Main.plugin.getIslandConfigFile());
									        } catch (IOException fpe) {
									           //Whatever you want to print/etc for error purposes
									        }
											return true;
											}else{

												p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l] &4Please provide numbers!"));
												return true;
											}
												
											}
										}else{

											p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[ &6&lBANK &f&l] &4Player not online"));
											return true;
										}
										}
									}
								}
								
							}else
								if(args[1].equalsIgnoreCase("reset")){

									if(args.length == 2){
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[ &6&lBANK &f&l] &fUsage: &a/sbbank admin reset <name>"));

										return true;
									}else{
										if(args.length == 3){
											Player player = Bukkit.getPlayer(args[2]);
											if (player != null){
											UUID puuid = player.getUniqueId();
											String uuid = puuid.toString();
											if (Main.plugin.getIslandConfig().getString(uuid) == null){
												String ouuid = Main.plugin.getPlayerConfig().getString(uuid);
												
											double amount = Main.plugin.getIslandConfig().getDouble(ouuid);
											Main.plugin.getIslandConfig().set(ouuid, 0);
											p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[ &6&lBANK &f&l] &fYou had reset &6" + player.getName() + "'s money"));

									        Main.plugin.saveDefaultConfig();
									        
									        try {
									           Main.plugin.getIslandConfig().save(Main.plugin.getIslandConfigFile());
									        } catch (IOException fpe) {
									           //Whatever you want to print/etc for error purposes
									        }
											return true;
											
												
											}else{
												
											double amount = Main.plugin.getIslandConfig().getDouble(uuid);
											Main.plugin.getIslandConfig().set(uuid, 0);
											p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[ &6&lBANK &f&l] &fYou had reset &6" + player.getName() + "'s money"));

									        Main.plugin.saveDefaultConfig();
									        
									        try {
									           Main.plugin.getIslandConfig().save(Main.plugin.getIslandConfigFile());
									        } catch (IOException fpe) {
									           //Whatever you want to print/etc for error purposes
									        }
											return true;
											
												
											}
											}else{
												p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[ &6&lBANK &f&l] &4Player not online"));
												return true;
												
											}

										}
										
									}
									
								}
						
				}else{
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[ &6&lBANK &f&l] &4You do not have permission to do that"));
					return true;
				}
				}else
					{

				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8-=[ &a&lx &8]=- &8[ &6<server> &8] -=[ &a&lx &8]=-").replaceAll("<server>", Main.plugin.getConfig().getString("ServerName")));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', " "));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank help &f- &7Command List"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank balance &f- &7Check island balance"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank withdraw &f- &7Withdraw money from island"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank deposit &f- &7Top up island bank"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', " "));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8-=[ &a&lx &8]=- &8[ &6BANK &8] -=[ &a&lx &8]=-"));
		    return true;
			}
		return false;
	
	}
}
