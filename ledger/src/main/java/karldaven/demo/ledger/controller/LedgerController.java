package karldaven.demo.ledger.controller;

import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import karldaven.demo.ledger.entity.LedgerEntity;
import karldaven.demo.ledger.entity.TransactionEntity;
import karldaven.demo.ledger.entity.projection.LedgerView;
import karldaven.demo.ledger.entity.projection.TransactionView;
import karldaven.demo.ledger.repository.LedgerRepository;
import karldaven.demo.ledger.repository.TransactionRepository;

@CrossOrigin
@Controller
@RequestMapping(path="/api/ledger")
public class LedgerController {
  Logger log = LoggerFactory.logger(LedgerController.class);

  @Autowired
  private LedgerRepository ledgerRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @GetMapping
  public @ResponseBody ResponseEntity<Object> getLedgers() {
    List<LedgerEntity> ledgerResults = ledgerRepository.findAll();
    return ResponseEntity.ok().body(ledgerResults);
  }

  @PostMapping
  public ResponseEntity<Object> createLedger(@RequestBody LedgerEntity ledgerEntity) {
    LedgerEntity createdLedger;

    try{
      createdLedger = ledgerRepository.save(ledgerEntity);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    } catch (OptimisticLockingFailureException e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
    
    log.info("Successfully added ledger. id = " + createdLedger.getId());
    return ResponseEntity.ok().body(createdLedger);
  }

  @GetMapping(path="/{ledgerId}/transactions")
  public @ResponseBody ResponseEntity<Object> getTransactions(@PathVariable("ledgerId") Integer ledgerId) {
    Iterable<TransactionView> transactionResults;

    try {
      Optional<LedgerView> ledgerResult = ledgerRepository.findByLedgerId(ledgerId);

      if (ledgerResult.isEmpty()) {
        log.error("Ledger not found.");
        return ResponseEntity.notFound().build();
      }

      transactionResults = transactionRepository.findByLedgerId(ledgerId);
    } catch (Exception e) {
      log.error(e.getMessage());
      return ResponseEntity.internalServerError().build();
    }

    return ResponseEntity.ok().body(transactionResults);
  }

  @PostMapping(path="/{ledgerId}/transactions")
  public ResponseEntity<Object> createTransaction(@RequestBody TransactionEntity transactionEntity) {
    TransactionEntity createdTransaction;

    try{
      Optional<LedgerView> ledgerResult = ledgerRepository.findByLedgerId(transactionEntity.getLedgerId());

      if (ledgerResult.isEmpty()) {
        log.error("Ledger not found.");
        return ResponseEntity.notFound().build();
      }

      createdTransaction = transactionRepository.save(transactionEntity);
    } catch (IllegalArgumentException e) {
      log.error(e.getMessage());
      return ResponseEntity.internalServerError().build();
    } catch (OptimisticLockingFailureException e) {
      log.error(e.getMessage());
      return ResponseEntity.internalServerError().build();
    }
    
    log.info("Successfully added transaction. id = " + createdTransaction.getId());
    return ResponseEntity.ok().body(createdTransaction.getId());
  }
}
