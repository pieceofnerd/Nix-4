package ua.com.alevel.data.service.jpa;

import ua.com.alevel.data.model.dto.AccountRecord;
import ua.com.alevel.data.model.dto.ClientRecord;
import ua.com.alevel.data.model.entity.Account;
import ua.com.alevel.data.model.entity.Client;
import ua.com.alevel.data.service.ClientService;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class JPAClientService  implements ClientService {


    private final Supplier<EntityManager> em;

    public JPAClientService(Supplier<EntityManager> em) {
        this.em = em;
    }

    @Override
    public Optional<ClientRecord> findById(Long id) {
        EntityManager jpa = em.get();

        Client entity = jpa.find(Client.class, id);

        return Optional.ofNullable(entity)
                .map(this::mapEntityToRecord);
    }

    @Override
    public List<AccountRecord> findAccountsByUserId(Long id) {
        EntityManager jpa = em.get();

        Client entity = jpa.find(Client.class, id);
        if(Objects.nonNull(entity))
            return entity.getAccounts().stream()
                    .map(this::mapEntityToRecord)
                    .collect(Collectors.toList());
        else return null;
    }

    private ClientRecord mapEntityToRecord(Client entity) {
        return new ClientRecord(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getAge(),
                entity.getEmail()
        );
    }

    private AccountRecord mapEntityToRecord(Account entity) {
        return new AccountRecord(
                entity.getId(),
                entity.getBalance(),
                entity.getClient()
        );
    }


}
