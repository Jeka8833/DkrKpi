package com.Jeka8833.DkrKpi;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        try {
            StudentDB.read();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Count student: " + StudentDB.database.getStudents().size());
            while (true) {
                System.out.println();
                System.out.println();
                System.out.println("Menu:");
                System.out.println("1 - Add student");
                System.out.println("2 - Edit");
                System.out.println("3 - List");
                System.out.println("4 - List without 'Dvoichnikiv'");
                System.out.println("5 - Find all students in group");
                System.out.println("exit - Exit");

                int selected = readInt(scanner, "Select: ");
                if (selected == 1) {
                    String name = readText(scanner, "Name: ");
                    String group = readText(scanner, "Group: ");
                    int score = readInt(scanner, "Score: ");

                    StudentDB.database.getStudents().add(new Student(name, group, score));
                } else if (selected == 2) {
                    String name = readText(scanner, "Searched name: ");

                    Student studentFound = StudentDB.getStudent(name);
                    if (studentFound == null) {
                        System.out.println("Student not found");
                    } else {
                        String newName = readText(scanner, "New name: ");
                        String newGroup = readText(scanner, "New group: ");
                        int newScore = readInt(scanner, "New score: ");

                        StudentDB.replaceStudent(studentFound, new Student(newName, newGroup, newScore));
                    }
                } else if (selected == 3) {
                    StudentDB.database.getStudents().forEach(System.out::println);
                } else if (selected == 4) {
                    List<String> groups = StudentDB.getGroupsWithoutDv();
                    if (groups.isEmpty()) {
                        System.out.println("All groups have 'dvoichnikov'");
                    } else {
                        for (String group : groups) {
                            System.out.println(group + " group don't have 'dvoichnikov':");
                            StudentDB.getStudents(group).forEach(System.out::println);
                            System.out.println();
                        }
                    }
                } else if (selected == 5) {
                    String group = readText(scanner, "Search group: ");

                    List<Student> studentsFound = StudentDB.getStudents(group);
                    if (studentsFound.isEmpty()) {
                        System.out.println("Group not found");
                    } else {
                        for (Student student : studentsFound) {
                            System.out.println(student);
                        }
                    }
                } else {
                    System.out.println("Try again");
                }
            }
        } catch (ExitException e) {
            // Ignore
        } catch (Exception exception) {
            System.out.println("Error when reading a file:");
            exception.printStackTrace();
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.println("Close after: " + (5 - i) + " second");
                    Thread.sleep(1000);
                } catch (Exception ignored) {
                }
            }
        } finally {
            StudentDB.write();
        }
    }

    @NotNull
    public static String readText(@NotNull Scanner scanner, String label) throws ExitException {
        System.out.print(label);
        String s = scanner.nextLine().trim();
        if (s.equalsIgnoreCase("exit")) throw new ExitException();
        return s;
    }

    public static int readInt(@NotNull Scanner scanner, String label) throws ExitException {
        System.out.print(label);
        while (true) {
            try {
                String s = scanner.nextLine().trim();
                if (s.equalsIgnoreCase("exit")) throw new ExitException();

                return Integer.parseInt(s);
            } catch (Exception exception) {
                System.out.println("Попробуйте ещё раз ввести число");
            }
        }
    }
}
