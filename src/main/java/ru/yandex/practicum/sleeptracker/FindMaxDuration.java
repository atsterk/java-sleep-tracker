package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

public class FindMaxDuration implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) throws NoSuchElementException {
        Optional<Duration> duration = sleepingSessions.stream()
                .map(SleepingSession::getSessionDuration)
                .max(Duration::compareTo);
        return new SleepAnalysisResult((int) duration.orElseThrow().toMinutes(),
                "Продолжительность самой долгой сессии в минутах: ");
    }
}
