package org.apostasy.tagged.core.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.apostasy.tagged.core.index.TaggedAttachmentTypes;

/**
 * @author Chemthunder
 */
public abstract class TagUtil {
    public static void tickPlayer(Player player) {
        int held = player.getAttachedOrCreate(TaggedAttachmentTypes.FIGHT_BUFFER);

        if (held > 0) {
            player.setAttached(TaggedAttachmentTypes.FIGHT_BUFFER, held - 1);
        }
    }

    public static void shame(Level level) {

    }
}
