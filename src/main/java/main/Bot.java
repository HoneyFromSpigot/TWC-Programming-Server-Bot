package main;

import main.Utils.support.SupportManager;
import main.event.Eventlistener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.EnumSet;

public class Bot {
    private final String PREFIX = "!!";

    private static Bot instance;

    private JDA jda;

    private Eventlistener eventlistener;
    private SupportManager supportManager;


    public static void main(String[] args) {
        System.out.println("Starte Bot...");
        instance = new Bot(System.getenv("token"));
    }

    private Bot(String token){
        this.eventlistener = new Eventlistener();
        this.supportManager = new SupportManager();

        try{
            JDABuilder b = JDABuilder.createDefault(token).addEventListeners(eventlistener);

            for(GatewayIntent i : GatewayIntent.values()){
                b.enableIntents(i);
            }

            jda = b.build();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public SupportManager getSupportManager() {
        return supportManager;
    }

    public String getPrefix() {
        return PREFIX;
    }

    public static Bot getInstance() {
        return instance;
    }
}
