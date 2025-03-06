package org.learning_bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Responder extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {

        String response = "I am sorry, I don't understand the message you sent";

        String chatId = update.getMessage().getChatId().toString();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(response);

        if (update.hasMessage() && update.getMessage().hasText()) {
            String userMessage = update.getMessage().getText().trim();

            if (userMessage.equalsIgnoreCase("Hello")) {
                sendMessage.setText("How are you?");
            }

            if (userMessage.equalsIgnoreCase("How are you?")) {
                sendMessage.setText("I'm fine thank you");
            }
        }


        try {
            sendApiMethod(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

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
