import bankmaneger.bankmanager.BankAccount;
import bankmaneger.bankmanager.BankApp;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

class BankAppTest extends ApplicationTest {

    private BankApp myApp;  // Referenz auf die Anwendung

    @Override
    public void start(Stage stage) throws Exception {
        myApp = new BankApp();
        myApp.start(stage);
    }

    @Test
    void testInitialBalance() {
        verifyThat("#balanceField", hasText("Kontostand: 0,00"));
    }

    @Test
    void testDepositInteraction() {
        clickOn("#amountField").write("500");
        clickOn("#depositButton");
        verifyThat("#balanceField", hasText("Kontostand: 500,00"));
    }

    @Test
    void testWithdrawInteraction() {
        clickOn("#amountField").write("500");
        clickOn("#depositButton");  // Zuerst Einzahlen
        clickOn("#amountField").clickOn().eraseText(3).write("100");  // Betrag ändern
        clickOn("#withdrawButton");
        verifyThat("#balanceField", hasText("Kontostand: 400,00"));
    }

        @Test
    void testInvalidDeposit() {
        clickOn("#amountField").write("invalid");
        clickOn("#depositButton");
        verifyThat("#balanceField", hasText("Ungültige Eingabe. Bitte eine Zahl eingeben."));
    }

    @Test
    void testInvalidWithdraw() {
        clickOn("#amountField").write("invalid");
        clickOn("#withdrawButton");
        verifyThat("#balanceField", hasText("Ungültige Eingabe. Bitte eine Zahl eingeben."));
    }

    @Test
    void testWithdrawMoreThanBalance() {
        clickOn("#amountField").write("1000");
        clickOn("#withdrawButton");
        verifyThat("#balanceField", hasText("Insufficient funds."));  // Nachricht basierend auf dem tatsächlichen Fehler-String
    }

    @Test
    void testApplyInterestSavingsAccount() {
        clickOn("#amountField").write("1000");
        clickOn("#depositButton");
        waitForFxEvents();
        clickOn("#applyInterestButton");
        verifyThat("#balanceField", hasText("Kontostand: 1005,00"));
    }

    @Test
    void testApplyInterestNonSavingsAccount() {
        BankAccount nonSavingsAccount = new BankAccount("67890", BankAccount.AccountType.CHECKING);
        myApp.setAccount(nonSavingsAccount);
        clickOn("#amountField").write("1000");
        clickOn("#depositButton");
        waitForFxEvents();
        clickOn("#applyInterestButton");
        verifyThat("#balanceField", hasText("Kontostand: 1000,00"));
    }

    @Test
    public void testAppLaunch() {
        // Dieser Test prüft, ob die Anwendung gestartet wird, ohne dass eine Exception geworfen wird.
        assertDoesNotThrow(() -> BankApp.main(new String[]{}));
    }

}
