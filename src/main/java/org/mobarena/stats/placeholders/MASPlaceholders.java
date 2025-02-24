package org.mobarena.stats.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.OfflinePlayer;
import org.mobarena.stats.MobArenaStats;
import org.mobarena.stats.MobArenaStatsPlugin;
import org.mobarena.stats.store.ArenaStats;
import org.mobarena.stats.store.GlobalStats;
import org.mobarena.stats.store.PlayerStats;
import org.mobarena.stats.store.StatsStore;

public class MASPlaceholders extends PlaceholderExpansion {

    MobArenaStats plugin = MobArenaStatsPlugin.getInstance();
    StatsStore store = plugin.getStatsStore();
    GlobalStats globalStats = store.getGlobalStats();



    @Override
    public String getAuthor() {
        return "Maroon28, Garbagemule";
    }

    @Override
    public String getIdentifier() {
        return "mastats";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {

        final String[] args = identifier.split("\\_");
        final String arenaName = args[0];

        ArenaStats arenaStats = store.getArenaStats(arenaName);
        PlayerStats playerStats = store.getPlayerStats(player.getName());

        long totalArenaMilliseconds = arenaStats.totalSeconds * 1000;
        long highestArenaMilliseconds = arenaStats.totalSeconds * 1000;

        switch (identifier) {
            case "global_sessions":
                return Integer.toString(globalStats.totalSessions);
            case "global_seconds":
                return Long.toString(globalStats.totalSeconds);
            case "global_seconds-formatted":
                long globalDurationFormatted = globalStats.totalSeconds * 1000;
                return DurationFormatUtils.formatDuration(globalDurationFormatted, "HH:mm:ss", true);
            case "global_kills":
                return Long.toString(globalStats.totalKills);
            case "global_waves":
                return Long.toString(globalStats.totalWaves);

            case "player_total-sessions":
                return Integer.toString(playerStats.totalSessions);
            case "player_total-kills":
                return Long.toString(playerStats.totalKills);
            case "player_total-seconds":
                return Long.toString(playerStats.totalSeconds);
            case "player_total-seconds-formatted":
                long totalPlayerMilliseconds = playerStats.totalSeconds * 1000;
                return DurationFormatUtils.formatDuration(totalPlayerMilliseconds, "HH:mm:ss", true);
            case "player_total-waves":
                return Long.toString(playerStats.totalWaves);
        }
        // arenaName_stat
        if (args.length == 2) {
            final String param = args[1];
            switch (param) {
                case "highest-wave":
                    return Integer.toString(arenaStats.highestWave);
                case "highest-kills":
                    return Integer.toString(arenaStats.highestKills);
                case "highest-seconds":
                    return Integer.toString(arenaStats.highestSeconds);
                case "highest-seconds-formatted":
                    return DurationFormatUtils.formatDuration(highestArenaMilliseconds, "HH:mm:ss", true);
                case "total-kills":
                    return Long.toString(arenaStats.totalKills);
                case "total-waves":
                    return Long.toString(arenaStats.totalWaves);
                case "total-sessions":
                    return Integer.toString(arenaStats.totalSessions);
                case "total-seconds":
                    return Long.toString(arenaStats.totalSeconds);
                case "total-seconds-formatted":
                    return DurationFormatUtils.formatDuration(totalArenaMilliseconds, "HH:mm:ss", true);
            }
        }
        return null; // Not recognized by the Expansion
}
}