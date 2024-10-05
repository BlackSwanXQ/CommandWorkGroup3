package com.example.CommandWorkGroup3.services;

import com.example.CommandWorkGroup3.interfaces.RecommendationRuleSet;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("topSaving")
public class TopSaving implements RecommendationRuleSet {

private final String name = "Top Saving";
private final String id = "59efc529-2fff-41af-baff-90ccd7402925";
   private final String description ="Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем!\n" +
            "\n" +
            "Преимущества «Копилки»:\n" +
            "\n" +
            "Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет.\n" +
            "\n" +
            "Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости.\n" +
            "\n" +
            "Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг.\n" +
            "\n" +
            "Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!";

   @Override
   public String getRecommendation(UUID user) {
      return "topSaving";
   }


}
