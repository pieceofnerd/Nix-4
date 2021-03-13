package ua.com.alevel;

import lombok.NonNull;

import java.util.Scanner;

public class AlgorithmicTask {

    public static void main(String[] args) {
        SchoolScheduleUtil.endOfLesson();
    }
}

class SchoolScheduleUtil {
    public static int chooseLesson() {
        System.out.print("\nDetermining the end of the lesson\nPlease enter the lesson number: ");
        Scanner scanner = new Scanner(System.in);
        @NonNull
        int numberOfLesson = scanner.nextInt();
        scanner.close();
        return numberOfLesson;
    }

    public static void endOfLesson() {
        int numberLesson = chooseLesson();
        int lessonDuration = 45;
        int notEvenLessonBreak = 5;
        int evenLessonBreak = 15;
        int res = numberLesson * lessonDuration + (numberLesson) / 2 * notEvenLessonBreak + (numberLesson - 1) / 2 * evenLessonBreak;
        int start = 9;
        System.out.println(start + res / 60 + " " + res % 60);
    }
}

