package main;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.Objects;

import static main.Parser.getLyrics;
import static main.Start.getCurrentTrack;

public class TelegramBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "Weather_Ohonovskiy_bot";
    }

    @Override
    public String getBotToken() {
        return "bot_token"; // 111111:AAAAA
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage();

        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            message.setChatId(chat_id + "");

            if(message_text.equals("Get lyrics") || message_text.equals("gl") || message_text.equals("Gl") || message_text.equals("get lyrics") || message_text.equals("lyrics")) {
                try {
                    message.setText(Objects.requireNonNull(getCurrentTrack()) + "\n\n" + getLyrics(Objects.requireNonNull(getCurrentTrack())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
