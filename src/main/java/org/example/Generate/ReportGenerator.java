package org.example.Generate;

import org.example.Model.Transaction;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {

    public void generateReport(List<Transaction> transactions) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String reportFileName = "src/main/java/org/example/ReportFile/report.txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(reportFileName, true))) {
            for (Transaction transaction : transactions) {
                String formattedDateTime = transaction.getTime().format(formatter);

                String reportLine = String.format(
                        "%s | %s | перевод с %s на %s %d | %s",
                        formattedDateTime,
                        transaction.getFileName(),
                        transaction.getFromAccount(),
                        transaction.getToAccount(),
                        transaction.getRemittance(),
                        transaction.isSuccess()
                );

                writer.println(reportLine);
            }

            System.out.println("Отчет сгенерирован успешно. Файл: " + reportFileName);
        } catch (IOException e) {
            System.out.println("Ошибка при генерации отчета.");
        }
    }
    public static List<Transaction> readReport(String reportFileName) throws IOException {
        List<Transaction> transactions = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (BufferedReader reader = new BufferedReader(new FileReader(reportFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                    String dateTimeString = parts[0].trim();
                    String fileName = parts[1].trim();
                    String transferDetails = parts[2].trim();
                    String status = parts[3].trim();

                    LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

                    String[] transferParts = transferDetails.split("\\s+");
                    if (transferParts.length == 6) {
                        String fromAccount = transferParts[2];
                        String toAccount = transferParts[4];
                        int remittance = Integer.parseInt(transferParts[5]);

                        Transaction transaction = new Transaction(fromAccount, toAccount, remittance, dateTime, status, fileName);
                        transaction.setFileName(fileName);
                        transactions.add(transaction);
                    } else {
                        System.out.println("Ошибка в формате строки в отчете: " + line);
                    }

            }
        }

        return transactions;
    }
}
