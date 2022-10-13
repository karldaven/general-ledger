package karldaven.demo.ledger.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "transaction")
public class TransactionEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "ledger_id")
  private Integer ledgerId;

  @Column(name = "transaction_datetime")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private Timestamp transactionDateTime;

  @Column(name = "is_credit")
  private Boolean isCredit;

  @Column(name = "amount")
  private BigDecimal amount;

  @Column(name = "balance")
  private BigDecimal balance;

  @Column(name = "description")
  private String description;
}
