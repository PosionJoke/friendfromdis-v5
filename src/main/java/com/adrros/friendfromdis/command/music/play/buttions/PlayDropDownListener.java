//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.adrros.friendfromdis.command.music.play.buttions;

import java.util.List;
import java.util.function.Consumer;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PlayDropDownListener extends ListenerAdapter {
    public PlayDropDownListener() {
    }

    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        if (event.getComponentId().equals("menu:id")) {
            List<String> selected = event.getValues();
            event.reply("Your selection was processed").queue();
            String id = event.getChannel().getId();
            Consumer<String> playSongParamsConsumer = SongsToPlayStore.get(id);
            String s = (String)event.getInteraction().getValues().get(0);
            playSongParamsConsumer.accept(s);
        }

    }
}
