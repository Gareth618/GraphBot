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
import java.util.List;
import java.util.Objects;

public class Bot extends ListenerAdapter {
    private List<Graph> graphs = null;
    private final SpringClient client = new SpringClient();

    public static void main(String[] args) {
        try {
            final JDA jda = JDABuilder
                .createDefault("OTgzNDE1NjQyMDcxOTA0MzQ3.GO8_2T.DIzgcIHdaUATXvEYf5M153hAVHVtHwmjWA9Z4g")
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
            final String author = Objects.requireNonNull(event.getMember()).getEffectiveName();
            final String request = event.getMessage().getContentRaw();
            final MessageChannel channel = event.getChannel();

            if (request.startsWith("$ ")) {
                final String content = request.substring("$ ".length());
                if (content.equals("select graph")) {
                    graphs = client.getGraphs();
                    channel.sendMessage("`" + author + "`: " + graphs + " :partying_face:").queue(response -> {
                        response.addReaction("⬅️").queue();
                        response.addReaction("\uD83C\uDD97").queue();
                        response.addReaction("➡️").queue();
                    });
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
