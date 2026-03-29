package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class FindAverageDuration implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        double average = (double) sleepingSessions.stream()
                .map(SleepingSession::getSessionDuration)
                .mapToLong(Duration::toMinutes).sum() / sleepingSessions.size();
        return new SleepAnalysisResult(average, "Средняя продолжительность сессии в минутах: ");
    }
}
