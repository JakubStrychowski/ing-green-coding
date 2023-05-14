package pl.ing.green.transactions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pl.ing.green.transactions.model.Account;
import pl.ing.green.transactions.model.Transaction;

/**
 * Calculates balances of accounts for a set of transactions. This algorithm has
 * linear complexity depending on number of transactions.
 *
 * @author Jakub Strychowski
 */
public class BalanceCalculator {

    public BalanceCalculator() {

    }

    /**
     * Transfer money between balances. Accounts are stored in a map and can be
     * quickly accessed for calculation.
     *
     * @param transactions
     * @return
     */
    protected List<Account> calucaleBalances(List<Transaction> transactions) {

        // calculate initial capacity of HashMap to prevent rehashing
        // number of accounts cannot be greater then 2 * number of transactions
        float loadFactor = 0.75f;
        int initCapacity = Math.round((float) 2.0f * transactions.size() / loadFactor);

        Map<String, Account> accounts = new HashMap<>(initCapacity);

        // Process all transactions in seqence.
        // This can be realize also in parallel (using more cores), but
        // this maybe not faster if cores are loaded by other requests.
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

}
