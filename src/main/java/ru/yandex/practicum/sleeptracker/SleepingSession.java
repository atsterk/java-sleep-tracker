package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;

public class SleepingSession {
    private final LocalDateTime sleepSessionStart;
    private final LocalDateTime sleepSessionEnd;
    private final SleepSessionQuality sleepSessionQuality;

    public SleepingSession(LocalDateTime sleepSessionStart, LocalDateTime sleepSessionEnd,
                           SleepSessionQuality sleepSessionQuality) {
        this.sleepSessionStart = sleepSessionStart;
        this.sleepSessionEnd = sleepSessionEnd;
        this.sleepSessionQuality = sleepSessionQuality;
    }

    public Duration getSessionDuration() {
        return Duration.between(sleepSessionStart, sleepSessionEnd);
    }

    public LocalDateTime getSleepSessionStart() {
        return sleepSessionStart;
    }

    public LocalDateTime getSleepSessionEnd() {
        return sleepSessionEnd;
    }

    public SleepSessionQuality getSleepSessionQuality() {
        return sleepSessionQuality;
    }
}
