package bankmaneger.bankmanager;

public class BankAccount {
    private String accountNumber;
    private double balance;
    private AccountType accountType;
    private AccountState state;

    public BankAccount(String accountNumber, AccountType type) {
        this.accountNumber = accountNumber;
        this.accountType = type;
        this.balance = 0.0;
        this.state = AccountState.ACTIVE;
    }

    public void deposit(double amount) throws IllegalArgumentException {
        if (state != AccountState.ACTIVE) {
            throw new IllegalStateException("Account is not active.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        balance += amount;
    }

    public void withdraw(double amount) throws Exception {
        if (state != AccountState.ACTIVE) {
            throw new IllegalStateException("Account is not active.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > balance) {
            throw new Exception("Insufficient funds.");
        }
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void changeState(AccountState newState) {
        this.state = newState;
    }

    public enum AccountType {
        CHECKING,
        SAVINGS,
        BUSINESS,
        UNKNOWN
        // Default value der im Nachhinein von ChatGPT4 hinzugefügt wurde, um den Test zu bestehen
    }

    public enum AccountState {
        ACTIVE,
        SUSPENDED,
        CLOSED
    }
}

