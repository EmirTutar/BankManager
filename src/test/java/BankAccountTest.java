import bankmaneger.bankmanager.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {
    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount("12345", BankAccount.AccountType.CHECKING);
    }

    @Test
    void depositValidAmount() {
        account.deposit(100.0);
        assertEquals(100.0, account.getBalance());
    }

    @Test
    void depositNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-100.0));
    }

    @Test
    void withdrawValidAmount() throws Exception {
        account.deposit(200.0);
        account.withdraw(100.0);
        assertEquals(100.0, account.getBalance());
    }

    @Test
    void withdrawExcessiveAmount() {
        assertThrows(Exception.class, () -> account.withdraw(100.0));
    }

    @Test
    void accountInactiveState() {
        account.changeState(BankAccount.AccountState.SUSPENDED);
        assertThrows(IllegalStateException.class, () -> account.deposit(50.0));
    }

    @Test
    void withdrawWithoutFunds() {
        BankAccount account = new BankAccount("12345", BankAccount.AccountType.CHECKING);
        Exception exception = assertThrows(Exception.class, () -> account.withdraw(100.0));
        assertTrue(exception.getMessage().contains("Insufficient funds"));
    }

    @Test
    void withdrawNegativeOrZeroAmount() {
        BankAccount account = new BankAccount("12345", BankAccount.AccountType.CHECKING);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-100.0));
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(0.0));
    }

    @Test
    void getAccountNumber() {
        BankAccount account = new BankAccount("12345", BankAccount.AccountType.CHECKING);
        assertEquals("12345", account.getAccountNumber());
    }

    @Test
    void getAccountType() {
        BankAccount account = new BankAccount("12345", BankAccount.AccountType.CHECKING);
        assertEquals(BankAccount.AccountType.CHECKING, account.getAccountType());
    }

    @Test
    void changeStateToClosedAndAttemptTransaction() {
        BankAccount account = new BankAccount("12345", BankAccount.AccountType.CHECKING);
        account.changeState(BankAccount.AccountState.CLOSED);
        assertThrows(IllegalStateException.class, () -> account.deposit(50.0));
        assertThrows(IllegalStateException.class, () -> account.withdraw(50.0));
    }

}
