package main.java.me.cain.cconomy.commands;

import main.java.me.cain.cconomy.cConomy;
import main.java.me.cain.cconomy.utils.ChatUtility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor
{
	public boolean onCommand(CommandSender s, Command c, String l, String [] args)
	{
		if(c.getName().equalsIgnoreCase("money")) {
			if(args.length < 1) {
				if(s instanceof Player) {
					int amount = cConomy.plugin.getConfig().getInt("players." + s.getName() + ".balance");
					ChatUtility.Message((Player) s, "Your current balance: " + ChatColor.WHITE + amount);
				} else {
					ChatUtility.Console("You must be in-game to use cConomy commands.");
				}
			}
		}
		if(c.getName().equalsIgnoreCase("pay")) {
			if(s instanceof Player) {
				if(args.length < 2) {
						ChatUtility.Denied((Player) s, "/pay [player] [amount]");
					} else {
						Player target = Bukkit.getServer().getPlayer(args[0]);
						if(target != null) {
						int balance = cConomy.plugin.getConfig().getInt("players." + s.getName() + ".balance");
						int player = cConomy.plugin.getConfig().getInt("players." + target.getName() + ".balance");
						int input = Integer.parseInt(args[1]);
						if(input <= balance) {
							String sPlayer = "players." + target.getName() + ".balance";
							String dPlayer = "players." + s.getName() + ".balance";
							int final1 = player + input;
							int final2 = balance - input;
							ChatUtility.Console("To Player: " + sPlayer);
							ChatUtility.Console("From Player: " + dPlayer);
							// Target Player
							cConomy.plugin.getConfig().set(sPlayer, final1);
							ChatUtility.Message(target, "You have been given " + input + " by " + s.getName());
							ChatUtility.Message(target, "New Balance: " + final1);
							// From Player
							cConomy.plugin.getConfig().set(dPlayer, final2);
							ChatUtility.Message((Player) s, "You have given " + target.getName() + " " + input);
							ChatUtility.Message((Player) s, "New Balance: " + final2);
							// Save.
							cConomy.plugin.saveConfig();
						} else {
							ChatUtility.Denied((Player) s, "You do not have enough funds!");
						}
					} else {
						ChatUtility.Denied((Player) s, "This player is not online.");
					}
				}
			} else {
				ChatUtility.Console("You can only use these commands in-game!");
			}
		}
	
		
		if(c.getName().equalsIgnoreCase("startermoney")) {
			if(args.length < 1) {
				if(s instanceof Player) {
					ChatUtility.Message((Player) s, "/startermoney [amount]");
				} else {
					ChatUtility.Console("/startermoney [amount]");
				}
			} else {
				if(s.isOp()) {
					cConomy.plugin.getConfig().set("settings.starteramount", Integer.parseInt(args[0]));
				}
				if(s instanceof Player) {
					ChatUtility.Message((Player) s, "New Starter Money Set! ( " + args[0] + " )");
				} else {
					ChatUtility.Console("New Starter Money Set! ( " + args[0] + " )");
				}
				cConomy.plugin.saveConfig();
			}
		}
		return false;
	}

}
