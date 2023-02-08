package com.Jeka8833.DkrKpi;

import com.google.gson.Gson;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class StudentDB {
    private final Set<Student> students = new HashSet<>();

    public Set<Student> getStudents() {
        return students;
    }


    private static final Path PATH = Paths.get("students.json");
    private static final Gson GSON = new Gson();
    private static final Random RANDOM = new Random();
    public static StudentDB database = new StudentDB();

    public static void read() throws IOException {
        if (Files.isRegularFile(PATH)) {
            System.out.println("Read file: " + PATH.toAbsolutePath());

            database = GSON.fromJson(new String(Files.readAllBytes(PATH), StandardCharsets.UTF_8), StudentDB.class);
        } else {
            for (int i = 0; i < 30; i++) {
                database.students.add(new Student(UUID.randomUUID().toString(),
                        RANDOM.nextBoolean() ? "A" : "B", RANDOM.nextInt(5)));
            }
        }
    }

    public static void write() throws IOException {
        System.out.println("Write file: " + PATH.toAbsolutePath());

        Files.write(PATH, GSON.toJson(database).getBytes(StandardCharsets.UTF_8));
    }

    public static void replaceStudent(@NotNull Student oldS, @NotNull Student newS) {
        database.getStudents().remove(oldS);
        database.getStudents().add(newS);
    }

    @NotNull
    @Contract(" -> new")
    public static List<String> getGroupsWithoutDv() {
        Map<String, List<Student>> groups = database.getStudents().stream()
                .collect(Collectors.groupingBy(student -> student.group));

        List<String> groupList = new ArrayList<>();
        for (Map.Entry<String, List<Student>> entry : groups.entrySet()) {
            boolean foundDv = entry.getValue().stream()
                    .anyMatch(student -> student.score <= 2);
            if (!foundDv) {
                groupList.add(entry.getKey());
            }
        }
        return groupList;
    }

    @NotNull
    @Contract("_ -> new")
    public static List<Student> getStudents(@NotNull String group) {
        return database.getStudents().stream()
                .filter(student -> student.group.equalsIgnoreCase(group))
                .collect(Collectors.toList());
    }

    @Nullable
    public static Student getStudent(@NotNull String name) {
        return StudentDB.database.getStudents().stream()
                .filter(student -> student.name.equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }
}
