/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.ing.green.transactions.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Jakub Strychowski
 */
public class AccountTest {
    
    private final static String TEST_ACCOUNT_1 = "12345678901234567890123456";
    private final static String TEST_ACCOUNT_2 = "22345678901234567890123456";
    
    public AccountTest() {
    }

    @Test
    public void testDebit() {
        Account account = new Account();
        account.setBalance(new BigDecimal("100.55"));
        account.debit(new BigDecimal(0.40));
        assertEquals(new BigDecimal("100.15"), account.getBalance());
        assertEquals(1, account.getDebitCount());
        account.debit(new BigDecimal(50.20));
        assertEquals(new BigDecimal("49.95"), account.getBalance());
        assertEquals(2, account.getDebitCount());
        account.debit(new BigDecimal(0.05));
        assertEquals(new BigDecimal("49.90"), account.getBalance());
        assertEquals(3, account.getDebitCount());
    }

    @Test
    public void testSetDebitCount() {
        Account account = new Account();
        account.setBalance(new BigDecimal("100.55"));
        account.debit(new BigDecimal(0.40));
        assertEquals(1, account.getDebitCount());
        account.setDebitCount(5);
        assertEquals(5, account.getDebitCount());
    }
    
    @Test
    public void testCredit() {
        Account account = new Account();
        account.setBalance(new BigDecimal("100.55"));
        account.credit(new BigDecimal(0.40));
        assertEquals(new BigDecimal("100.95"), account.getBalance());
        assertEquals(1, account.getCreditCount());
        account.credit(new BigDecimal(100.10));
        assertEquals(new BigDecimal("201.05"), account.getBalance());
        assertEquals(2, account.getCreditCount());
        account.credit(new BigDecimal(0.05));
        assertEquals(new BigDecimal("201.10"), account.getBalance());
        assertEquals(3, account.getCreditCount());
    }
    

    @Test
    public void testSetCreditCount() {
        Account account = new Account();
        account.setBalance(new BigDecimal("100.55"));
        account.credit(new BigDecimal(0.40));
        assertEquals(1, account.getCreditCount());
        account.setCreditCount(5);
        assertEquals(5, account.getCreditCount());
    }

    

    @Test
    public void testAccount() {
        Account account = new Account(TEST_ACCOUNT_1);
        assertEquals(TEST_ACCOUNT_1, account.getAccount());
        account.setAccount(TEST_ACCOUNT_2);
        assertEquals(TEST_ACCOUNT_2, account.getAccount());
    }

    

    @Test
    public void testBalance() {
        Account account = new Account();
        account.setBalance(new BigDecimal("100.55"));
        assertEquals(new BigDecimal("100.55"), account.getBalance());
        account.setBalance(new BigDecimal("100.50"));
        assertEquals(new BigDecimal("100.50"), account.getBalance());
        account.setBalance(new BigDecimal("-100.50"));
        assertEquals(new BigDecimal("-100.50"), account.getBalance());
        account.setBalance(new BigDecimal("0"));
        assertEquals(new BigDecimal("0.00"), account.getBalance());
    }
    
    @Test
    public void testToString() {
        Account account = new Account();
        account.setBalance(new BigDecimal("100.55"));
        assertEquals("100.55", account.getBalance().toString());
        account.setBalance(new BigDecimal("100.50"));
        assertEquals("100.50", account.getBalance().toString());
        account.setBalance(new BigDecimal("-100.50"));
        assertEquals("-100.50", account.getBalance().toString());
        account.setBalance(new BigDecimal("0"));
        assertEquals("0.00", account.getBalance().toString());
    }
    
    @Test
    public void testSerializer() throws JsonProcessingException {
        makeSerializationTest(TEST_ACCOUNT_1, "100.50", 1, 2);
        makeSerializationTest(TEST_ACCOUNT_1, "0.00", 0, 0);
        makeSerializationTest(TEST_ACCOUNT_2, "987234.99", 233, 45);
        makeSerializationTest(TEST_ACCOUNT_2, "-987234.01", 23, 5);
        makeSerializationTest(TEST_ACCOUNT_2, "4.10", 2, 3);
    }
    
    private Account buildAccount(String accountStr, String balanceStr, int debitCount, int creditCount) {
        Account account = new Account(accountStr);
        account.setBalance(new BigDecimal(balanceStr));
        account.setCreditCount(creditCount);
        account.setDebitCount(debitCount);
        return account;
    }
            
    private void makeSerializationTest(String accountStr, String balanceStr, int debitCount, int creditCount)  throws JsonProcessingException {
        Account account = buildAccount(accountStr, balanceStr, debitCount, creditCount);
        ObjectMapper mapper = new ObjectMapper();
        String serializedString = mapper.writeValueAsString(account); 
        String expected = String.format("{\"account\":\"%s\",\"debitCount\":%d,\"creditCount\":%d,\"balance\":%s}", 
                accountStr, debitCount,creditCount, balanceStr);
        assertEquals(expected, serializedString);
    }
    

   
    @Test
    public void testEquals() {
        Account account1 = buildAccount(TEST_ACCOUNT_1, "0.00", 0, 0);
        Account account2 = buildAccount(TEST_ACCOUNT_1, "0.00", 0, 0);
        assertTrue(account1.equals(account2));
        assertTrue(account2.equals(account1));

        account1 = buildAccount(TEST_ACCOUNT_1, "1.01", 1, 2);
        account2 = buildAccount(TEST_ACCOUNT_1, "1.01", 1, 2);
        assertTrue(account1.equals(account2));
        assertTrue(account2.equals(account1));
        
        account1 = buildAccount(TEST_ACCOUNT_1, "0.00", 0, 0);
        account2 = buildAccount(TEST_ACCOUNT_1, "0.01", 0, 0);
        assertFalse(account1.equals(account2));
        assertFalse(account2.equals(account1));
        
        account1 = buildAccount(TEST_ACCOUNT_1, "0.00", 0, 0);
        account2 = buildAccount(TEST_ACCOUNT_2, "0.00", 0, 0);
        assertFalse(account1.equals(account2));
        assertFalse(account2.equals(account1));
        
        account1 = buildAccount(TEST_ACCOUNT_1, "0.00", 1, 0);
        account2 = buildAccount(TEST_ACCOUNT_1, "0.00", 0, 0);
        assertFalse(account1.equals(account2));
        assertFalse(account2.equals(account1));

        account1 = buildAccount(TEST_ACCOUNT_1, "0.00", 0, 1);
        account2 = buildAccount(TEST_ACCOUNT_1, "0.00", 0, 0);
        assertFalse(account1.equals(account2));
        assertFalse(account2.equals(account1));
}

    @Test
    public void testHashCode() {
        Account account1 = buildAccount(TEST_ACCOUNT_1, "20.01", 1, 2);
        Account account2 = buildAccount(TEST_ACCOUNT_1, "20.01", 1, 2);
        Account account3 = buildAccount(TEST_ACCOUNT_1, "20.11", 1, 2);
        assertEquals(account1.hashCode(), account2.hashCode());
        assertNotEquals(account1.hashCode(), account3.hashCode());
    }
    
}
