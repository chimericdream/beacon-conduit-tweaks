package com.chimericdream.bctweaks;

import com.chimericdream.bctweaks.config.ConfigManager;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BCTweaksMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(ModInfo.MOD_ID);

    static {
        ConfigManager.registerAutoConfig();
    }

    @Override
    public void onInitialize() {
    }
}
