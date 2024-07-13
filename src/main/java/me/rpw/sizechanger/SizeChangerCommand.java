package me.rpw.sizechanger;

import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SizeChangerCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }
        Player player = (Player) sender;
        if (args.length > 0) {
            switch (args[0]) {
                case "add":
                    if (args.length == 2) {
                        add(sender, player, Double.parseDouble(args[1]));
                    } else {
                        add(sender, player, SizeChangerConfig.getInstance().getAddRemoveValue());
                    }
                    break;
                case "remove":
                    if (args.length == 2) {
                        remove(sender, player, Double.parseDouble(args[1]));
                    } else {
                        remove(sender, player, SizeChangerConfig.getInstance().getAddRemoveValue());
                    }
                    break;
                case "get":
                    sender.sendMessage("you are now " + get(player) + " Tall");
                    break;
                case "reset":
                    reset(sender, player);
                    break;
            }
            return true;
        }
        return false;
    }

    private static double get(LivingEntity entity) {
        return entity.getAttribute(Attribute.GENERIC_SCALE).getBaseValue();
    }

    private static void add(@NotNull CommandSender sender, LivingEntity entity, double amount) {
        if (entity.getAttribute(Attribute.GENERIC_SCALE).getBaseValue() >= SizeChangerConfig.getInstance().getMaxSize()) {
            sender.sendMessage("You are already " + SizeChangerConfig.getInstance().getMaxSize() + " Tall");
        } else {
            if (amount <= SizeChangerConfig.getInstance().getMaxSize() && (get(entity) + amount) <= SizeChangerConfig.getInstance().getMaxSize()) {
                entity.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(get(entity) + amount);
            } else {
                sender.sendMessage("Your given amount ist to big");
            }
        }
    }

    private static void remove(@NotNull CommandSender sender, LivingEntity entity, double amount) {
        if (entity.getAttribute(Attribute.GENERIC_SCALE).getBaseValue() <= SizeChangerConfig.getInstance().getMinSize()) {
            sender.sendMessage("You are already " + SizeChangerConfig.getInstance().getMinSize() + " Tall");
        } else {
            if (amount >= SizeChangerConfig.getInstance().getMinSize() && (get(entity) - amount) >= SizeChangerConfig.getInstance().getMinSize()) {
                entity.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(get(entity) - amount);
            } else {
                sender.sendMessage("Your given amount ist to small");
            }
        }
    }

    private static void reset(@NotNull CommandSender sender, LivingEntity entity) {
        double default_scale = entity.getAttribute(Attribute.GENERIC_SCALE).getDefaultValue();
        double current_scale = get(entity);
        if (current_scale != default_scale) {
            entity.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(default_scale);
        } else {
            sender.sendMessage("You are already 1.0 Tall");
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        //System.out.println("Args: " + args.length);
        if (args.length == 1) {
            return Arrays.asList("get", "add", "remove", "reset");
        }
        return new ArrayList<>();
    }
}
