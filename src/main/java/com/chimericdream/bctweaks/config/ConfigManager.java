package com.chimericdream.bctweaks.config;

import com.chimericdream.bctweaks.config.BCTweaksConfig.Defaults;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;

import java.util.Objects;
import java.util.function.Consumer;

public class ConfigManager {
    private static ConfigHolder<BCTweaksConfig> holder;

    public static final Consumer<BCTweaksConfig> DEFAULT = (config) -> {
        config.reset = "Erase to reset";
        config.beaconBaseRange = Defaults.BEACON_BASE_RANGE;
        config.beaconRangePerLevel = Defaults.BEACON_RANGE_PER_LEVEL;
        config.beaconRangePerBlock = Defaults.BEACON_RANGE_PER_BLOCK;
        config.conduitAddVanillaRange = Defaults.CONDUIT_ADD_VANILLA_RANGE;
        config.conduitRangePerBlock = Defaults.CONDUIT_RANGE_PER_BLOCK;
    };

    public static void registerAutoConfig() {
        if (holder != null) {
            throw new IllegalStateException("Configuration already registered");
        }

        holder = AutoConfig.register(BCTweaksConfig.class, JanksonConfigSerializer::new);

        if (holder.getConfig().reset == null || Objects.equals(holder.getConfig().reset, "")) {
            DEFAULT.accept(holder.getConfig());
        }

        holder.save();
    }

    public static BCTweaksConfig getConfig() {
        if (holder == null) {
            return new BCTweaksConfig();
        }

        return holder.getConfig();
    }

    public static void load() {
        if (holder == null) {
            registerAutoConfig();
        }

        holder.load();
    }

    public static void save() {
        if (holder == null) {
            registerAutoConfig();
        }

        holder.save();
    }
}
