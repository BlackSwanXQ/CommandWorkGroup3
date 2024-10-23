package com.example.CommandWorkGroup3.listener;

import com.example.CommandWorkGroup3.entity.UserRecommendationTelegram;
import com.example.CommandWorkGroup3.repository.UserRecommendationTelegramRepository;
import com.example.CommandWorkGroup3.services.NotificationTaskService;
import com.example.CommandWorkGroup3.services.TelegramBotClient;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    //    @Autowired
    private final TelegramBot telegramBot;

    private final TelegramBotClient telegramBotClient;
    private final NotificationTaskService notificationTaskService;
    private final UserRecommendationTelegramRepository userRecommendationTelegramRepository;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final Pattern pattern = Pattern.compile(
            "(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)");

    TelegramBotUpdatesListener(
            TelegramBot telegramBot, TelegramBotClient telegramBotClient,
            NotificationTaskService notificationTaskService,
            UserRecommendationTelegramRepository userRecommendationTelegramRepository) {
        this.telegramBot = telegramBot;
        this.telegramBotClient = telegramBotClient;
        this.notificationTaskService = notificationTaskService;
        this.userRecommendationTelegramRepository = userRecommendationTelegramRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> {
                logger.info("Processing update: {}", update);
                // Process your updates here
                String text = update.message().text();
                long chatId = update.message().chat().id();
                if ("/start".equals(text)) {
                    telegramBotClient.sendMessage(chatId, "Hello " + update.message().chat().firstName());
                } else if ("/stop".equals(text)) {
                    telegramBotClient.sendMessage(chatId, "Goodbye " + update.message().chat().firstName());
                } else if ("/recommend".equals(text)) {
                    if (!userRecommendationTelegramRepository.findAllByUserId(chatId).isEmpty()) {
                        List<UserRecommendationTelegram> rules = new ArrayList<>(userRecommendationTelegramRepository.findAllByUserId(chatId));
//                        List<String> args = rules.stream().map(r -> r.getRule().getArguments()).toList();
                        List<UserRecommendationTelegram> changeCount = rules.stream().map(r -> {
                            r.setCount(r.getCount() + 1);
                            return r;
                        }).toList();
                        userRecommendationTelegramRepository.saveAll(changeCount);

                        telegramBotClient.sendMessage(chatId, "Ищете способ быстро и без лишних хлопот получить нужную сумму? " +
                                "Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту." +
                                "Почему выбирают нас:" +
                                "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов." +
                                "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении." +
                                "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое." +
                                "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!" +
                                "FromDB");
                    } else {
                        telegramBotClient.sendMessage(chatId, "Пользователь не найден.");
                    }
                } else if (text != null) {
                    Matcher matcher = pattern.matcher(text);
                    if (!matcher.find()) {
                        telegramBotClient.sendMessage(chatId, "It's not a pattern. Try again.");
                    } else {
                        LocalDateTime notificationDateTime;
                        text = matcher.group(3);
                        if ((notificationDateTime = parse(matcher.group(1))) != null) {
                            notificationTaskService.save(text, chatId, notificationDateTime);
                        } else {
                            telegramBotClient.sendMessage(chatId, "Invalid date and time format");
                        }
                    }

                } else {
                    telegramBotClient.sendMessage(chatId, "This is not text"); // ???
                }


//                createNotification(update);
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

//    private void createNotification(Update update) {
//
//        Matcher matcher = pattern.matcher(update.message().text());
//        if (matcher.matches()) {
//            // обрабатываем ситуацию, когда строка соответствует паттерну
//            LocalDateTime dateTime = LocalDateTime.parse(matcher.group(1),
//                    formatter);
////                    DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
//            NotificationTask notificationTask = new NotificationTask();
//            notificationTask.setId(null);
//            notificationTask.setDate(dateTime);
//            notificationTask.setChatID(update.message().chat().id());
//            notificationTask.setNotification(matcher.group(3));
//            notificationTaskRepository.save(notificationTask);
//        }
//    }

    @Nullable
    private LocalDateTime parse(String dateTime) {
        try {
            return LocalDateTime.parse(dateTime, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

}
