package ua.com.alevel.data.util;

import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.data.model.dto.OperationRecord;
import ua.com.alevel.data.model.dto.QuittanceRecord;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class CSVParser {

    private static final Logger logger = LoggerFactory.getLogger(CSVParser.class);

    private static final String path = "financial-accounting\\src\\main\\resources";

    private static int quittanceId = 1;

    public static void createQuittance(QuittanceRecord quittanceRecord) {
        String filename = path  + "\\quittance" + quittanceId + ".csv";

        File file = new File(filename);
        try {
            if (file.createNewFile())
                logger.info("Quittance file was created");

        } catch (IOException e) {
            logger.error("Problem occurs during working with the file");
            throw new RuntimeException(e);
        }
        String[] title = {"Balance before", "Transaction amount", "Balance after", "Date", "Type"};
        List<String[]> data = new ArrayList<>();
        data.add(title);

        if (!quittanceRecord.operationList().isEmpty()) {


            for (OperationRecord operation : quittanceRecord.operationList()) {
                String[] row = {
                        operation.balanceBefore().toString(),
                        operation.transactionAmount().toString(),
                        operation.balanceAfter().toString(),
                        operation.date().toString(),
                        operation.type()
                };
                data.add(row);
            }
        }

        String[] income = {"\nIncome", quittanceRecord.income().toString()};
        String[] balanceInHand = {"balance in hand", quittanceRecord.balanceInHand().toString()};

        data.add(income);
        data.add(balanceInHand);

        try (CSVWriter writer = new CSVWriter(new FileWriter(filename))) {
            writer.writeAll(data);
            quittanceId++;
        } catch (IOException e) {
            logger.error("Problem occurs during parsing csv");
            throw new RuntimeException();
        }

    }


}
