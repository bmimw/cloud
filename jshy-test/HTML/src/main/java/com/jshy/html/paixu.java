package com.jshy.html;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class paixu {
    public static void main(String[] args) {
        // 指定要排序的文本文件的路径
        String filePath = "D:\\Hei_Ma\\cloud-store\\jshy\\jshy-test\\HTML\\src\\main\\resources\\sensitive-words.txt";

        // 读取文件内容到列表
        List<String> lines = readFile(filePath);

        // 对列表中的内容进行排序
        Collections.sort(lines);

        // 将排序后的内容写回到文件
        writeFile(filePath, lines);

        System.out.println("文件内容已排序并写回到 " + filePath);
    }

    // 读取文件内容到列表
    private static List<String> readFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    // 将排序后的内容写回到文件
    private static void writeFile(String filePath, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
