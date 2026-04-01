package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {
    private static final String PATH = "C:\\Users\\ki_ri\\IdeaProjects\\java-sleep-tracker\\src\\main\\resources\\sleep_log.txt";

    private static final CountSessions COUNT_SESSIONS = new CountSessions();
    private static final FindMinDuration FIND_MIN_DURATION = new FindMinDuration();
    private static final FindMaxDuration FIND_MAX_DURATION = new FindMaxDuration();
    private static final FindAverageDuration FIND_AVERAGE_DURATION = new FindAverageDuration();
    private static final CountBadSessions COUNT_BAD_SESSIONS = new CountBadSessions();
    private static final CountSleeplessNights COUNT_SLEEPLESS_NIGHTS = new CountSleeplessNights();
    private static final DefineChronotype DEFINE_CHRONOTYPE = new DefineChronotype();

    private static final List<Function<List<SleepingSession>, SleepAnalysisResult>> FUNCTIONS =
            List.of(COUNT_SESSIONS, FIND_MIN_DURATION, FIND_MAX_DURATION, FIND_AVERAGE_DURATION,
                    COUNT_BAD_SESSIONS, COUNT_SLEEPLESS_NIGHTS, DEFINE_CHRONOTYPE);

    public static void main(String[] args) {
        SleepingSessionsLoader sleepingSessionsLoader = new SleepingSessionsLoader(PATH);
        try {
            List<SleepingSession> sleepingSessions = sleepingSessionsLoader.getSleepingSessions();
            FUNCTIONS.stream()
                    .map(f -> f.apply(sleepingSessions))
                    .forEach(SleepAnalysisResult::printResult);

        } catch (RuntimeException exp) {
            System.out.println(exp.getMessage());
        }
    }
}