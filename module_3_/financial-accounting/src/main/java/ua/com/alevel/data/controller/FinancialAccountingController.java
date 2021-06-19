package ua.com.alevel.data.controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.data.command.FinancialAccountingCommand;
import ua.com.alevel.data.exception.FinancialAccountingExceptionDataNotFound;
import ua.com.alevel.data.model.dto.OperationRecord;
import ua.com.alevel.data.model.dto.OperationSaveExpense;
import ua.com.alevel.data.model.dto.OperationSaveIncome;
import ua.com.alevel.data.model.dto.QuittanceRecord;
import ua.com.alevel.data.model.entity.ExpenseCategory;
import ua.com.alevel.data.model.entity.IncomeCategory;
import ua.com.alevel.data.service.ClientService;
import ua.com.alevel.data.service.CreatingOperationService;
import ua.com.alevel.data.service.QuittanceService;
import ua.com.alevel.data.service.jdbc.JDBCFinancialService;
import ua.com.alevel.data.service.jpa.JPAClientService;
import ua.com.alevel.data.service.jpa.JPACreateOperation;
import ua.com.alevel.data.util.CSVParser;
import ua.com.alevel.data.view.FinancialAccountingView;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class FinancialAccountingController {

    private static final Logger logger = LoggerFactory.getLogger(FinancialAccountingController.class);

    public void run(String name, String password) {
        Configuration configuration = new Configuration().configure()
                .setProperty(Environment.USER, name)
                .setProperty(Environment.PASS, password);

        FinancialAccountingView view = new FinancialAccountingView();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            EntityManager em = sessionFactory.createEntityManager();
            Supplier<EntityManager> supplier = () -> em;

            CreatingOperationService operationService = new JPACreateOperation(supplier);
            ClientService clientService = new JPAClientService(supplier);

            Long userId = getUserId(view, clientService);

            FinancialAccountingCommand command = new FinancialAccountingCommand(
                    view,
                    operationService,
                    clientService,
                    userId
            );

            while (true) {

                int optionMenu = view.chooseOption();
                switch (optionMenu) {
                    case 0 -> System.exit(0);
                    case 1 -> {
                        command.createOperation();
                        session.close();
                    }
                    case 2 -> {
                        command.makeQuittance(name, password);
                    }
                }

            }

        }  catch (Exception e) {
            logger.error(e.toString());
            System.out.println("Problem occurs during program working. Please, try again");
        }
    }

    private Long getUserId(FinancialAccountingView view, ClientService clientService) {
        Long userId = (long) view.getUserId();
        if (clientService.findById(userId).isPresent())
            return userId;
        else {
            logger.warn("There is no client with id {}", userId);
            return getUserId(view, clientService);
        }
    }


}
