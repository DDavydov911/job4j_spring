package ru.job4j_spring;

import java.util.Scanner;

public class ConsoleInput {

    private Scanner scanner = new Scanner(System.in);

    public String askStr(String question) {
        System.out.print(question);
        return scanner.nextLine();

    }
}
