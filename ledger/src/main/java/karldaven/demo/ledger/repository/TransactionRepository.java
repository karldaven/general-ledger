package karldaven.demo.ledger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import karldaven.demo.ledger.entity.TransactionEntity;
import karldaven.demo.ledger.entity.projection.TransactionView;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {

  @Query(value = "select " +
                    "t.id, " +
                    "t.transaction_datetime as transactionDateTime, " +
                    "t.is_credit as isCredit, " +
                    "t.amount, " +
                    "t.balance, " +
                    "t.description " +
                 "from transaction t " +
                 "where t.ledger_id = :ledgerId " +
                 "order by transactionDateTime desc", nativeQuery = true)
  List<TransactionView> findByLedgerId(Integer ledgerId);
}
