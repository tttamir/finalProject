package org.example.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private String fromAccount;
    private String toAccount;
    private int remittance;
    private LocalDateTime time;
    private String successMessage;
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public Transaction(String fromAccount, String toAccount, int remittance, LocalDateTime time, String successMessage, String fileName) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.remittance = remittance;
        this.time = time;
        this.successMessage = successMessage;
        this.fileName = fileName;
    }


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return time.format(formatter) + " | "+
                fileName + " | " +
                "перевод с " + fromAccount +
                " на " + toAccount +
                " " + remittance +
                " | " + successMessage;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public int getRemittance() {
        return remittance;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
    public String isSuccess() {
        return successMessage;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
