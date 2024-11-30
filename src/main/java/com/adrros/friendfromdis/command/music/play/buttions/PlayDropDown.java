//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.adrros.friendfromdis.command.music.play.buttions;

import java.util.List;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.LayoutComponent;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

public class PlayDropDown {
    public PlayDropDown() {
    }

    public void createDropDown(TextChannel channel, List<String> titles) {
        StringSelectMenu.Builder builder = StringSelectMenu.create("menu:id");
        titles.forEach((title) -> builder.addOption(title, title));
        StringSelectMenu menu = builder.build();
        channel.sendMessageComponents(ActionRow.of(new ItemComponent[]{menu}), new LayoutComponent[0]).queue();
    }
}
