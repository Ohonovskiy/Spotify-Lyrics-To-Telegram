package main;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Objects;

import static main.Parser.getLyrics;
import static main.Start.getCurrentTrack;

public class Main {
    public static void main(String[] args) throws Exception {

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        try {
            telegramBotsApi.registerBot(new TelegramBot());
            String buffer = getCurrentTrack();
            while (true) {
                if (!buffer.equals(getCurrentTrack())) {
                    buffer = getCurrentTrack();
                }
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
