package com.yellowpepper.transferservice.daos;

import com.yellowpepper.transferservice.dtos.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Integer> {
}
