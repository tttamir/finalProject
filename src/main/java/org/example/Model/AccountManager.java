package org.example.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AccountManager {

    private List<BankAccount> accounts;

    public AccountManager() {
        this.accounts = new ArrayList<>();
    }

    public void loadAccounts(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length == 2) {
                    String accountNumber = parts[0];
                    int balance = Integer.parseInt(parts[1]);
                    accounts.add(new BankAccount(accountNumber, balance));
                }
            }
        }
    }

    public void updateAccountBalance(Transaction transaction) {
        String fromAccount = transaction.getFromAccount();
        String toAccount = transaction.getToAccount();
        int remittance = transaction.getRemittance();
        BankAccount fromBankAccount = getBankAccount(fromAccount);
        BankAccount toBankAccount = getBankAccount(toAccount);

        if (fromBankAccount != null && toBankAccount != null) {
            if (remittance > 0) {
                if (fromBankAccount.withdraw(remittance)) {
                    toBankAccount.deposit(remittance);
                } else {
                    System.out.println("Ошибка: недостаточно средств на счете " + fromAccount);
                    transaction.setSuccessMessage("Недостаточно средств на счете");
                }
            }
            else {
                System.out.println("Ошибка: неверная сумма перевода " + fromAccount);
                transaction.setSuccessMessage("Неверная сумма перевода");
            }
        } else {
            System.out.println("Ошибка: не найден один из счетов");
            transaction.setSuccessMessage("Не найден один из счетов");
        }
    }

    public void saveAccounts(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (BankAccount account : accounts) {
                writer.write(account.getAccountNumber() + " " + account.getBalance());
                writer.newLine();
            }
        }
    }

    private BankAccount getBankAccount(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
}

