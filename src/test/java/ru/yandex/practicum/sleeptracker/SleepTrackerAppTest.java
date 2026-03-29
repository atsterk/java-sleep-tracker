package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SleepTrackerAppTest {
    public static final String DATE_TIME_FORMAT = "dd.MM.yy HH:mm";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    private static final CountSessions COUNT_SESSIONS = new CountSessions();
    private static final FindMinDuration FIND_MIN_DURATION = new FindMinDuration();
    private static final FindMaxDuration FIND_MAX_DURATION = new FindMaxDuration();
    private static final FindAverageDuration FIND_AVERAGE_DURATION = new FindAverageDuration();
    private static final CountBadSessions COUNT_BAD_SESSIONS = new CountBadSessions();
    private static final CountSleeplessNights COUNT_SLEEPLESS_NIGHTS = new CountSleeplessNights();
    private static final defineChronotype DEFINE_CHRONOTYPE = new defineChronotype();

    @Test
    public void testCountSessions() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("01.01.25 23:00", formatter),
                LocalDateTime.parse("02.01.25 07:00", formatter), SleepSessionQuality.BAD));
        Assertions.assertEquals(1, COUNT_SESSIONS.apply(sleepingSessions).getResult());

        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("02.01.25 23:00", formatter),
                LocalDateTime.parse("03.01.25 07:00", formatter), SleepSessionQuality.BAD));
        Assertions.assertEquals(2, COUNT_SESSIONS.apply(sleepingSessions).getResult());
    }

    @Test
    public void testFindMinDuration() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("01.01.25 23:00", formatter),
                LocalDateTime.parse("02.01.25 07:00", formatter), SleepSessionQuality.BAD));
        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("02.01.25 11:00", formatter),
                LocalDateTime.parse("02.01.25 14:00", formatter), SleepSessionQuality.BAD));
        Assertions.assertEquals(180, FIND_MIN_DURATION.apply(sleepingSessions).getResult());

        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("02.01.25 23:15", formatter),
                LocalDateTime.parse("02.01.25 23:30", formatter), SleepSessionQuality.BAD));
        Assertions.assertEquals(15, FIND_MIN_DURATION.apply(sleepingSessions).getResult());
    }

    @Test
    public void testFindMaxDuration() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("01.01.25 23:00", formatter),
                LocalDateTime.parse("02.01.25 04:00", formatter), SleepSessionQuality.BAD));
        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("02.01.25 11:00", formatter),
                LocalDateTime.parse("02.01.25 14:00", formatter), SleepSessionQuality.BAD));
        Assertions.assertEquals(300, FIND_MAX_DURATION.apply(sleepingSessions).getResult());

        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("02.01.25 23:00", formatter),
                LocalDateTime.parse("03.01.25 07:00", formatter), SleepSessionQuality.BAD));
        Assertions.assertEquals(480, FIND_MAX_DURATION.apply(sleepingSessions).getResult());
    }

    @Test
    public void testFindAverageDuration() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("01.01.25 23:00", formatter),
                LocalDateTime.parse("02.01.25 04:00", formatter), SleepSessionQuality.BAD));
        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("02.01.25 11:00", formatter),
                LocalDateTime.parse("02.01.25 14:00", formatter), SleepSessionQuality.BAD));
        Assertions.assertEquals(240.0, FIND_AVERAGE_DURATION.apply(sleepingSessions).getResult());

        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("02.01.25 23:00", formatter),
                LocalDateTime.parse("03.01.25 07:00", formatter), SleepSessionQuality.BAD));
        Assertions.assertEquals(320.0, FIND_AVERAGE_DURATION.apply(sleepingSessions).getResult());
    }

    @Test
    public void testCountBadSessions() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("01.01.25 23:00", formatter),
                LocalDateTime.parse("02.01.25 04:00", formatter), SleepSessionQuality.GOOD));
        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("02.01.25 11:00", formatter),
                LocalDateTime.parse("02.01.25 14:00", formatter), SleepSessionQuality.BAD));
        Assertions.assertEquals(1, COUNT_BAD_SESSIONS.apply(sleepingSessions).getResult());

        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("02.01.25 23:00", formatter),
                LocalDateTime.parse("03.01.25 07:00", formatter), SleepSessionQuality.BAD));
        Assertions.assertEquals(2, COUNT_BAD_SESSIONS.apply(sleepingSessions).getResult());

        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("03.01.25 23:00", formatter),
                LocalDateTime.parse("04.01.25 07:00", formatter), SleepSessionQuality.NORMAL));
        Assertions.assertEquals(2, COUNT_BAD_SESSIONS.apply(sleepingSessions).getResult());
    }

    @Test
    public void testCountSleeplessNightsStartAfterMidday() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("01.01.25 23:00", formatter),
                LocalDateTime.parse("02.01.25 05:00", formatter), SleepSessionQuality.GOOD));
        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("02.01.25 11:00", formatter),
                LocalDateTime.parse("02.01.25 14:00", formatter), SleepSessionQuality.BAD));
        Assertions.assertEquals(0, COUNT_SLEEPLESS_NIGHTS.apply(sleepingSessions).getResult());

        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("02.01.25 23:00", formatter),
                LocalDateTime.parse("03.01.25 07:00", formatter), SleepSessionQuality.BAD));
        Assertions.assertEquals(0, COUNT_SLEEPLESS_NIGHTS.apply(sleepingSessions).getResult());

        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("04.01.25 07:00", formatter),
                LocalDateTime.parse("04.01.25 11:00", formatter), SleepSessionQuality.NORMAL));
        Assertions.assertEquals(1, COUNT_SLEEPLESS_NIGHTS.apply(sleepingSessions).getResult());
    }

    @Test
    public void testCountSleeplessNightsStartBeforeMidday() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("01.01.25 08:00", formatter),
                LocalDateTime.parse("01.01.25 17:00", formatter), SleepSessionQuality.GOOD));
        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("01.01.25 23:00", formatter),
                LocalDateTime.parse("02.01.25 06:00", formatter), SleepSessionQuality.BAD));
        Assertions.assertEquals(1, COUNT_SLEEPLESS_NIGHTS.apply(sleepingSessions).getResult());

        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("02.01.25 23:00", formatter),
                LocalDateTime.parse("03.01.25 07:00", formatter), SleepSessionQuality.BAD));
        Assertions.assertEquals(1, COUNT_SLEEPLESS_NIGHTS.apply(sleepingSessions).getResult());

        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("04.01.25 07:00", formatter),
                LocalDateTime.parse("04.01.25 11:00", formatter), SleepSessionQuality.NORMAL));
        Assertions.assertEquals(2, COUNT_SLEEPLESS_NIGHTS.apply(sleepingSessions).getResult());
    }

    @Test
    public void testCountSleeplessNightsDifferentMonths() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("30.01.25 23:00", formatter),
                LocalDateTime.parse("31.01.25 07:00", formatter), SleepSessionQuality.GOOD));
        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("31.01.25 23:00", formatter),
                LocalDateTime.parse("01.02.25 06:00", formatter), SleepSessionQuality.BAD));
        Assertions.assertEquals(0, COUNT_SLEEPLESS_NIGHTS.apply(sleepingSessions).getResult());

        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("01.02.25 23:00", formatter),
                LocalDateTime.parse("02.02.25 07:00", formatter), SleepSessionQuality.BAD));
        Assertions.assertEquals(0, COUNT_SLEEPLESS_NIGHTS.apply(sleepingSessions).getResult());

        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("03.02.25 07:00", formatter),
                LocalDateTime.parse("03.02.25 11:00", formatter), SleepSessionQuality.NORMAL));
        Assertions.assertEquals(1, COUNT_SLEEPLESS_NIGHTS.apply(sleepingSessions).getResult());
    }

    @Test
    public void testDefineChronotype() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("01.01.25 20:00", formatter),
                LocalDateTime.parse("02.01.25 06:00", formatter), SleepSessionQuality.GOOD));
        Assertions.assertEquals("Жаворонок", DEFINE_CHRONOTYPE.apply(sleepingSessions).getResult());
        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("02.01.25 23:30", formatter),
                LocalDateTime.parse("03.01.25 10:00", formatter), SleepSessionQuality.BAD));
        Assertions.assertEquals("Голубь", DEFINE_CHRONOTYPE.apply(sleepingSessions).getResult());

        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("03.01.25 12:00", formatter),
                LocalDateTime.parse("03.01.25 15:00", formatter), SleepSessionQuality.BAD));
        Assertions.assertEquals("Голубь", DEFINE_CHRONOTYPE.apply(sleepingSessions).getResult());

        sleepingSessions.add(new SleepingSession(LocalDateTime.parse("03.01.25 23:00", formatter),
                LocalDateTime.parse("04.01.25 09:00", formatter), SleepSessionQuality.NORMAL));
        Assertions.assertEquals("Сова", DEFINE_CHRONOTYPE.apply(sleepingSessions).getResult());
    }
}