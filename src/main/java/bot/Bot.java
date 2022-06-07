package bot;

import model.Graph;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import spring.SpringClient;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.util.List;

public class Bot extends ListenerAdapter {
    private List<Graph> graphs = null;
    private final ImageDrawer drawer = new ImageDrawer();
    private final SpringClient client = new SpringClient();

    public static void main(String[] args) {
        try {
            final JDA jda = JDABuilder
                .createDefault("OTgzNDE1NjQyMDcxOTA0MzQ3.GeeKUi.4ac4awF3I-w8Um6oyZAQ7Asr1Z76Uu2xLuPEIg")
                .addEventListeners(new Bot())
                .build();
            jda.awaitReady();
        }
        catch (final LoginException exc) {
            System.err.println("cannot login to discord");
        }
        catch (final InterruptedException exc) {
            System.err.println("thread error");
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.TEXT)) {
            final String request = event.getMessage().getContentRaw();
            final MessageChannel channel = event.getChannel();
            if (request.startsWith("$ ")) {
                final String content = request.substring("$ ".length());
                if (content.startsWith("select graph ")) {
                    graphs = client.getGraphs();
                    if (graphs.isEmpty())
                        channel
                            .sendMessage("no graphs to choose from :pleading_face:")
                            .queue();
                    else {
                        final int id = Integer.parseInt(content.substring("select graph ".length()));
                        drawer.save(graphs.get(id));
                        channel
                            .sendMessage("graph `" + id + "` from `" + graphs.size() + "`")
                            .addFile(new File("image.png"))
                            .queue(response -> {
                                response.addReaction("⬅️").queue();
                                response.addReaction("\uD83C\uDD97").queue();
                                response.addReaction("➡️").queue();
                            });
                    }
                    return;
                }
                channel.sendMessage("unknown command :face_in_clouds:").queue();
            }
        }
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        System.out.println(event);
    }
}
