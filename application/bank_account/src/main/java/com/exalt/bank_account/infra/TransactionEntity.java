package com.exalt.bank_account.infra;

import com.exalt.bank_account.domain.model.TransactionTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_seq_generator")
    @SequenceGenerator(name = "transaction_id_seq_generator", sequenceName = "bank.transaction_id_seq",  allocationSize = 1)
    @Column(name = "id")
    private Integer id;
    @Column(name = "details")
    private String details;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TransactionTypeEnum type;
    @ManyToOne
    @JoinColumn(name = "id_bank_account")
    private BankAccountEntity bankAccountEntity;

    public TransactionEntity(String details, TransactionTypeEnum type, BankAccountEntity bankAccountEntity) {
        this.details = details;
        this.type = type;
        this.bankAccountEntity = bankAccountEntity;
    }
}
