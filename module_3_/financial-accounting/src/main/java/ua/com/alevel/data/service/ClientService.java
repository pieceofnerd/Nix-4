package ua.com.alevel.data.service;

import ua.com.alevel.data.model.dto.AccountRecord;
import ua.com.alevel.data.model.dto.ClientRecord;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Optional<ClientRecord> findById(Long id);

    List<AccountRecord> findAccountsByUserId(Long id);
}
