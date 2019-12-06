package com.starplex;

import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        var input = new Scanner(System.in);
        String path = input.nextLine();
        HashMap<Integer, HashMap<Integer, Double>> result = new HashMap<>();
        HashMap<Integer, HashMap<Integer, Double>> execTime = new HashMap<>();
        Pattern resPattern = Pattern.compile("S = (.*)");
        Pattern timePattern = Pattern.compile("execution time (.*)s\\n");

        try (var ignored = Files.walk(Paths.get(path))) {
            ignored.forEach(filePath -> {
                if (filePath.toString().endsWith(".out")) {
                    var fileName = filePath.getFileName().toString();
                    var data = Arrays.stream(fileName.split("\\.")[0]
                            .split("_"))
                            .map(Integer::decode)
                            .collect(Collectors.toUnmodifiableList());
                    try (Scanner reader = new Scanner(new FileInputStream(filePath.toFile()))) {
                        result.putIfAbsent(data.get(0), new HashMap<>());
                        result.get(data.get(0)).put(data.get(1),
                                reader.findAll(resPattern).findAny()
                                        .map(x -> x.group(1))
                                        .map(Double::parseDouble)
                                        .get());
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                    try (Scanner reader = new Scanner(new FileInputStream(filePath.toFile()))) {
                        execTime.putIfAbsent(data.get(0), new HashMap<>());
                        execTime.get(data.get(0)).put(data.get(1),
                                reader.findAll(timePattern).findAny()
                                        .map(x -> x.group(1))
                                        .map(Double::parseDouble)
                                        .get());
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 1; i <= 128; i *= 2) {
            for (int j = 128; j <= 33_000; j *= 2) {
                System.out.print(result.get(i).get(j) + ",");
            }
            System.out.println();
        }
        System.out.println();
        for (int i = 1; i <= 128; i *= 2) {
            for (int j = 128; j <= 33_000; j *= 2) {
                System.out.print(execTime.get(i).get(j) + ",");
            }
            System.out.println();
        }
    }
}
