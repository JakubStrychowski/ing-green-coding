/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.ing.green.transactions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import pl.ing.green.transactions.model.Account;
import pl.ing.green.transactions.model.Transaction;

/**
 *
 * @author Jakub Strychowski
 */
public class TransactionsServiceTest {
    
    private final static int MAX_NUMBER_OF_ACCOUNT = 25000;
    ArrayList<String> accounts;
    
    private final static String[] TEST_ACCOUNTS = {
        "06105023389842834748547303",
        "31074318698137062235845814",
        "32309111922661937852684864",
        "66105036543749403346524547"
    };
    
    public TransactionsServiceTest() {
        accounts = new ArrayList<>(MAX_NUMBER_OF_ACCOUNT);
        for (int i = 0; i < MAX_NUMBER_OF_ACCOUNT; i++) {
            accounts.add(Account.randomAccountNumber());            
        }
    }

    public String randomAccount() {
        int index = (int) Math.floor(Math.random() * accounts.size());
        return accounts.get(index);
    }
    
    public BigDecimal randomAmount() {
        return BigDecimal.valueOf(Math.round(Math.random() * 100000.0) / 100.0);
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

//    /**
//     * Test of calucaleBalances method, of class TransactionsService.
//     */
//    @org.junit.jupiter.api.Test
//    public void testCalucaleBalances() {
//        System.out.println("calucaleBalances");
//        List<Transaction> transactions = null;
//        TransactionsService instance = new TransactionsService();
//        List<Account> expResult = null;
//        List<Account> result = instance.calucaleBalances(transactions);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
    public void testMovingBetween4Accounts() {
        List<Transaction> transactions = List.of(
            new Transaction(TEST_ACCOUNTS[0], TEST_ACCOUNTS[1], new BigDecimal("10.00")),
            new Transaction(TEST_ACCOUNTS[1], TEST_ACCOUNTS[2], new BigDecimal("10.00")),
            new Transaction(TEST_ACCOUNTS[2], TEST_ACCOUNTS[3], new BigDecimal("10.00"))
        );
        
        
        TransactionsService transactionsService = new TransactionsService();
        List<Account> result = transactionsService.calucaleBalances(transactions);
        assertEquals(4, result.size());
        assertEquals(new BigDecimal("-10.00"), result.get(0).getBalance());
        assertEquals(new BigDecimal("0.00"), result.get(1).getBalance());
        assertEquals(new BigDecimal("0.00"), result.get(2).getBalance());
        assertEquals(new BigDecimal("10.00"), result.get(3).getBalance());
    }
    
    public void testRandom100000TransactionsTime() {
        
        int numberOfTransactions = 100000;
        
        List<Transaction> transactions = new ArrayList(numberOfTransactions);
        for (int i = 0 ; i < numberOfTransactions; i++) {
            transactions.add(new Transaction(
                    randomAccount(),
                    randomAccount(),
                    randomAmount()));
        }
        
        TransactionsService transactionsService = new TransactionsService();
        List<Account> result = transactionsService.calucaleBalances(transactions);
        assertTrue(result.size() > 0);
        
    }
    

//    /**
//     * Test of report method, of class TransactionsService.
//     */
//    @org.junit.jupiter.api.Test
//    public void testReport() {
//        System.out.println("report");
//        List<Transaction> transactions = null;
//        TransactionsService instance = new TransactionsService();
//        ResponseEntity<List<Account>> expResult = null;
//        ResponseEntity<List<Account>> result = instance.report(transactions);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
