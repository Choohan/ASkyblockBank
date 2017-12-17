package me.choohan.askyblock.bank;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmd implements CommandExecutor {
	
	// Define main and plugin variable
	main plugin;

	public Cmd(main instance) {
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
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8-=[ &a&lx &8]=- &8[ &6<server> &8] -=[ &a&lx &8]=-").replaceAll("<server>", main.plugin.getConfig().getString("ServerName")));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', " "));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank help &f- &7Command List"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank balance &f- &7Check island balance"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank withdraw &f- &7Withdraw money from island"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank deposit &f- &7Top up island bank"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank addmember &f- &7Add member to access bank"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank remmember &f- &7Remove member from access bank"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', " "));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8-=[ &a&lx &8]=- &8[ &6BANK &8] -=[ &a&lx &8]=-"));
	    return true;
		}

		if (args[0].equalsIgnoreCase("balance")) {
			
			double amount = Method.getBalance(p);
			ChatUtilities.message(p, ChatColor.translateAlternateColorCodes('&', "&fBalance: &6$" + amount));
			return true;
		}else
			if(args[0].equalsIgnoreCase("addmember")) {
				if (args.length == 2){
					if (Bukkit.getPlayer(args[1]) != null){

						Player player = Bukkit.getPlayer(args[1]);
						Method.AddMember(p, player);
						return true;
					}else{
						ChatUtilities.message(p, ChatColor.translateAlternateColorCodes('&', "Target player not online"));
					}
				}else{

					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l] &fUsage: &a/sbbank addmember <player>"));
					return true;
				}
				
			}else
				if(args[0].equalsIgnoreCase("remmember")) {
					if (args.length == 2){

						if (Bukkit.getPlayer(args[1]) != null){
						Player player = Bukkit.getPlayer(args[1]);
						Method.RemMember(p, player);
						return true;
						}else{
							ChatUtilities.message(p, ChatColor.translateAlternateColorCodes('&', "Target player not online"));
						}
					}else{

						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l] &fUsage: &a/sbbank remmember <player>"));
						return true;
					}
					
				}else
			if (args[0].equalsIgnoreCase("withdraw")){
				
				if (args.length == 2){
				if (isInt(args[1])){
					double money = Double.parseDouble(args[1]);
					Method.playerWithdrawtMoneyFromBank(p, money);
					return true;
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
					if (isInt(args[1])){
						double money = Double.parseDouble(args[1]);
						Method.playerDepositMoneyToBank(p, money);
						return true;
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
						if(sender.hasPermission("asbbank.admin")){

						if (args.length == 1){
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8-=[ &a&lx &8]=- &8[ &6<server> &8] -=[ &a&lx &8]=-").replaceAll("<server>", main.plugin.getConfig().getString("ServerName")));
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

											if (Bukkit.getOfflinePlayer(args[2]).getPlayer() != null){
											Player player = Bukkit.getOfflinePlayer(args[2]).getPlayer();

											if (isInt(args[3])){
												double money = Double.parseDouble(args[3]);
												Method.adminAddMoneyToBank(p, player, money);
												return true;
											}else{
												p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l] &4Please provide numbers!"));
												return true;
											}
										}else{

											p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l] &4Unkown player!"));
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
												if (Bukkit.getOfflinePlayer(args[2]).getPlayer() != null){
												Player player = Bukkit.getOfflinePlayer(args[2]).getPlayer();

												if (isInt(args[3])){
													double money = Double.parseDouble(args[3]);
													Method.adminTakeMoneyFromoBank(p, player, money);
													return true;
												}else{
													p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l] &4Please provide numbers!"));
													return true;
												}
											}else{

												p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l] &4Unkown player!"));
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

												if (Bukkit.getOfflinePlayer(args[2]).getPlayer() != null){
												Player player = Bukkit.getOfflinePlayer(args[2]).getPlayer();
												Method.adminResetPlayerBank(p, player);
												return true;
												}else{
													p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&6&l BANK &f&l] &4Unkown player!"));
													return true;
												}
											}else{
												p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[ &6&lBANK &f&l] &fUsage: &a/sbbank admin reset <name>"));
												return true;
											}
										}
									}else{

										p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8-=[ &a&lx &8]=- &8[ &6<server> &8] -=[ &a&lx &8]=-").replaceAll("<server>", main.plugin.getConfig().getString("ServerName")));
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', " "));
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank admin add &f- &7Command List"));
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank admin take &f- &7Check island balance"));
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank admin reset &f- &7Withdraw money from island"));
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', " "));
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8-=[ &a&lx &8]=- &8[ &6BANK &8] -=[ &a&lx &8]=-"));
								    return true;
									}
									
								
						}else{
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[ &6&lBANK &f&l] &4You do not have permission to do that"));
							return true;
						}
					}else{

						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8-=[ &a&lx &8]=- &8[ &6<server> &8] -=[ &a&lx &8]=-").replaceAll("<server>", main.plugin.getConfig().getString("ServerName")));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', " "));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank help &f- &7Command List"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank balance &f- &7Check island balance"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank withdraw &f- &7Withdraw money from island"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank deposit &f- &7Top up island bank"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank addmember &f- &7Add member to access bank"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/sbbank remmember &f- &7Remove member from access bank"));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', " "));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8-=[ &a&lx &8]=- &8[ &6BANK &8] -=[ &a&lx &8]=-"));
						return true;
					}
						
		return false;
		
	}
}
