package bankmaneger.bankmanager;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.text.DecimalFormat;

public class BankApp extends Application {
    private BankAccount account = new SavingsAccount("12345", 0.5);
    private static final DecimalFormat df = new DecimalFormat("0.00");

    //Später hinzugefügter Modifizierung-Vorschlag von ChatGPT4 um den Test zu bestehen
    public void setAccount(BankAccount newAccount) {
    this.account = newAccount;
    }

    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        TextField amountField = new TextField();
        amountField.setId("amountField");
        Button depositButton = new Button("Einzahlen");
        depositButton.setId("depositButton");
        Button withdrawButton = new Button("Abheben");
        withdrawButton.setId("withdrawButton");
        Button applyInterestButton = new Button("Zinsen anwenden");
        applyInterestButton.setId("applyInterestButton");
        Label balanceLabel = new Label("Kontostand: " + formatBalance(account.getBalance()));
        balanceLabel.setId("balanceField");

        depositButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                account.deposit(amount);
                balanceLabel.setText("Kontostand: " + formatBalance(account.getBalance()));
            } catch (NumberFormatException ex) {
                balanceLabel.setText("Ungültige Eingabe. Bitte eine Zahl eingeben.");
            }
        });

        withdrawButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                account.withdraw(amount);
                balanceLabel.setText("Kontostand: " + formatBalance(account.getBalance()));
            } catch (NumberFormatException ex) {
                balanceLabel.setText("Ungültige Eingabe. Bitte eine Zahl eingeben.");
            } catch (Exception ex) {
                balanceLabel.setText(ex.getMessage());
            }
        });

        applyInterestButton.setOnAction(e -> {
            if (account instanceof SavingsAccount) {
                ((SavingsAccount) account).applyInterest();
                balanceLabel.setText("Kontostand: " + formatBalance(account.getBalance()));
            }
        });

        root.getChildren().addAll(amountField, depositButton, withdrawButton, applyInterestButton, balanceLabel);
        Scene scene = new Scene(root, 350, 250);
        primaryStage.setTitle("Bankanwendung");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String formatBalance(double balance) {
        return df.format(balance);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
