package ua.com.alevel.data.service;

import ua.com.alevel.data.model.dto.AccountRecord;

import java.util.Optional;

public interface AccountService {

    Optional<AccountRecord> findAccountById(Long id);


}
