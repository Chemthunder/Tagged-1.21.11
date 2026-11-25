package org.apostasy.tagged.core.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Chemthunder
 */
public interface Tags {
    Map<String, Tag> TAGS = new HashMap<>();

    Tag OPEN_TO_PVP = create("open_to_pvp", "A");
    Tag NO_PVP = create("no_pvp", "B");
    Tag AFK = create("afk", "C");
    Tag IN_LORE = create("in_lore", "D");
    Tag IDLE = create("idle", "E");

    private static Tag create(String name, String character) {
        Tag tag = new Tag(character);
        TAGS.put(name, tag);
        return tag;
    }
}
