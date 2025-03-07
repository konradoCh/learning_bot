package org.learning_bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Responder extends TelegramLongPollingBot {

    @Override
    public synchronized void onUpdateReceived(Update update) {

        try {


            String response = "I am sorry, I don't understand the message you sent";

            String chatId = "";

            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(response);

            if (update.hasCallbackQuery() && update.getCallbackQuery().getData() != null && !update.getCallbackQuery().getData().isEmpty()) {
                chatId = update.getCallbackQuery().getMessage().getChatId().toString();

                String callBackData = update.getCallbackQuery().getData();

                if (callBackData.equalsIgnoreCase(CallBackData.CD_YES.toString())) {
                    LocalDateTime currentTime = LocalDateTime.now();
                    sendMessage.setText(currentTime.toLocalTime().toString());
                }

                if (callBackData.equalsIgnoreCase(CallBackData.CD_NO.toString())) {
                    sendMessage.setText("fine, thanks!");
                }
            }


            if (update.hasMessage()) {
                chatId = update.getMessage().getChatId().toString();

                if (update.getMessage().hasText()) {
                    String userMessage = update.getMessage().getText().trim();

                    if (userMessage.equalsIgnoreCase("Hello")) {
                        sendMessage.setText("How are you? \uD83D\uDE00");
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
                        yesButton.setText("\uD83D\uDC4D");  // wygląd/tekst przycisku
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

                    if (userMessage.contains("contact")) {
                        sendMessage.setText("Can you share your contact details (phone)?");

                        //1 - Create KeyboardRow
                        KeyboardRow keyboardRow = new KeyboardRow();

                        //2 - Create KeyboardButton
                        KeyboardButton keyboardButton = new KeyboardButton();
                        keyboardButton.setText("Yes, share contact");
                        keyboardButton.setRequestContact(true);

                        //3 - Add the KeyboardButton to the KeyboardRow
                        keyboardRow.add(keyboardButton);

                        //4 - Create list of KeyboardRows
                        List<KeyboardRow> listOfKeyboardRows = new ArrayList<>();

                        //5 - Add KeyboardRow of the listOfKeyboardRows
                        listOfKeyboardRows.add(keyboardRow);

                        //6 - Create Reply KeyboardMarkup and set parameters
                        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                        replyKeyboardMarkup.setKeyboard(listOfKeyboardRows);
                        replyKeyboardMarkup.setOneTimeKeyboard(true);
                        replyKeyboardMarkup.setResizeKeyboard(true);

                        sendMessage.setReplyMarkup(replyKeyboardMarkup);

                    }
                }

                if (update.getMessage().hasContact()) {
                    sendMessage.setText("Thank you for sending us your phone number. We will contact you shortly.");

                    String phoneNumber = update.getMessage().getContact().getPhoneNumber().trim();
//                String vCard = update.getMessage().getContact().getVCard();
                    //You can now encrypt the phone number and store it.

                }
            }


            if (chatId.isEmpty()) {
                throw new IllegalStateException("The chat ID couldn't be identified or found");
            }

            sendMessage.setChatId(chatId);


                sendApiMethod(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            } catch (Exception exception) {
                //Execute logic to handle generic exceptions
                exception.printStackTrace();
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
