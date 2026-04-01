package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class CountBadSessions implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        long badSessions = sleepingSessions.stream()
                .filter(session -> session.getSleepSessionQuality() == SleepSessionQuality.BAD)
                .count();
        return new SleepAnalysisResult((int) badSessions, "Количество сессий с плохим качеством сна: ");
    }
}
