package com.github.theredbrain.subblocks;

import com.github.theredbrain.subblocks.registry.BlockRegistry;
import com.github.theredbrain.subblocks.registry.ItemGroupRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubBlocks implements ModInitializer {
	public static final String MOD_ID = "subblocks";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Sub blocks!");

		// Registry
		BlockRegistry.init();
		ItemGroupRegistry.init();
	}

	public static Identifier identifier(String path) {
		return new Identifier(MOD_ID, path);
	}

	public static void info(String message) {
		LOGGER.info("[" + MOD_ID + "] [info]: " + message);
	}

}
