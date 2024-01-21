package org.example.Read;

import org.example.Model.Transaction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {

    public List<Transaction> parseFile(File file) throws IOException {
        List<Transaction> transactions = new ArrayList<>();
        String fromAccount = null;
        String toAccount = null;
        int remittance = 0;


        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("account from:")) {
                    fromAccount = extractAccountNumber(line);
                } else if (line.startsWith("account to:")) {
                    toAccount = extractAccountNumber(line);
                } else if (line.startsWith("deposit:")) {
                    remittance = extractRemittance(line);
                    if (fromAccount != null && toAccount != null) {
                        LocalDateTime time = LocalDateTime.now();
                        Transaction transaction = new Transaction(fromAccount, toAccount, remittance, time, "Успешно обработан", file.getName());
                        transactions.add(transaction);
                        fromAccount = null;
                        toAccount = null;
                        remittance = 0;
                    }
                }
            }
        }
        return transactions;
    }

    private String extractAccountNumber(String line) {
        Pattern pattern = Pattern.compile("\\b\\d{5}-\\d{5}\\b");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group();
        }
        else
            return null;
    }

    private int extractRemittance(String line) {
        Pattern pattern = Pattern.compile("-?\\d+");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        } else {
            return 0;
        }
    }

}
