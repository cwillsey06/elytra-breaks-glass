package org.cw.ebg;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElytraBreaksGlass implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("elytra-breaks-glass");

	@Override
	public void onInitialize() {
		LOGGER.info("ElytraBreaksGlass Initialized :)");
	}
}
