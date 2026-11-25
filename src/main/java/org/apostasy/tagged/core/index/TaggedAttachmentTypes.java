package org.apostasy.tagged.core.index;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.Unit;
import org.apostasy.tagged.core.Tagged;
import org.apostasy.tagged.core.util.Tag;
import org.apostasy.tagged.core.util.Tags;

import java.util.function.Consumer;

/**
 * @author Chemthunder
 */
public interface TaggedAttachmentTypes {
    AttachmentType<Tag> TAG = register("tag",
            builder -> builder
                    .persistent(Tag.CODEC)
                    .syncWith(Tag.STREAM_CODEC, AttachmentSyncPredicate.all())
                    .initializer(() -> Tags.IDLE)
                    .copyOnDeath()
    );

    AttachmentType<Integer> FIGHT_BUFFER = register("fight_buffer",
            builder -> builder
                    .persistent(Codec.INT)
                    .syncWith(ByteBufCodecs.INT, AttachmentSyncPredicate.all())
                    .initializer(() -> 0)
    );

    AttachmentType<Unit> SHOW_MESSAGES = register("show_messages",
            builder -> builder
                    .persistent(Unit.CODEC)
                    .syncWith(Unit.STREAM_CODEC, AttachmentSyncPredicate.all())
    );

    static void init() {}

    private static <V> AttachmentType<V> register(String name, Consumer<AttachmentRegistry.Builder<V>> consumer) {
        return AttachmentRegistry.create(Tagged.id(name), consumer);
    }
}
