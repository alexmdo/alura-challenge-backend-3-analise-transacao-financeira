package br.com.alexmdo.txanalyser.service;

import br.com.alexmdo.txanalyser.model.TransactionImported;
import br.com.alexmdo.txanalyser.repository.TransactionImportedRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction;
import static org.springframework.data.domain.Sort.by;

@Service
public class TransactionImportedService {

    private final TransactionImportedRepository transactionImportedRepository;

    public TransactionImportedService(TransactionImportedRepository transactionImportedRepository) {
        this.transactionImportedRepository = transactionImportedRepository;
    }


    public List<TransactionImported> findAllAndSortByTransactionDate() {
        return transactionImportedRepository.findAll(by(Direction.DESC, "transactionDate"));
    }

    public TransactionImported save(TransactionImported entity) {
        return transactionImportedRepository.save(entity);
    }
}
