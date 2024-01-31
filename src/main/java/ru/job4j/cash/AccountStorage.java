package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.put(account.id(), account) != null;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        Optional<Account> firstAccount = getById(fromId);
        Optional<Account> secondAccount = getById(toId);
        if (firstAccount.isPresent() && secondAccount.isPresent()
                && firstAccount.get().amount() - amount >= 0) {
            update(new Account(fromId, firstAccount.get().amount() - amount));
            update(new Account(toId, secondAccount.get().amount() + amount));
        }
        return result;
    }
}
