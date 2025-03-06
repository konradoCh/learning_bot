package org.learning_bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Responder extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {

        String response = "I am sorry, I don't understand the message you sent";

        String chatId = "";

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(response);

        if (update.hasCallbackQuery() && update.getCallbackQuery().getData() != null && !update.getCallbackQuery().getData().isEmpty()) {
            chatId = update.getCallbackQuery().getMessage().getChatId().toString();

            String callBackData = update.getCallbackQuery().getData();

            if(callBackData.equalsIgnoreCase(CallBackData.CD_YES.toString())) {
                LocalDateTime currentTime = LocalDateTime.now();
                sendMessage.setText(currentTime.toLocalTime().toString());
            }

            if (callBackData.equalsIgnoreCase(CallBackData.CD_NO.toString())) {
                sendMessage.setText("fine, thanks!");
            }
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            chatId = update.getMessage().getChatId().toString();
            String userMessage = update.getMessage().getText().trim();

            if (userMessage.equalsIgnoreCase("Hello")) {
                sendMessage.setText("How are you?");
            }

            if (userMessage.equalsIgnoreCase("How are you?")) {
                sendMessage.setText("I'm fine thank you");
            }

            if (userMessage.contains("time")) {

                sendMessage.setText("Would you like to know the current time?");

                //First create the keyboard
                List<List<InlineKeyboardButton>> keyboard = new ArrayList<>(); // tworzenie przycisków

                //Then we create the buttons: row
                List<InlineKeyboardButton> buttonsRow = new ArrayList<>();

                //Create yes button
                InlineKeyboardButton yesButton = new InlineKeyboardButton();
                yesButton.setText("Yes?");  // wygląd/tekst przycisku
                yesButton.setCallbackData(CallBackData.CD_YES.toString());

                InlineKeyboardButton noThankButton = new InlineKeyboardButton();
                noThankButton.setText("No thanks");
                noThankButton.setCallbackData(CallBackData.CD_NO.toString());

                //We add the yes button to the buttons row
                buttonsRow.add(yesButton);
                buttonsRow.add(noThankButton);

                //We add the newly created buttons row that contains the yes button to the keyboard
                keyboard.add(buttonsRow);

                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                inlineKeyboardMarkup.setKeyboard(keyboard);

                sendMessage.setReplyMarkup(inlineKeyboardMarkup);
            }

            if (userMessage.equalsIgnoreCase("/day")) {
                DayOfWeek todayDayOfTheWeek = LocalDateTime.now().getDayOfWeek();
                sendMessage.setText(todayDayOfTheWeek.toString());
            }
        }


        if (chatId.isEmpty()) {
            throw new IllegalStateException("The chat ID couldn't be identified or found");
        }

        sendMessage.setChatId(chatId);

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
