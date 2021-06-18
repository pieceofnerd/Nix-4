package ua.com.alevel.data.model.entity;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import ua.com.alevel.data.annotation.Category;

@Entity
@Table(name = "operations")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance_before", nullable = false)
    private Long balanceBefore;

    @Column(name = "balance_after", nullable = false)
    private Long balanceAfter;

    @Column(name = "transaction_amount", nullable = false)
    private Long transactionAmount;

    @Column(name = "date_creation")
    private Instant dateCreation;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "type")
    private String type;

    @ManyToMany(mappedBy = "operations")
    Set<IncomeCategory> incomeCategorySet = new HashSet<>();

    @ManyToMany(mappedBy = "operations")
    Set<ExpenseCategory> expenseCategories = new HashSet<>();

    public Operation() {
        this.dateCreation = Instant.now();
    }

    public Operation(Long balanceBefore, Long transactionAmount, Account account) {
        this.balanceBefore = balanceBefore;
        this.transactionAmount = transactionAmount;
        this.account = account;
        this.dateCreation = Instant.now();
    }

    public void setBalanceAfter(Long balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public void setType(Object type) {
        Class<?> classOfCategory = type.getClass();

        if (classOfCategory.isAnnotationPresent(Category.class)) {
            if (classOfCategory.getName().equals("ua.com.alevel.data.model.entity.IncomeCategory")) {
                this.incomeCategorySet.add((IncomeCategory) type);
                this.type = "income";
            } else {
                this.expenseCategories.add((ExpenseCategory) type);
                this.type = "expense";
            }
        } else {
            throw new RuntimeException("Incorrect type");
        }
    }


}
