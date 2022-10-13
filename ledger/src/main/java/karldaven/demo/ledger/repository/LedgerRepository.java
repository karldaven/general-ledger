package karldaven.demo.ledger.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import karldaven.demo.ledger.entity.LedgerEntity;
import karldaven.demo.ledger.entity.projection.LedgerView;

public interface LedgerRepository extends JpaRepository<LedgerEntity, Integer> {
  
  @Query(value = "select " +
                    "l.name, " +
                    "l.currency, " +
                    "l.description " +
                 "from ledger l " +
                 "where l.id = :ledgerId", nativeQuery = true)
  Optional<LedgerView> findByLedgerId(Integer ledgerId);
}
