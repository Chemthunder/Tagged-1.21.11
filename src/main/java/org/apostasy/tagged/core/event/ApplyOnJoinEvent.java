package org.apostasy.tagged.core.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.level.ServerPlayer;
import org.apostasy.tagged.core.index.TaggedAttachmentTypes;
import org.apostasy.tagged.core.util.Tags;

/**
 * @author Chemthunder
 */
public class ApplyOnJoinEvent implements ServerPlayerEvents.Join {
    public void onJoin(ServerPlayer player) {
        player.setAttached(TaggedAttachmentTypes.TAG, Tags.IDLE);
    }
}
