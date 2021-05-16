package ua.com.alevel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

public class ParserJson {

    public static <T> List<String> readJson(String file) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<String> dates = objectMapper.readValue(readFile(file), new TypeReference<>() {
            });
            return dates;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Problems with ObjectMapper");
        }

    }

    public static String readFile(String file) {
        Path fileName = Path.of(file);
        try {
            return Files.readString(fileName);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }


}
