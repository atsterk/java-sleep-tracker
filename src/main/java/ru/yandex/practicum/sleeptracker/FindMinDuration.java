package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class FindMinDuration implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        Optional<Duration> duration = sleepingSessions.stream()
                .map(SleepingSession::getSessionDuration)
                .min(Duration::compareTo);
        return new SleepAnalysisResult((int) duration.orElseThrow().toMinutes(),
                "Продолжительность самой короткой сессии в минутах: ");
    }
}
