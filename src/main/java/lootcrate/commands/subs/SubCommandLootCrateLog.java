package lootcrate.commands.subs;

import lootcrate.LootCrate;
import lootcrate.commands.SubCommand;
import lootcrate.enums.Message;
import lootcrate.enums.Permission;
import lootcrate.logging.RewardLogManager;
import lootcrate.logging.RewardRecord;
import lootcrate.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class SubCommandLootCrateLog extends SubCommand {

    private final String[] args;
    private final CommandSender sender;
    private final LootCrate plugin;

    private static final int DEFAULT_RESULT_COUNT = 10;
    private static final int MAX_RESULT_COUNT = 100;

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    .withZone(ZoneId.systemDefault());

    /**
     * Default constructor for any {@link lootcrate.commands.SubCommand}
     *
     * @param plugin an instance of {@link lootcrate.LootCrate}
     * @param sender the {@link org.bukkit.command.CommandSender} which is executing this command
     * @param args   the following arguments in the command string
     */
    public SubCommandLootCrateLog(LootCrate plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args,
                Permission.COMMAND_LOOTCRATE_DISPLAYCHANCES,
                Permission.COMMAND_LOOTCRATE_ADMIN);
        this.plugin = plugin;
        this.sender = sender;
        this.args = args;
    }

    @Override
    public void runSubCommand(boolean playerRequired) {
        if (this.testPlayer(playerRequired))
            return;
        if (!this.testPermissions())
            return;

        // /lootcrate log player {name/uuid} {results count}
        // /lootcrate log reward {reward_id} {result count}
        // /lootcrate log crate {crate_id} {results count}

        if (args.length < 3) {
            // You can replace this with a Message enum if you like
            sender.sendMessage("§cUsage: /lootcrate log <player|reward|crate> <target> [count]");
            return;
        }

        String mode = args[1].toLowerCase(Locale.ROOT);
        int limit = parseCount(args.length >= 4 ? args[3] : null);

        RewardLogManager logManager = plugin.getManager(RewardLogManager.class);
        if (logManager == null) {
            sender.sendMessage("§cLogging is not enabled.");
            return;
        }

        switch (mode) {
            case "player":
                handlePlayerLog(logManager, limit);
                break;
            case "reward":
                handleRewardLog(logManager, limit);
                break;
            case "crate":
                handleCrateLog(logManager, limit);
                break;
            default:
                sender.sendMessage("§cUnknown log mode. Use: player, reward, crate");
                break;
        }
    }

    /* ============================
     *      MODE: PLAYER
     * ============================ */

    private void handlePlayerLog(RewardLogManager logManager, int limit) {
        String target = args[2];

        UUID uuid = parsePlayerUUID(target);
        if (uuid == null) {
            sender.sendMessage("§cCould not resolve player or UUID: §e" + target);
            return;
        }

        sender.sendMessage("§7Fetching log entries for player §e" + target + "§7...");

        // Async IO
        logManager.readForPlayerAsync(uuid).thenAccept(records -> {
            // Sort newest first
            records.sort(Comparator.comparingInt(RewardRecord::getTimestamp).reversed());

            List<RewardRecord> sub = records.subList(0, Math.min(limit, records.size()));

            // Jump back to main thread to send messages
            Bukkit.getScheduler().runTask(plugin, () -> {
                if (sub.isEmpty()) {
                    sender.sendMessage("§7No log entries found for §e" + target + "§7.");
                    return;
                }

                sender.sendMessage("§7Showing §e" + sub.size() + "§7 most recent entries for §e" + target + "§7:");
                for (RewardRecord r : sub) {
                    sender.sendMessage(formatRecordLine(r));
                }
            });
        });
    }

    /* ============================
     *      MODE: REWARD
     * ============================ */

    private void handleRewardLog(RewardLogManager logManager, int limit) {
        Integer rewardIdInt = CommandUtils.tryParse(args[2]);
        if (rewardIdInt == null) {
            sender.sendMessage("§cInvalid reward id: §e" + args[2]);
            return;
        }
        short rewardId = rewardIdInt.shortValue();

        sender.sendMessage("§7Fetching log entries for reward ID §e" + rewardId + "§7...");

        CompletableFuture<List<RewardRecord>> future = logManager.readAllAsync();
        future.thenAccept(all -> {
            List<RewardRecord> filtered = new ArrayList<>();
            for (RewardRecord r : all) {
                if (r.getRewardId() == rewardId) {
                    filtered.add(r);
                }
            }

            filtered.sort(Comparator.comparingInt(RewardRecord::getTimestamp).reversed());
            List<RewardRecord> sub = filtered.subList(0, Math.min(limit, filtered.size()));

            Bukkit.getScheduler().runTask(plugin, () -> {
                if (sub.isEmpty()) {
                    sender.sendMessage("§7No log entries found for reward ID §e" + rewardId + "§7.");
                    return;
                }

                sender.sendMessage("§7Showing §e" + sub.size() + "§7 most recent entries for reward ID §e" + rewardId + "§7:");
                for (RewardRecord r : sub) {
                    sender.sendMessage(formatRecordLine(r));
                }
            });
        });
    }

    /* ============================
     *      MODE: CRATE
     * ============================ */

    private void handleCrateLog(RewardLogManager logManager, int limit) {
        Integer crateIdInt = CommandUtils.tryParse(args[2]);
        if (crateIdInt == null) {
            sender.sendMessage("§cInvalid crate id: §e" + args[2]);
            return;
        }
        short crateId = crateIdInt.shortValue();

        sender.sendMessage("§7Fetching log entries for crate ID §e" + crateId + "§7...");

        CompletableFuture<List<RewardRecord>> future = logManager.readAllAsync();
        future.thenAccept(all -> {
            List<RewardRecord> filtered = new ArrayList<>();
            for (RewardRecord r : all) {
                if (r.getCrateId() == crateId) {
                    filtered.add(r);
                }
            }

            filtered.sort(Comparator.comparingInt(RewardRecord::getTimestamp).reversed());
            List<RewardRecord> sub = filtered.subList(0, Math.min(limit, filtered.size()));

            Bukkit.getScheduler().runTask(plugin, () -> {
                if (sub.isEmpty()) {
                    sender.sendMessage("§7No log entries found for crate ID §e" + crateId + "§7.");
                    return;
                }

                sender.sendMessage("§7Showing §e" + sub.size() + "§7 most recent entries for crate ID §e" + crateId + "§7:");
                for (RewardRecord r : sub) {
                    sender.sendMessage(formatRecordLine(r));
                }
            });
        });
    }

    /* ============================
     *   Helpers
     * ============================ */

    private int parseCount(String raw) {
        if (raw == null) {
            return DEFAULT_RESULT_COUNT;
        }
        Integer parsed = CommandUtils.tryParse(raw);
        if (parsed == null || parsed <= 0) {
            return DEFAULT_RESULT_COUNT;
        }
        return Math.min(parsed, MAX_RESULT_COUNT);
    }

    private UUID parsePlayerUUID(String input) {
        // Try UUID directly
        try {
            return UUID.fromString(input);
        } catch (IllegalArgumentException ignored) {
        }

        // Try online player by name
        if (Bukkit.getPlayerExact(input) != null) {
            return Bukkit.getPlayerExact(input).getUniqueId();
        }

        // Try offline player
        @SuppressWarnings("deprecation")
        OfflinePlayer offline = Bukkit.getOfflinePlayer(input);
        if (offline != null && offline.hasPlayedBefore()) {
            return offline.getUniqueId();
        }

        return null;
    }

    private String formatRecordLine(RewardRecord r) {
        String timeString = DATE_FORMAT.format(Instant.ofEpochSecond(r.getTimestamp()));
        OfflinePlayer off = Bukkit.getOfflinePlayer(r.getPlayerUUID());
        String name = (off != null && off.getName() != null) ? off.getName() : "Unknown";

        return String.format(
                "§8┌──── §7Reward Log Entry §8────\n" +
                        "§8│ §7Time:   §f%s\n" +
                        "§8│ §7Player: §e%s §8(§7%s§8)\n" +
                        "§8│ §7Crate:  §b%s\n" +
                        "§8│ §7Reward: §d%s\n" +
                        "§8│ §7Amount: §a%s\n" +
                        "§8└────────────────────────",
                timeString,
                name,
                r.getPlayerUUID(),
                r.getCrateId(),
                r.getRewardId(),
                r.getRewardAmount()
        );


    }


    @Override
    public List<String> runTabComplete() {
        List<String> list = new LinkedList<>();

        if (!sender.hasPermission(Permission.COMMAND_LOOTCRATE_DISPLAYCHANCES.getKey())
                && !sender.hasPermission(Permission.COMMAND_LOOTCRATE_ADMIN.getKey()))
            return list;

        // /lootcrate log <player|reward|crate> ...
        if (args.length == 2) {
            list.add("player");
            list.add("reward");
            list.add("crate");
            return list;
        }

        // /lootcrate log player <name|uuid> [count]
        if (args.length == 3 && args[1].equalsIgnoreCase("player")) {
            // Suggest online players
            Bukkit.getOnlinePlayers().forEach(p -> list.add(p.getName()));
            return list;
        }

        // /lootcrate log reward <reward_id> [count]
        if (args.length == 3 && args[1].equalsIgnoreCase("reward")) {
            list.add("[reward_id]");
            return list;
        }

        // /lootcrate log crate <crate_id> [count]
        if (args.length == 3 && args[1].equalsIgnoreCase("crate")) {
            list.add("[crate_id]");
            // If you want, you can also add crate IDs from cacheManager here
            // cacheManager.getCrates().forEach(crate -> list.add(String.valueOf(crate.getId())));
            return list;
        }

        // [count] tab-complete
        if (args.length == 4) {
            list.add("10");
            list.add("25");
            list.add("50");
            return list;
        }

        return list;
    }
}
