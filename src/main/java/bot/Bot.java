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
import java.util.Objects;

public class Bot extends ListenerAdapter {
    private int currentGraphId = -1;
    private int selectedGraphId = -1;
    private List<Graph> graphs = null;
    private String selectGraphMessageId = "";

    private final ImageDrawer drawer = new ImageDrawer();
    private final SpringClient client = new SpringClient();

    public static void main(String[] args) {
        try {
            final JDA jda = JDABuilder
                .createDefault("OTgzNDE1NjQyMDcxOTA0MzQ3.GB8lBl.-qXIu1VE918Pq5heEdFpsuVEGl4c2FUIyQzWBY")
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
                if (content.equals("select graph")) {
                    graphs = client.getGraphs();
                    if (graphs.isEmpty())
                        channel.sendMessage("no graphs to choose from :pleading_face:").queue();
                    else {
                        currentGraphId = 1;
                        sendCurrentGraph(channel);
                    }
                    return;
                }
                channel.sendMessage("unknown command :face_in_clouds:").queue();
            }
        }
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        if (Objects.requireNonNull(event.getUser()).isBot())
            return;
        if (event.getMessageId().equals(selectGraphMessageId)) {
            final String emoji = event.getReactionEmote().getEmoji();
            if (emoji.equals("⬅️") && currentGraphId > 1) {
                currentGraphId--;
                event.getChannel().deleteMessageById(selectGraphMessageId).queue();
                sendCurrentGraph(event.getChannel());
            }
            else if (emoji.equals("➡️") && currentGraphId < graphs.size()) {
                currentGraphId++;
                event.getChannel().deleteMessageById(selectGraphMessageId).queue();
                sendCurrentGraph(event.getChannel());
            }
            else if (emoji.equals("🆗")) {
                selectedGraphId = currentGraphId;
                event.getChannel().deleteMessageById(selectGraphMessageId).queue();
                event.getChannel().sendMessage("`" + Objects.requireNonNull(event.getMember()).getEffectiveName() + "` selected graph `" + currentGraphId + "` :partying_face:").queue();
            }
        }
    }

    public void sendCurrentGraph(MessageChannel channel) {
        drawer.save(graphs.get(currentGraphId - 1));
        channel
            .sendMessage("graph `" + currentGraphId + "` of `" + graphs.size() + "`")
            .addFile(new File("image.png"))
            .queue(response -> {
                response.addReaction("⬅️").queue();
                response.addReaction("🆗").queue();
                response.addReaction("➡️").queue();
                selectGraphMessageId = response.getId();
            });
    }
}
