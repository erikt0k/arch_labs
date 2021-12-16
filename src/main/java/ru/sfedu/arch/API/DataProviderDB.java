package ru.sfedu.arch.API;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.arch.Constants;
import ru.sfedu.arch.Emp;
import ru.sfedu.arch.Product;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DataProviderDB implements IDataProvider{
    private static final Logger log = LogManager.getLogger(DataProviderDB.class);
    private static Connection conn;

    @Override
    public boolean saveEmpRecord(Emp emp) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try{
            conn = DriverManager.getConnection("jdbc:h2:~/test", "user", "123");
            Statement statement = conn.createStatement();
            // Для Insert, Update, Delete
            int result = statement.executeUpdate("INSERT INTO emp VALUES ("+emp.getId()+","+emp.getName()+","+emp.getAge()+")");
            log.info(result);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteEmpRecord(long id) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        return false;
    }

    @Override
    public boolean saveProductRecord(Product prod) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try{
            conn = DriverManager.getConnection("jdbc:h2:~/test", "user", "123");
            Statement statement = conn.createStatement();
            // Для Insert, Update, Delete
            String query = String.format(Constants.INSERT_INTO_PROD, prod.getId(),prod.getName(),prod.getPrice());
            System.out.println("Trying to execute "+ query);
            int result = statement.executeUpdate(query);
            log.trace(result);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteProductRecord(long id) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        return false;
    }

    @Override
    public Emp getEmpById(long id) throws IOException {
        return null;
    }

    @Override
    public Product getProductById(long id) throws IOException {
        return null;
    }

    @Override
    public boolean initDataSource() {
        return false;
    }

    @Override
    public boolean updateEmpRecord(long id, Emp emp) {
        return false;
    }

    @Override
    public boolean updateProductRecord(long id, Product prod) {
        return false;
    }
}
