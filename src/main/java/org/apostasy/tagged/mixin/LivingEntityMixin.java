package org.apostasy.tagged.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.apostasy.tagged.core.index.TaggedAttachmentTypes;
import org.apostasy.tagged.core.util.Tag;
import org.apostasy.tagged.core.util.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

/**
 * @author Chemthunder
 */
@Mixin(value = LivingEntity.class)
public abstract class LivingEntityMixin {

    @WrapMethod(method = "createWitherRose")
    private void tagged$shamethemasses(LivingEntity adversary, Operation<Void> original) {
        LivingEntity victim = (LivingEntity) (Object) this;
        if (adversary instanceof Player player) {
            Tag myTag = victim.getAttached(TaggedAttachmentTypes.TAG);
            Tag theirTag = player.getAttached(TaggedAttachmentTypes.TAG);

            if (myTag != null && theirTag != null) {
                if (myTag == Tags.AFK) {
                    broadcast(victim.level(), Component.empty()
                            .append(player.getDisplayName())
                            .append(" ")
                            .append("killed an AFK person! SHAME THEM!!")
                    );
                }

                if (myTag == Tags.IN_LORE && theirTag != Tags.IN_LORE) {
                    broadcast(victim.level(), Component.empty()
                            .append(player.getDisplayName())
                            .append(" ")
                            .append("killed a larper! SHAME!! FAIL RP!!!")
                    );
                }

                if (myTag == Tags.NO_PVP) {
                    broadcast(victim.level(), Component.empty()
                            .append(player.getDisplayName())
                            .append(" ")
                            .append("killed a no-pvper! SHAME THEM!!!")
                    );
                }
            }
        }
    }

    @WrapMethod(method = "handleRelativeFrictionAndCalculateMovement")
    private Vec3 tagged$noVelocity(Vec3 vec3, float f, Operation<Vec3> original) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self.getAttachedOrCreate(TaggedAttachmentTypes.TAG) == Tags.AFK) {
            return Vec3.ZERO;
        }
        return original.call(vec3, f);
    }

    @WrapMethod(method = "getFluidFallingAdjustedMovement")
    private Vec3 tagged$noVelocityInWater(double d, boolean bl, Vec3 vec3, Operation<Vec3> original) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self.getAttachedOrCreate(TaggedAttachmentTypes.TAG) == Tags.AFK) {
            return Vec3.ZERO;
        }
        return original.call(d, bl, vec3);
    }

    @Unique
    private static void broadcast(Level level, Component broadcasted) {
        if (level.getServer() != null) {
            for (ServerPlayer serverPlayer : level.getServer().getPlayerList().getPlayers()) {
                if (!serverPlayer.hasAttached(TaggedAttachmentTypes.SHOW_MESSAGES)) {
                    serverPlayer.sendSystemMessage(broadcasted);
                }
            }
        }
    }
}

// ~~no pvp can attack people~~
// ~~lore should attack lore people~~