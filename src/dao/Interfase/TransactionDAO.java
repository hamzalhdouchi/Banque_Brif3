package dao.Interfase;

import java.util.List;

public interface TransactionDAO {
    boolean ajouter(entity.Transaction transaction);

    List<entity.Transaction> trouverToutes();
}
