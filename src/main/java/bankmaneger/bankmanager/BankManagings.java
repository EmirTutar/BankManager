package bankmaneger.bankmanager;

import java.util.List;

public class BankManagings {
    public static double calculateFee(BankAccount account, double amount) {
        switch (account.getAccountType()) {
            case CHECKING:
                return amount * 0.01;  // 1% fee
            case SAVINGS:
                return amount * 0.005;  // 0.5% fee
            case BUSINESS:
                return amount * 0.015;  // 1.5% fee
            default:
                return 0;
        }
    }

    public static double calculateCompoundInterest(double principal, double rate, int times, int years) {
        if (years == 0) {
            return principal;
        } else {
            double newPrincipal = principal * (1 + rate / times);
            return calculateCompoundInterest(newPrincipal, rate, times, years - 1);
        }
    }

    public static void transfer(BankAccount from, BankAccount to, double amount) throws Exception {
        double fee = calculateFee(from, amount);
        double totalAmount = amount + fee;
        if (from.getBalance() < totalAmount) {
            throw new Exception("Überweisung nicht möglich: Nicht genügend Guthaben.");
        }
        from.withdraw(totalAmount);
        to.deposit(amount);
    }

    public static void batchTransfer(List<BankAccount> accounts, BankAccount target, double amount) {
        for (BankAccount source : accounts) {
            while (true) {
                try {
                    transfer(source, target, amount);
                    break;
                } catch (Exception e) {
                    System.out.println("Fehler bei der Überweisung: " + e.getMessage());
                    break;
                }
            }
        }
    }
}
