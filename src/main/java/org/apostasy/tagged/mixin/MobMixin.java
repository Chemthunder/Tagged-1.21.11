package org.apostasy.tagged.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import org.apostasy.tagged.core.index.TaggedAttachmentTypes;
import org.apostasy.tagged.core.util.Tags;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author Chemthunder
 */
@Mixin(value = Mob.class)
public abstract class MobMixin {
    @WrapMethod(method = "interact")
    private InteractionResult tagged$fuckYouTickingClock(Player player, InteractionHand interactionHand, Operation<InteractionResult> original) {
        if (player.getAttachedOrCreate(TaggedAttachmentTypes.TAG) == Tags.AFK) {
            return InteractionResult.PASS;
        }
        return original.call(player, interactionHand);
    }
}
