package org.apostasy.tagged.core.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.level.ServerPlayer;
import org.apostasy.tagged.core.index.TaggedAttachmentTypes;

/**
 * @author Chemthunder
 */
public class ClearOnLeaveEvent implements ServerPlayerEvents.Leave {
    public void onLeave(ServerPlayer player) {
        player.removeAttached(TaggedAttachmentTypes.TAG);
    }
}
