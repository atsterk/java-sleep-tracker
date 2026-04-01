package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult {
    private final String textResult;
    private final Number numResult;
    private final String description;

    public SleepAnalysisResult(String result, String description) {
        this.textResult = result;
        this.numResult = null;
        this.description = description;
    }

    public SleepAnalysisResult(Number result, String description) {
        this.numResult = result;
        this.textResult = null;
        this.description = description;
    }

    public void printResult() {
        System.out.println(description + getResult());
    }

    public Object getResult() {
        return textResult != null ? textResult : numResult;
    }
}
