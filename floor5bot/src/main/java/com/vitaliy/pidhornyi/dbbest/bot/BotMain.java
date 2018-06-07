package com.vitaliy.pidhornyi.dbbest.bot;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class BotMain extends TelegramLongPollingBot {
    private static Logger logger = Logger.getLogger(BotMain.class);
    private CoincideChecker coincideChecker;

    public BotMain() {
        coincideChecker = new CoincideChecker();
    }

    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(update.getMessage().getChatId())
                .setText(update.getMessage().getText());
            try {
                String answer = coincideChecker.findAnswerForMessage(
                    update.getMessage().getText(),
                    update.getMessage().getFrom().getFirstName() + "_" + update.getMessage().getFrom().getLastName());
                if (answer != null && !answer.equals("")) {
                    execute(message.setText(answer));
                }
            } catch (TelegramApiException e) {
                logger.error(e);
            }
        } else {
            if (update.getMessage().getSticker() != null) {
                processStiker(update);
            }
        }
    }

    private void processStiker(Update update) {
        String answer = coincideChecker.findAnswerForStikker(update.getMessage().getSticker().getEmoji());
        if (answer != null && !answer.equals("")) {
            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(update.getMessage().getChatId())
                .setText(update.getMessage().getText());
            try {
                execute(message.setText(answer));
            } catch (TelegramApiException e) {
                logger.error(e);
            }
        }
    }

    public String getBotUsername() {
        return "dbbestFloor5";
    }

    public String getBotToken() {
        return "595481323:AAHNZRQY-R1mmyOU_sweaxSIK3eqbuyjc2s";
    }

    public static class Main {
        public static void main(String[] args) {

            ApiContextInitializer.init();

            TelegramBotsApi botsApi = new TelegramBotsApi();

            try {
                botsApi.registerBot(new BotMain());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
