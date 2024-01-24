package com.axis.task.repository;

import com.axis.task.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
     void deleteByAccountId(String accountId);
     AccountEntity findByAccountId(String accountId);
}
