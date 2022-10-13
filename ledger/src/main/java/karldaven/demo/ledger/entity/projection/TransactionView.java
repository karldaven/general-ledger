package karldaven.demo.ledger.entity.projection;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface TransactionView {
  Integer getId();

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  Timestamp getTransactionDateTime();

  @Value("#{target.isCredit == 1}")
  Boolean getIsCredit();

  BigDecimal getAmount();

  BigDecimal getBalance();
  
  String getDescription();
}
