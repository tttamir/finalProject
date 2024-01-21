package org.example;

import org.example.Check.FileUtils;
import org.example.Check.FileValidator;
import org.example.Generate.ReportGenerator;
import org.example.Model.AccountManager;
import org.example.Model.Transaction;
import org.example.Read.FileParser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Выберите операцию:");
            System.out.println("1. Парсинг файлов");
            System.out.println("2. Вывод списка всех переводов");

            int userInput = scanner.nextInt();

            switch (userInput) {
                case 1:
                    List<File> inputFiles = FileUtils.getFilesFromDirectory("src/main/java/org/example/input", ".txt");
                    if(inputFiles.isEmpty()){
                        System.out.println("Нет подходящих файлов в каталоге");
                    }
                    for (File file : inputFiles) {
                        if (FileValidator.isValidFile(file) && FileValidator.isValidFileExtension(file, ".txt")) {
                            List<Transaction> transactions = new FileParser().parseFile(file);

                            AccountManager accountManager = new AccountManager();
                            accountManager.loadAccounts("src/main/java/org/example/Accounts/accounts.txt");
                            for (Transaction transaction : transactions) {
                                accountManager.updateAccountBalance(transaction);
                            }
                            accountManager.saveAccounts("src/main/java/org/example/Accounts/accounts.txt");
                            new ReportGenerator().generateReport(transactions);

                            File archiveDirectory = new File("src/main/java/org/example/archive");
                            if (!archiveDirectory.exists()) {
                                archiveDirectory.mkdir();
                            }
                            File archivedFile = new File(archiveDirectory, file.getName());
                            if (file.renameTo(archivedFile)) {
                                System.out.println("Файл перемещен в директорию 'archive'.");
                            } else {
                                System.out.println("Ошибка при перемещении файла в директорию 'archive'.");
                            }

                        } else {
                            System.out.println("Некорректный файл: " + file.getName());
                        }

                    }
                    break;
                case 2:
                    System.out.println("Список всех переводов из файла-отчета:");
                    List<Transaction> reportTransactions = ReportGenerator.readReport("src/main/java/org/example/ReportFile/report.txt");
                    for (Transaction transaction : reportTransactions) {
                        System.out.println(transaction);
                    }
                    break;
                default:
                    System.out.println("Некорректный ввод.");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
