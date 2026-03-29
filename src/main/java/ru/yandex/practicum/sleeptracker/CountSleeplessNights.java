package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;

public class CountSleeplessNights implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {

        LocalDate start = sleepingSessions.getFirst().getSleepSessionStart().toLocalDate();
        LocalDate end = sleepingSessions.getLast().getSleepSessionEnd().toLocalDate();

        long amountOfNights;

        if (sleepingSessions.getFirst().getSleepSessionStart().getHour() < 12) {
            amountOfNights = ChronoUnit.DAYS.between(start, end) + 1;
        } else {
            amountOfNights = ChronoUnit.DAYS.between(start, end);
        }

        long sleepfulNights = sleepingSessions.stream()
                .filter(session -> session.getSleepSessionStart().getDayOfMonth()
                        != session.getSleepSessionEnd().getDayOfMonth()
                        || session.getSleepSessionStart().getHour() < 6)
                .map(session -> session.getSleepSessionEnd().toLocalDate())
                .distinct()
                .count();

        return new SleepAnalysisResult((int) (amountOfNights - sleepfulNights),
                "Количество бессонных ночей: ");
    }
}
