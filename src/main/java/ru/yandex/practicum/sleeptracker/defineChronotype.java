package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class defineChronotype implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        long owlNights = sleepingSessions.stream()
                .filter(session -> session.getSleepSessionStart().getHour() == 23
                        || session.getSleepSessionStart().getHour() < 6 && session.getSleepSessionEnd().getHour() >= 9)
                .count();
        long larkNights = sleepingSessions.stream()
                .filter(session -> session.getSleepSessionStart().getHour() < 22
                        && session.getSleepSessionStart().getHour() > 6 && session.getSleepSessionEnd().getHour() < 7)
                .count();
        long doveNights = sleepingSessions.stream()
                .filter(session -> !(session.getSleepSessionStart().getHour() > 6 //фильтр по дневным сессиям
                        && session.getSleepSessionEnd().getHour() >= 7))
                .count() - owlNights - larkNights;

        long maxNights = Math.max(owlNights, Math.max(larkNights, doveNights));

        if (owlNights == larkNights || maxNights == doveNights) {
            return new SleepAnalysisResult("Голубь", "Хронотип: ");
        } else if (maxNights == larkNights) {
            return new SleepAnalysisResult("Жаворонок", "Хронотип");
        } else {
            return new SleepAnalysisResult("Сова", "Хронотип: ");
        }
    }
}
