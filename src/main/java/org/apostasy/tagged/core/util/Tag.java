package org.apostasy.tagged.core.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * @author Chemthunder
 */
public record Tag(String character) {
    public static final Codec<Tag> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            Codec.STRING
                    .fieldOf("character")
                    .forGetter(Tag::character)
    ).apply(codec, Tag::new));

    public static final StreamCodec<ByteBuf, Tag> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);
}
