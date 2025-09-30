package dao.interfase;

import java.util.List;

public interface Transaction {
    boolean ajouter(entity.Transaction transaction);

    List<entity.Transaction> trouverToutes();
}
