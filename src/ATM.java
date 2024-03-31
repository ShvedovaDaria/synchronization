class BankAccount {
        private double balance;

    public BankAccount(double initialBalance) {
            this.balance = initialBalance;
        }

        public synchronized void deposit(double amount) {
            balance += amount;
            System.out.println(Thread.currentThread().getName() + " deposited " + amount + ", new balance is " + balance);
        }

        public synchronized void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " withdrew " + amount + ", new balance is " + balance);
        } else {
            System.out.println(Thread.currentThread().getName() + " tried to withdraw " + amount + " but insufficient funds.");
        }
    }
}

class Transaction implements Runnable {
    private BankAccount account;
    private boolean isDeposit;
    private double amount;

    public Transaction(BankAccount account, boolean isDeposit, double amount) {
        this.account = account;
        this.isDeposit = isDeposit;
        this.amount = amount;
    }

    @Override
    public void run() {
        if (isDeposit) {
            account.deposit(amount);
        } else {
            account.withdraw(amount);
        }
    }
}

public class ATM {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000);

        Thread[] threads = new Thread[4]; // Creating 4 threads

        for (int i = 0; i < threads.length; i++) {
            boolean isDeposit = Math.random() > 0.5; // Randomly selecting deposit or withdraw
            double amount = Math.random() * 500; // Random amount between 0 and 500
            threads[i] = new Thread(new Transaction(account, isDeposit, amount), "Thread-" + i);
        }

        // Starting all threads
        for (Thread thread : threads) {
            thread.start();
        }
    }
}
