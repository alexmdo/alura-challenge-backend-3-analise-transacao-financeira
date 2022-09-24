package br.com.alexmdo.txanalyser.service;

import br.com.alexmdo.txanalyser.model.TransactionImported;
import br.com.alexmdo.txanalyser.model.User;
import br.com.alexmdo.txanalyser.repository.TransactionImportedRepository;
import br.com.alexmdo.txanalyser.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction;
import static org.springframework.data.domain.Sort.by;

@Service
public class TransactionImportedService {

    private final TransactionImportedRepository transactionImportedRepository;
    private final UserRepository userRepository;

    public TransactionImportedService(TransactionImportedRepository transactionImportedRepository, UserRepository userRepository) {
        this.transactionImportedRepository = transactionImportedRepository;
        this.userRepository = userRepository;
    }


    public List<TransactionImported> findAllAndSortByTransactionDate() {
        return transactionImportedRepository.findAll(by(Direction.DESC, "transactionDate"));
    }

    @Transactional
    public void save(TransactionImported entity) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        entity.setUser(user);
        transactionImportedRepository.save(entity);
    }

    public Optional<TransactionImported> findById(Long id) {
        return transactionImportedRepository.findById(id);
    }
}
