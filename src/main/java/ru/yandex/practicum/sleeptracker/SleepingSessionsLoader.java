package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SleepingSessionsLoader {
    public static final String DATE_TIME_FORMAT = "dd.MM.yy HH:mm";
    private final String path;

    public SleepingSessionsLoader(String path) {
        this.path = path;
    }

    public List<SleepingSession> getSleepingSessions() throws IOException, DateTimeParseException {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

        try (Stream<String> lines = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            lines.map(line -> line.split(";"))
                    .forEach(array -> sleepingSessions.add(new SleepingSession(LocalDateTime.parse(array[0], formatter),
                            LocalDateTime.parse(array[1], formatter), SleepSessionQuality.valueOf(array[2]))));
            return sleepingSessions;
        }
    }
}
