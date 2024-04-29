import bankmaneger.bankmanager.SavingsAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SavingsAccountTest {
    private SavingsAccount savingsAccount;

    @BeforeEach
    void setUp() {
        savingsAccount = new SavingsAccount("67890", 5.0);  // 5% interest rate
    }

    @Test
    void applyInterest() {
        savingsAccount.deposit(1000.0);
        savingsAccount.applyInterest();
        assertEquals(1050.0, savingsAccount.getBalance(), 0.01);
    }
}
