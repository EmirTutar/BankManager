import bankmaneger.bankmanager.BankAccount;
import bankmaneger.bankmanager.BankManagings;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class BankManagingsTest {

    @Test
    void calculateFeeChecking() {
        BankAccount account = new BankAccount("12345", BankAccount.AccountType.CHECKING);
        assertEquals(1.0, BankManagings.calculateFee(account, 100.0));  // 1% fee
    }

    @Test
    void calculateCompoundInterest() {
    double result = BankManagings.calculateCompoundInterest(1000, 0.05, 12, 1);
    assertEquals(1004.17, result, 0.01);  // Korrekter Wert basierend auf der tatsächlichen Berechnung
    }

    @Test
    void calculateFeeUnknownAccountType() {
        BankAccount account = new BankAccount("12345", BankAccount.AccountType.UNKNOWN);
        assertEquals(0, BankManagings.calculateFee(account, 100.0));  // Erwarten 0 Gebühr für unbekannten Kontotyp
    }

    @Test
    void transferFundsEnoughBalance() throws Exception {
        BankAccount from = new BankAccount("11111", BankAccount.AccountType.SAVINGS);
        BankAccount to = new BankAccount("22222", BankAccount.AccountType.CHECKING);
        from.deposit(1000.0);
        BankManagings.transfer(from, to, 900.0);
        assertEquals(95.5, from.getBalance(), 0.01);  // Korrekter verbleibender Betrag nach Abzug der Gebühr
        assertEquals(900.0, to.getBalance());
    }


    @Test
    void transferFundsNotEnoughBalance() {
        BankAccount from = new BankAccount("11111", BankAccount.AccountType.SAVINGS);
        BankAccount to = new BankAccount("22222", BankAccount.AccountType.CHECKING);
        from.deposit(500.0);
        assertThrows(Exception.class, () -> BankManagings.transfer(from, to, 900.0));
    }

        @Test
    void calculateFeeSavings() {
        BankAccount account = new BankAccount("12345", BankAccount.AccountType.SAVINGS);
        assertEquals(0.5, BankManagings.calculateFee(account, 100.0));  // 0.5% fee
    }

    @Test
    void calculateFeeBusiness() {
        BankAccount account = new BankAccount("12345", BankAccount.AccountType.BUSINESS);
        assertEquals(1.5, BankManagings.calculateFee(account, 100.0));  // 1.5% fee
    }

    @Test
    void batchTransferWithMultipleAccounts() {
        BankAccount from1 = new BankAccount("11111", BankAccount.AccountType.SAVINGS);
        BankAccount from2 = new BankAccount("22222", BankAccount.AccountType.CHECKING);
        BankAccount target = new BankAccount("33333", BankAccount.AccountType.BUSINESS);
        from1.deposit(1000.0);
        from2.deposit(1000.0);
        BankManagings.batchTransfer(Arrays.asList(from1, from2), target, 100.0);
        assertEquals(899.5, from1.getBalance(), 0.01);
        assertEquals(899.0, from2.getBalance(), 0.01);
        assertEquals(200.0, target.getBalance());
    }

    @Test
    void batchTransferWithException() {
        BankAccount from1 = new BankAccount("11111", BankAccount.AccountType.SAVINGS);
        BankAccount from2 = new BankAccount("22222", BankAccount.AccountType.CHECKING);
        BankAccount target = new BankAccount("33333", BankAccount.AccountType.BUSINESS);
        from1.deposit(50.0);  // Nicht genug Geld für die Überweisung nach Abzug der Gebühr
        from2.deposit(1000.0);
        BankManagings.batchTransfer(Arrays.asList(from1, from2), target, 100.0);
        assertEquals(50.0, from1.getBalance(), 0.01);  // Keine Änderung, da Überweisung fehlschlug
        assertEquals(899.0, from2.getBalance(), 0.01);  // Erfolgreiche Überweisung von from2
        assertEquals(100.0, target.getBalance());  // Nur von from2 überwiesen
    }


}
