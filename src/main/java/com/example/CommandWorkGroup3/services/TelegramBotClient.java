package com.example.CommandWorkGroup3.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TelegramBotClient {

    private final TelegramBot telegramBot;
    private Logger logger = LoggerFactory.getLogger(TelegramBotClient.class);

    public TelegramBotClient(TelegramBot bot) {
        this.telegramBot = bot;
    }

    public void sendMessage(long chatId, String message) {
        SendResponse sendResponse = telegramBot.execute(new SendMessage(chatId, message));
        if (!sendResponse.isOk()) {
            logger.error("Send message failed {}", sendResponse.message());
//            Message message1 = new Message();
//            message1.chat()
        }
    }
}
