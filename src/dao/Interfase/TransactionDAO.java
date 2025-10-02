package dao.Interfase;

import entity.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionDAO {
    boolean ajouter(Transaction transaction);

    List<Transaction> trouverToutes();
    public List<Transaction>  trouverParDate(LocalDateTime startDate, LocalDateTime endDate);


}