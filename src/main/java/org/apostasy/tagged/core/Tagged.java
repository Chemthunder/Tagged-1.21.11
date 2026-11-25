package org.apostasy.tagged.core;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.resources.Identifier;
import org.apostasy.tagged.core.command.TagCommand;
import org.apostasy.tagged.core.event.ApplyOnJoinEvent;
import org.apostasy.tagged.core.event.ClearOnLeaveEvent;
import org.apostasy.tagged.core.index.TaggedAttachmentTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Chemthunder
 */
public class Tagged implements ModInitializer {
	public static final String MOD_ID = "tagged";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Identifier TAGS_FONT = id("tags");

	public void onInitialize() {
		TaggedAttachmentTypes.init();

		CommandRegistrationCallback.EVENT.register(TagCommand::create);

		ServerPlayerEvents.JOIN.register(new ApplyOnJoinEvent());
		ServerPlayerEvents.LEAVE.register(new ClearOnLeaveEvent());

		LOGGER.info("im straight up tagging it, and by it, i mean, by apostasy");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
