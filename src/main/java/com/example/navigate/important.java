package com.example.navigate;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class important {

    public static String getUniqueID(){
        UUID uuid = UUID.randomUUID();
        long longValue = uuid.getLeastSignificantBits();

        // Convert the least significant bits to a 12-character hexadecimal string
        String hexString = Long.toHexString(longValue);

        // If the length is less than 12, pad with zeros
        while (hexString.length() < 5) {
            hexString = "0" + hexString;
        }

        // Take the last 6 characters to achieve a length of 6
        return "#"+hexString.substring(1,7);
    }

    public static String getDate(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static Alert showErrorAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        alert.setContentText(msg);
        return alert;
    }

    public static Alert showSuccessAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task Completed");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        alert.setContentText("SuccessFully Completed");
        return alert;
    }
    public static Alert showWarningAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        alert.setContentText(msg);
        return alert;
    }

    public static boolean mobileIsValid(String mobileNumber) {
        // Regular expression for a valid Sri Lankan mobile number
        String regex = "0?[1-9][0-9]{8}";

        // Creating a pattern object
        Pattern pattern = Pattern.compile(regex);

        // Creating a matcher object
        Matcher matcher = pattern.matcher(mobileNumber);

        // Verifying whether the given mobile number matches the pattern
        return matcher.matches();
    }

    public static boolean isNumeric(String str) {
        // Regular expression to check if the string is a number
        return str.matches("\\d*(\\.\\d+)?");
    }

}
