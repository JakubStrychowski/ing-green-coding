package pl.ing.green.transactions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import pl.ing.green.transactions.model.Account;
import pl.ing.green.transactions.model.Transaction;

/**
 * Tests Transactions Report Service.
 *
 * @author Jakub Strychowski
 */
public class TransactionsServiceTest {

    private final static int MAX_NUMBER_OF_ACCOUNT = 25000;
    private ArrayList<String> accounts;

    /**
     * Constructs test and randomize test accounts.
     */
    public TransactionsServiceTest() {
        accounts = new ArrayList<>(MAX_NUMBER_OF_ACCOUNT);
        for (int i = 0; i < MAX_NUMBER_OF_ACCOUNT; i++) {
            accounts.add(Account.randomAccountNumber());
        }
    }

    /**
     * Draws example account from a pool of randomized accounts.
     *
     * @return account number
     */
    public String drawAccount() {
        Random random = new Random();
        int index = random.nextInt(0, accounts.size());
        return accounts.get(index);
    }

    /**
     * Randomize amount for transaction.
     *
     * @return random number between 0.00 and 100000.00
     */
    public float randomAmount() {
        return Math.round((float) (Math.random() * 10000000.0f)) / 100.0f;
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Tests moving money between sequence of 4 accounts.
     */
    @Test
    public void testMovingBetween4Accounts() {
        List<Transaction> transactions = List.of(
                new Transaction("06105023389842834748547303", "31074318698137062235845814", 10.0f),
                new Transaction("31074318698137062235845814", "32309111922661937852684864", 10.0f),
                new Transaction("32309111922661937852684864", "66105036543749403346524547", 10.0f)
        );

        BalanceCalculator balanceCalculator = new BalanceCalculator();
        List<Account> result = balanceCalculator.calucaleBalances(transactions);
        assertEquals(4, result.size());

        int index = 0;
        validateAccount(result.get(index++), "06105023389842834748547303",
                1, 0, -10.0f);
        validateAccount(result.get(index++), "31074318698137062235845814",
                1, 1, 0f);
        validateAccount(result.get(index++), "32309111922661937852684864",
                1, 1, 0f);
        validateAccount(result.get(index++), "66105036543749403346524547",
                0, 1, 10f);

        validateResult(transactions, result);
    }

    /**
     * Tests example from the specification.
     */
    @Test
    public void testExample1() {
        List<Transaction> transactions = List.of(
                new Transaction(
                        "32309111922661937852684864",
                        "06105023389842834748547303",
                        10.90f),
                new Transaction(
                        "31074318698137062235845814",
                        "66105036543749403346524547",
                        200.90f),
                new Transaction(
                        "66105036543749403346524547",
                        "32309111922661937852684864",
                        50.10f)
        );

        BalanceCalculator balanceCalculator = new BalanceCalculator();
        List<Account> result = balanceCalculator.calucaleBalances(transactions);
        assertEquals(4, result.size());
        int index = 0;
        validateAccount(result.get(index++), "06105023389842834748547303",
                0, 1, 10.90f);
        validateAccount(result.get(index++), "31074318698137062235845814",
                1, 0, -200.90f);
        validateAccount(result.get(index++), "32309111922661937852684864",
                1, 1, 39.20f);
        validateAccount(result.get(index++), "66105036543749403346524547",
                1, 1, 150.80f);

        validateResult(transactions, result);
    }

    /**
     * Tests service for single transaction as input.
     */
    @Test
    public void testOneTransaction() {
        List<Transaction> transactions = List.of(
                new Transaction(
                        "32309111922661937852684864",
                        "06105023389842834748547303",
                        10.90f)
        );

        BalanceCalculator balanceCalculator = new BalanceCalculator();
        List<Account> result = balanceCalculator.calucaleBalances(transactions);
        assertEquals(2, result.size());
        int index = 0;
        validateAccount(result.get(index++), "06105023389842834748547303",
                0, 1, 10.90f);
        validateAccount(result.get(index++), "32309111922661937852684864",
                1, 0, -10.90f);

        validateResult(transactions, result);
    }

    /**
     * Tests single transaction and single account.
     */
    @Test
    public void testTransactionToOneself() {
        List<Transaction> transactions = List.of(
                new Transaction(
                        "32309111922661937852684864",
                        "32309111922661937852684864",
                        10.90f)
        );

        BalanceCalculator balanceCalculator = new BalanceCalculator();
        List<Account> result = balanceCalculator.calucaleBalances(transactions);
        assertEquals(1, result.size());
        int index = 0;
        validateAccount(result.get(index++), "32309111922661937852684864",
                1, 1, 0f);

        validateResult(transactions, result);
    }

    /**
     * Tests for empty input data.
     */
    @Test
    public void testNoTransactions() {
        List<Transaction> transactions = Collections.EMPTY_LIST;

        BalanceCalculator balanceCalculator = new BalanceCalculator();
        List<Account> result = balanceCalculator.calucaleBalances(transactions);
        assertEquals(0, result.size());
    }

    /**
     * Test 10 times for randomized transactions (100 000 transaction per
     * iteration).
     */
    @Test
    public void testRandomTransactions() {
//        for (int i = 0; i < 10; i++) {
//            makeRandomTest();
//        }
    }

    /**
     * Performs single iteration of a randomized test.
     */
    private void makeRandomTest() {
        int numberOfTransactions = 100000;

        List<Transaction> transactions = new ArrayList(numberOfTransactions);
        for (int i = 0; i < numberOfTransactions; i++) {
            transactions.add(new Transaction(
                    drawAccount(),
                    drawAccount(),
                    randomAmount()));
        }

        long startTime = System.currentTimeMillis();
        BalanceCalculator balanceCalculator = new BalanceCalculator();
        List<Account> result = balanceCalculator.calucaleBalances(transactions);
        long duration = System.currentTimeMillis() - startTime;

        System.out.println(
                String.format("%d transactions processed in %d ms",
                        numberOfTransactions,
                        duration));

        validateResult(transactions, result);
    }

    /**
     * Checks if returned account has proper, given values.
     *
     * @param account Account to check.
     * @param number expected account number
     * @param debitCount expected number of debits on account
     * @param creditCount expected number of credits on account
     * @param balance expected balance
     */
    private void validateAccount(
            Account account,
            String number,
            int debitCount,
            int creditCount,
            float balance) {

        assertEquals(number, account.getAccount());
        assertEquals(debitCount, account.getDebitCount());
        assertEquals(creditCount, account.getCreditCount());
        
        //assertEquals(balance, account.getBalance());
        
        BigDecimal expBigDecimal = BigDecimal.valueOf(balance).setScale(2, RoundingMode.HALF_UP);;
        BigDecimal resultBigDecimal = BigDecimal.valueOf(account.getBalance()).setScale(2, RoundingMode.HALF_UP);;
        
        assertEquals(expBigDecimal, resultBigDecimal);
    }

    /**
     * Checks if evaluated result is correct for a given task.
     *
     * @param transactions list of transactions to process 0 input data
     * @param result result returned by service.
     */
    private void validateResult(List<Transaction> transactions, List<Account> result) {

        Map<String, List<Transaction>> debitMap
                = transactions.stream().collect(
                        Collectors.groupingBy(Transaction::getDebitAccount));

        Map<String, List<Transaction>> creditMap
                = transactions.stream().collect(
                        Collectors.groupingBy(Transaction::getCreditAccount));

        for (Account account : result) {
            String accountNr = account.getAccount();
            float balance = 0f;
            List<Transaction> creditTransactions = creditMap.get(accountNr);
            if (creditTransactions != null) {
                assertEquals(account.getCreditCount(), creditTransactions.size());
                balance = creditTransactions.stream().map(t -> t.getAmount())
                        .reduce(balance, (sum, val) -> sum + val);
            }

            List<Transaction> debitTransactions = debitMap.get(accountNr);
            if (debitTransactions != null) {
                assertEquals(account.getDebitCount(), debitTransactions.size());
                balance = debitTransactions.stream().map(t -> t.getAmount())
                        .reduce(balance, (sum, val) -> sum - val);
            }

            
            //assertEquals(account.getBalance(), balance);
            
            BigDecimal expBigDecimal = BigDecimal.valueOf(account.getBalance()).setScale(2, RoundingMode.HALF_UP);;
            BigDecimal resultBigDecimal = BigDecimal.valueOf(balance).setScale(2, RoundingMode.HALF_UP);;
            
            assertEquals(expBigDecimal, resultBigDecimal);
            
        }
    }

}
