package com.yellowpepper.accountservice.daos;

import com.yellowpepper.accountservice.dtos.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findAccountByAccount(String account);

    @Modifying
    @Query("update Account a set a.accountBalance = ?2 where a.account = ?1")
    void updateAccountBalanceByAccount(String account, Float balance);
}
