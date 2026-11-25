package org.apostasy.tagged.core.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.player.Player;
import org.apostasy.tagged.core.index.TaggedAttachmentTypes;
import org.apostasy.tagged.core.util.Tag;
import org.apostasy.tagged.core.util.Tags;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

/**
 * @author Chemthunder
 */
@SuppressWarnings("unused")
public class TagCommand {
    public static void create(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment) {
        dispatcher.register(literal("status")
                .then(literal("set")
                        .then(argument("tag", StringArgumentType.word())
                                .suggests(TagCommand::buildTagSuggestions)
                                .executes(TagCommand::executeTagApplication)
                        )
                )
                .then(literal("get")
                        .executes(TagCommand::executeTagQuery)
                )
                .then(literal("mute")
                        .executes(TagCommand::executeMute)
                )
        );
    }

    private static int executeTagApplication(CommandContext<CommandSourceStack> context) {
        ServerPlayer serverPlayer = context.getSource().getPlayer();
        String key = StringArgumentType.getString(context, "tag");

        if (serverPlayer != null) {
            Tag postTag = null;

            for (String tagKey : Tags.TAGS.keySet()) {
                if (tagKey.equals(key)) {
                    postTag = Tags.TAGS.get(tagKey);
                }
            }

            if (postTag != null) {
                Tag preTag = serverPlayer.getAttached(TaggedAttachmentTypes.TAG);
                
                if (preTag != null) {
                    if (preTag == Tags.OPEN_TO_PVP && postTag != Tags.OPEN_TO_PVP) {
                        serverPlayer.setAttached(TaggedAttachmentTypes.FIGHT_BUFFER, (2 * 20));
                    }
                }
                
                serverPlayer.setAttached(TaggedAttachmentTypes.TAG, postTag);

                context.getSource().sendSuccess(
                        () -> Component.literal("Set tag to ").append(key),
                        false
                );
            }
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int executeTagQuery(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();

        if (player != null) {
            context.getSource().sendSystemMessage(Component.literal(player.getAttachedOrCreate(TaggedAttachmentTypes.TAG).character()));
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int executeMute(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();

        if (player != null) {
            if (player.hasAttached(TaggedAttachmentTypes.SHOW_MESSAGES)) {
                player.removeAttached(TaggedAttachmentTypes.SHOW_MESSAGES);
                player.displayClientMessage(Component.literal("Unmuted tagged messages"), true);
            } else {
                player.setAttached(TaggedAttachmentTypes.SHOW_MESSAGES, Unit.INSTANCE);
                player.displayClientMessage(Component.literal("Muted tagged messages"), true);
            }
        }
        return Command.SINGLE_SUCCESS;
    }

    private static CompletableFuture<Suggestions> buildTagSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        for (String tagKey : Tags.TAGS.keySet()) {
            builder.suggest(tagKey.toLowerCase());
        }
        return builder.buildFuture();
    }
}
