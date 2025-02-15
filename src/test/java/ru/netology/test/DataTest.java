package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


class DataTest {


    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Plan")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        $("[data-test-id=city] .input__control").setValue(validUser.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(firstMeetingDate);
        $("[data-test-id=name] .input__control").setValue(validUser.getName());
        $("[data-test-id=phone] .input__control").setValue(validUser.getPhone());
        $("[data-test-id=agreement] .checkbox__box").click();
        $$(".button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__title").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("div.notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id=success-notification] .icon-button__text").click();
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(secondMeetingDate);
        $(".button__content").click();
        $("[data-test-id=replan-notification] .notification__title").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id=replan-notification] .notification__content").shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id=replan-notification] .button__text").click();
        $("[data-test-id=success-notification] .notification__title").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("div.notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate));
    }
}