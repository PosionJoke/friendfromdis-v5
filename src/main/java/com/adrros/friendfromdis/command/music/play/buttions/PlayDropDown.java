//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.adrros.friendfromdis.command.music.play.buttions;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.LayoutComponent;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

public class PlayDropDown {
    public PlayDropDown() {
    }

    public void createAndRunDropDown(TextChannel channel, List<String> titles) {
        List<String> titlesFixed = removeDuplicates(titles);
        
        StringSelectMenu.Builder builder = StringSelectMenu.create("menu:id");
        titlesFixed.forEach((title) -> builder.addOption(title, title));
        StringSelectMenu menu = builder.build();
        channel.sendMessageComponents(ActionRow.of(new ItemComponent[]{menu}), new LayoutComponent[0]).queue();
    }
    
    public static List<String> removeDuplicates(List<String> titles) {
        Set<String> uniqueTitlesSet = new LinkedHashSet<>(titles);
        return new ArrayList<>(uniqueTitlesSet);
    }
}
