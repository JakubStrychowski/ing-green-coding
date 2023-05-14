package pl.ing.green.transactions;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.ing.green.transactions.api.TransactionsApi;
import pl.ing.green.transactions.model.Account;
import pl.ing.green.transactions.model.Transaction;

/**
 * Service calculating balances of accounts for a set of transactions. This
 * algorithm has linear complexity depending on number of transactions.
 *
 * @author Jakub Strychowski
 */
@RestController
public class TransactionsService implements TransactionsApi {

    /**
     * Transfer money between accounts and reports final balance for each account.
     * 
     * @param transactions
     * 
     * @return Report about account balances and account operations.
     */
    @Override
    public ResponseEntity<List<Account>> report(List<Transaction> transactions) {

        BalanceCalculator calculator = new BalanceCalculator();
        List<Account> result = calculator.calucaleBalances(transactions);

        ResponseEntity<List<Account>> response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

}
