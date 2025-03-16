package business;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author kj418824
 */
public class Category implements Serializable {

    private int categoryID;
    private String categoryName;
    private LocalDate createdDate;
    private double budgetAmount;
    private int accountID;

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public Category() {
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public Category(int categoryID, String categoryName, LocalDate createdDate, double budgetAmount) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.budgetAmount = budgetAmount;
        this.createdDate = createdDate;
    }
    
    public Category(int categoryID, String categoryName, LocalDate createdDate, double budgetAmount, int accountID) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.budgetAmount = budgetAmount;
        this.createdDate = createdDate;
        this.accountID = accountID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
