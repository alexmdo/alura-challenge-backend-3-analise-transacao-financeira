package br.com.alexmdo.txanalyser;

import br.com.alexmdo.txanalyser.model.Bank;
import br.com.alexmdo.txanalyser.model.Transaction;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    private final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            .withZone(ZoneId.of("UTC"));

    public List<Transaction> readFromFile(InputStream inputStream) throws IOException {
        CSVParser csvParser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();

        CSVReaderBuilder csvReaderBuilder = new CSVReaderBuilder(new InputStreamReader(inputStream))
                .withSkipLines(0)
                .withCSVParser(csvParser);
        try (CSVReader csvReader = csvReaderBuilder.build()) {
            List<Transaction> transactions = new ArrayList<>();

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                transactions.add(new Transaction(new Bank(line[0], line[1], line[2]), new Bank(line[3], line[4], line[5]), new BigDecimal(line[6]).setScale(2, RoundingMode.HALF_UP), LocalDateTime.parse(line[7], formatter)));
            }

            return transactions;
        }
    }
}
