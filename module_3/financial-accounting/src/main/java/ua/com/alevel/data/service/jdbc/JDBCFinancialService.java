package ua.com.alevel.data.service.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.data.model.dto.OperationRecord;
import ua.com.alevel.data.model.dto.QuittanceRecord;
import ua.com.alevel.data.service.QuittanceService;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class JDBCFinancialService implements QuittanceService {

    private static final Logger logger = LoggerFactory.getLogger(JDBCFinancialService.class);

    private Connection connection;

    public JDBCFinancialService(String url, String username, String password) {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            logger.error("connection failed");
            throw new RuntimeException("Problem occurs during connection to database");
        }
    }

    @Override
    public List<OperationRecord> getOperationsByTimePeriod(Long accountId, Instant timeFrom, Instant timeTo) {
        try (PreparedStatement getOperations = connection.prepareStatement("SELECT * " +
                " FROM operations " +
                " Where account_id=? and date_creation> ? and date_creation<?")) {

            getOperations.setLong(1, accountId);
            getOperations.setTimestamp(2, Timestamp.from(timeFrom));
            getOperations.setTimestamp(3, Timestamp.from(timeTo));

            ResultSet resultSet = getOperations.executeQuery();

            List<OperationRecord> operations = new ArrayList<>();
            while (resultSet.next()) {
                operations.add(
                        new OperationRecord(
                                resultSet.getLong(1),
                                resultSet.getLong(5),
                                resultSet.getLong(2),
                                resultSet.getLong(3),
                                resultSet.getTimestamp(6).toInstant(),
                                resultSet.getString(7)));
            }
            return operations;

        } catch (SQLException throwables) {
            logger.error("Error occurs during working with sql");
            throw new RuntimeException(throwables);
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                logger.error("Problem occurs during closing SQL connection");
            }
        }
    }

    @Override
    public Long generalIncome(List<OperationRecord> operations) {
        Long income = 0L;

        if (operations.isEmpty()) {
            return 0L;
        }
        for (OperationRecord operation : operations) {
            if (operation.type().equals("income"))
                income += operation.transactionAmount();
        }

        return income;
    }

    @Override
    public Long balanceInHand(List<OperationRecord> operations) {
        if (operations.isEmpty()) {
            return 0L;
        }
        return generalIncome(operations) + generalExpense(operations);
    }

    @Override
    public QuittanceRecord generateQuittance(List<OperationRecord> operations, Long income, Long balanceInHand) {

        return new QuittanceRecord(
                operations,
                income,
                balanceInHand
        );
    }

    private Long generalExpense(List<OperationRecord> operations) {
        Long outcome = 0L;

        for (OperationRecord operation : operations) {
            if (operation.type().equals("expense"))
                outcome += operation.transactionAmount();
        }

        return outcome;
    }


}
