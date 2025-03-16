/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import business.Expense;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author KJ
 */
public class CSV {

    // Send Linked hash map of expenses and filePath in form of 'path.csv' 
    public static void export(LinkedHashMap<Integer, Expense> map, String filePath) throws IOException {

        String pattern = "MM-dd-yyyy";
        //Sets up pattern to format date to string
        DateTimeFormatter date = DateTimeFormatter.ofPattern(pattern);

        try (PrintWriter out = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(filePath, false)))) {
            //prints header for csv file
            String header = String.format("%s,%s,%s,%s,%s,%s,%s",
                    "ExpenseID", "Name", "Amount", "Date", "Frequency", "CategoryID", "AccountID");
            out.println(header);
                   
            
            //cycles through HashMap
            for (Map.Entry<Integer, Expense> entry : map.entrySet()) {

                //grab entry from hash map
                Expense expense = entry.getValue();

                //need to format date to string before sending to csv
                String formattedDate = expense.getTransactionDate().format(date);

                // format examples %d = int, %s = string, %.2f = double
                String row = String.format("%d, %s, %.2f, %s, %d, %d, %d", expense.getExpenseID(), expense.getExpenseName(),
                        expense.getAmount(), formattedDate, expense.getFrequency(), expense.getFkCategoryID(), expense.getFkAccountID());

                out.println(row);

            }
            
        } catch (IOException e) {
            System.out.println(e);
        }
        File file = new File(filePath);
        System.out.println("File saved at: " + file.getAbsolutePath());
    }
}
