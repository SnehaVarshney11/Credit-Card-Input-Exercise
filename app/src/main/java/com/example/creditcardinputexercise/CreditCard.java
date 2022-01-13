package com.example.creditcardinputexercise;

import java.util.Calendar;
import java.util.Locale;

public class CreditCard {
    private String CardNum, ExpDate, cvv, FName, LName;

    public CreditCard() {
        this.CardNum = null;
        this.ExpDate = null;
        this.cvv = null;
        this.FName = null;
        this.LName = null;
    }

    public CreditCard(String CardNum, String expDate, String cvv, String fName, String lName) {
        this.CardNum = CardNum;
        this.ExpDate = expDate;
        this.cvv = cvv;
        this.FName = fName;
        this.LName = lName;
    }

    public static boolean isValidCardNum(String cardNum) {
        return Validation(cardNum) && (isValidAmericanExp(cardNum) || isValidMasterCard(cardNum) || isValidVisa(cardNum));
    }

    private static boolean Validation(String cardNum) {
        int complete = 0;
        for (int i = 1; i <= cardNum.length(); i++) {
            int ch = Character.getNumericValue(cardNum.charAt(cardNum.length() - i));
            if (i % 2 == 0) {
                ch *= 2;
                if (ch > 9) {
                    ch -= 9;
                }
            }
            complete += ch;
        }
        return complete % 10 == 0;
    }

    private static boolean isValidAmericanExp(String cardNum) {
        return cardNum.length() == 15 && cardNum.charAt(0) == '3' && (cardNum.charAt(1) == '4' || cardNum.charAt(1) == '7');
    }
    private static boolean isValidMasterCard(String cardNum) {
        return cardNum.length() == 16 && ((cardNum.charAt(0) == '5' && cardNum.charAt(1) >= '1' && cardNum.charAt(1) <= '5') ||
                        (cardNum.charAt(0) == '2' && cardNum.charAt(1) >= '2' && cardNum.charAt(1) <= '7'));
    }

    private static boolean isValidVisa(String cardNumber) {
        return cardNumber.length() >= 16 && cardNumber.length() <= 19 && cardNumber.charAt(0) == '4';
    }

    public static boolean isValidExpDate(String expirationDate) {
        String[] expDateParts = expirationDate.split("/");
        if (expDateParts.length != 2 || expDateParts[0].isEmpty() || expDateParts[1].isEmpty()) {
            return false;
        }
        int expireMonth = Integer.parseInt(expDateParts[0]);
        int expireYear = Integer.parseInt(expDateParts[1]);
        if (!(expireMonth >= 1 && expireMonth <= 12)) {
            return false;
        }
        Calendar today = Calendar.getInstance(Locale.getDefault());
        int todayMonth = today.get(Calendar.MONTH) + 1;
        int todayYear = today.get(Calendar.YEAR) % 100;
        return (expireYear > todayYear) || (expireYear == todayYear && expireMonth >= todayMonth);
    }

    public static boolean isValidCvv(String cardNumber, String cvv) {
        return (isValidAmericanExp(cardNumber) && cvv.length() == 4) || (isValidMasterCard(cardNumber) || isValidVisa(cardNumber) && cvv.length() == 3);
    }
}
