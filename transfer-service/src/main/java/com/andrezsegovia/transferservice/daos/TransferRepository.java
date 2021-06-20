package com.andrezsegovia.transferservice.daos;

import com.andrezsegovia.transferservice.dtos.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Integer> {

    @Query("select count(t) from Transfer t where t.originAccount = ?1 and t.createDate between ?2 and ?3")
    Integer totalTransfersByCreationDateRange(String originAccount, Date start, Date end);
}
