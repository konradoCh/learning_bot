package org.learning_bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Responder extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return Bot.USERNAME;
    }

    @Override
    public String getBotToken() {
        return Bot.BOT_TOKEN;
    }
}
