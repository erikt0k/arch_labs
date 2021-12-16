package ru.sfedu.arch.API;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.arch.Emp;
import ru.sfedu.arch.Product;

import java.io.IOException;

public interface IDataProvider {
    boolean saveEmpRecord(Emp emp) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;
    boolean deleteEmpRecord(long id) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;
    boolean saveProductRecord(Product prod) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;
    boolean deleteProductRecord(long id) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;
    Emp getEmpById(long id) throws IOException;
    Product getProductById(long id) throws IOException;
    boolean initDataSource();
    boolean updateEmpRecord(long id, Emp emp);
    boolean updateProductRecord(long id, Product prod);
    //<T> List<T> loadBeanList(String path, T beanClass) throws IOException;
}
