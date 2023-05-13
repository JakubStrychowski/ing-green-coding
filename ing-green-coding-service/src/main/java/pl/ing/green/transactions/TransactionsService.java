package pl.ing.green.transactions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.ing.green.transactions.api.TransactionsApi;
import pl.ing.green.transactions.model.Account;
import pl.ing.green.transactions.model.Transaction;

/**
 *
 * @author Jakub Strychowski
 */
@RestController
public class TransactionsService implements TransactionsApi {

    protected List<Account> calucaleBalances(List<Transaction> transactions) {
        
        // calculate initial capacity of HashMap to prevent rehashing
        // number of accounts cannot be greater then 2 * number of transactions
        float loadFactor = 0.75f;
        int initCapacity = Math.round((float) 2.0f * transactions.size() / loadFactor);
        
        Map<String, Account> accounts = new HashMap<>(initCapacity);
        
        // Process all transactions in seqence.
        // Paraller processing can be faster for big number of transactions
        // but only if service is not loaded by other, paraller requests.
        for (Transaction transaction : transactions) {
            String debitAccountNumber = transaction.getDebitAccount();
            Account debitAccount = accounts.get(debitAccountNumber);
            if (debitAccount == null) {
                debitAccount = new Account(debitAccountNumber);
                accounts.put(debitAccountNumber, debitAccount);
            }
            
            String creditAccountNumber = transaction.getCreditAccount();
            Account creditAccount = accounts.get(creditAccountNumber);
            if (creditAccount == null) {
                creditAccount = new Account(creditAccountNumber);
                accounts.put(creditAccountNumber, creditAccount);
            }
            
            // In real case account creation and following 
            // section should be globally synchronized 
            // but in this task we only make some 
            // calculations for reporting purpose using separate context
            // for each request.
            debitAccount.debit(transaction.getAmount());
            creditAccount.credit(transaction.getAmount());
            
        }
        
        
        ArrayList<Account> result = new ArrayList(accounts.values());
        result.sort((Account o1, Account o2) -> o1.getAccount().compareTo(o2.getAccount()));
        
        return result;
        
    }
    
    
    @Override
    public ResponseEntity<List<Account>> report(List<Transaction> transactions) {
        
        List<Account> result = calucaleBalances(transactions);
        
        ResponseEntity<List<Account>> response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;        
    }
    
    
}
