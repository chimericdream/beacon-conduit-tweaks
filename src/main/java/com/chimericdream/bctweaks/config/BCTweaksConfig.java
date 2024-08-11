package com.chimericdream.bctweaks.config;

import com.chimericdream.bctweaks.BCTweaksMod;
import com.chimericdream.bctweaks.ModInfo;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.HashMap;
import java.util.Map;

@Config(name = ModInfo.MOD_ID)
public class BCTweaksConfig implements ConfigData {
    public String reset = "";

    public double beaconBaseRange = Defaults.BEACON_BASE_RANGE;
    public double beaconRangePerLevel = Defaults.BEACON_RANGE_PER_LEVEL;
    public String beaconRangePerBlock = Defaults.BEACON_RANGE_PER_BLOCK;
    public boolean conduitAddVanillaRange = Defaults.CONDUIT_ADD_VANILLA_RANGE;
    public String conduitRangePerBlock = Defaults.CONDUIT_RANGE_PER_BLOCK;

    @ConfigEntry.Gui.Excluded
    public transient Map<String, Double> beaconBlockModifiers = Defaults.BEACON_BLOCK_MODIFIERS;

    @ConfigEntry.Gui.Excluded
    public transient Map<String, Double> conduitBlockModifiers = Defaults.CONDUIT_BLOCK_MODIFIERS;

    public void validatePostLoad() {
        if (this.beaconBaseRange < 0) {
            BCTweaksMod.LOGGER.info("[config] Invalid value found for 'beaconBaseRange'! Resetting to default.");
            this.beaconBaseRange = Defaults.BEACON_BASE_RANGE;
        }

        if (this.beaconRangePerLevel < 0) {
            this.beaconRangePerLevel = Defaults.BEACON_RANGE_PER_LEVEL;
            BCTweaksMod.LOGGER.info("[config] Invalid value found for 'beaconRangePerLevel'! Resetting to default.");
        }

        try {
            this.beaconBlockModifiers = parseBlockRange(this.beaconRangePerBlock);
        } catch (Exception e) {
            BCTweaksMod.LOGGER.info("[config] Invalid value found for 'beaconRangePerBlock'! Resetting to default.");
            this.beaconRangePerBlock = Defaults.BEACON_RANGE_PER_BLOCK;
            this.beaconBlockModifiers = Defaults.BEACON_BLOCK_MODIFIERS;
        }

        try {
            this.conduitBlockModifiers = parseBlockRange(this.conduitRangePerBlock);
        } catch (Exception e) {
            BCTweaksMod.LOGGER.info("[config] Invalid value found for 'conduitRangePerBlock'! Resetting to default.");
            this.conduitRangePerBlock = Defaults.CONDUIT_RANGE_PER_BLOCK;
            this.conduitBlockModifiers = Defaults.CONDUIT_BLOCK_MODIFIERS;
        }
    }

    private Map<String, Double> parseBlockRange(String blockRange) throws Exception {
        if (blockRange == null || blockRange.isEmpty()) {
            return new HashMap<>();
        }

        Map<String, Double> blockModifiers = new HashMap<>();

        String[] blockRanges = blockRange.split(";");
        for (String block : blockRanges) {
            String[] blockData = block.split(",");
            if (blockData.length != 2) {
                throw new Exception("Invalid block range format");
            }

            try {
                Double.parseDouble(blockData[1]);
            }
            catch (NumberFormatException e) {
                throw new Exception("Invalid block range format");
            }

            blockModifiers.put(blockData[0], Double.parseDouble(blockData[1]));
        }

        return blockModifiers;
    }

    public static class Defaults {
        public static double BEACON_BASE_RANGE = 10;
        public static double BEACON_RANGE_PER_LEVEL = 10;
        public static String BEACON_RANGE_PER_BLOCK = "minecraft:iron_block,0.0;minecraft:gold_block,0.0;minecraft:diamond_block,0.5;minecraft:emerald_block,0.5;minecraft:netherite_block,2.0";
        public static boolean CONDUIT_ADD_VANILLA_RANGE = true;
        public static String CONDUIT_RANGE_PER_BLOCK = "";

        public static Map<String, Double> BEACON_BLOCK_MODIFIERS = Map.of(
            "minecraft:iron_block", 0.0,
            "minecraft:gold_block",0.0,
            "minecraft:diamond_block",0.5,
            "minecraft:emerald_block",0.5,
            "minecraft:netherite_block",2.0
        );
        public static Map<String, Double> CONDUIT_BLOCK_MODIFIERS = Map.of();
    }
}
