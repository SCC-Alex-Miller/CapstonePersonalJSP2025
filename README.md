FINAL BLUEPRINT

Users
-userId: int
-firstName: String
-lastName: String
-email: String
-password: String
-List<Account>: accounts
-createdDate: LocalDateTime
+getters & Setters
+login()
+logout()
+registerUser()
+registerAccount()
+getAccounts()

Account
-accountId int
-accountName: String
-User: userId
-createdDate: LocalDateTime
+getters & setters
+getFinancialSummary()
+getBudget()
+addCategory()
+editCategory()
+deleteCategory()
+addExpense()
+editExpense()
+deleteExpense()


Expense -superClass
-expenseId: int
-Category: categoryId
-amount: double
-transactionDate: LocalDate
+getters & setters

Recurring -childClass
-frequency: int
+getter & setter

Category - superClass
-categoryId: int
-categoryName: String
+getter & setter


CustomCategory - childClass
-Account: accountId
+getter & setter


