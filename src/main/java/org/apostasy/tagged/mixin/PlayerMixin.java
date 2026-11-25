package org.apostasy.tagged.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.world.entity.player.Player;
import org.apostasy.tagged.core.Tagged;
import org.apostasy.tagged.core.index.TaggedAttachmentTypes;
import org.apostasy.tagged.core.util.Tag;
import org.apostasy.tagged.core.util.TagUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Chemthunder
 */
@Mixin(value = Player.class, priority = 2500)
public abstract class PlayerMixin {
    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void tagged$additionalTick(CallbackInfo ci) {
        TagUtil.tickPlayer((Player) (Object) this);
    }

    @WrapMethod(method = "getDisplayName")
    private Component tagged$apply(Operation<Component> original) {
        Player player = (Player) (Object) this;

        if (player.hasAttached(TaggedAttachmentTypes.TAG)) {
            Tag tag = player.getAttached(TaggedAttachmentTypes.TAG);

            if (tag != null) {
                return Component.empty()
                        .append(Component.literal(tag.character() + " ").withStyle(style -> style.withFont(new FontDescription.Resource(Tagged.TAGS_FONT))))
                        .append(original.call().copy());
            }
        }

        return original.call();
    }
}

