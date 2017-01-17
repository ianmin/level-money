# level-money

The project is built using Spring Boot. It implements all features as mentioned.

Installation and Getting Started
----------------
The project is built using Maven wrapper and make sure you have JDK 1.8 installed.

Clone the github repository:

    git clone https://github.com/ianmin/level-money.git
    d level-money/
    ./mvnw clean install
    cd client
    ./mvnw spring-boot:run
    
Retrieve results
----------------
You could perform the following request either in web browser or using curl

 - Loads a user's transactions from the GetAllTransactions endpoint
 
        curl http://localhost:8081/api/transactions/all

 - Determines how much money the user spends and makes in each of the months and in the "average" month:

   ![first equation](http://latex.codecogs.com/gif.latex?Average%20%3D%20%5Cfrac%7B%5Csum%20%28monthly%20%5C%3A%20spend%20%5C%3A%20or%20%5C%3A%20monthly%20%5C%3A%20income%29%7D%7Btotoal%20%5C%3A%20number%20%5C%3A%20of%20%5C%3A%20months%20%5C%3A%20of%20%5C%3A%20all%20%5C%3A%20transactions%7D)
    
        curl http://localhost:8081/api/transactions/spendIncome

 - ignore-donuts:

    With the below request, the analysis of monthly spend and income will ignore DONUT spending transactions.

    The project is using merchant filed named “Krispy Kreme Donuts” or “DUNKIN #336784” to ignore donut transactions. 
    
        curl http://localhost:8081/api/transactions/spendIncome/withoutDonut
    
 - crystal-ball: 
 
    Retrieve all projected transactions of current month using GetProjectedTransactionsForMonth endpoint,
    and merge pending transactions with all transactions retrieving from the GetAllTransactions endpoint 
    to predict current monthly spend and income in addition to previous months.
    
        curl 'http://localhost:8081/api/projectedTransactions/spendAndIncome?year=2017&month=1'
    
 - ignore-cc-payments:

    With the below request, the analysis of monthly spend and income will ignore Credit Card Payments transactions.
    The project is using the merchant filed to filter credit card payments on both credit card account and asset account.
    The merchant filed showing as "CC Payment" for an asset account or as "Credit Card Payment" for a credit card 
    account will be targeted as credit card payments.

    Retrieve the spend and income with out credit card payments:

        curl http://localhost:8081/api/transactions/spendIncome/withoutCreditCardPayment
    
    Retrieve the list of credit card payments showing on both credit card account and asset account:    
    
        curl http://localhost:8081/api/transactions/getCreditCardPayments
    