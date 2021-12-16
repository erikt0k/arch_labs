package ru.sfedu.arch.API;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import junit.framework.TestCase;
import org.junit.Test;
import ru.sfedu.arch.Emp;

import java.io.IOException;

public class DataProviderDBTestPositive extends TestCase {
    Emp emp = new Emp();
    DataProviderDB dataProviderDB = new DataProviderDB();
    @Test
    public void testSaveEmpRecord() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        emp.setId();
        emp.setName("Test");
        emp.setAge(10);
        dataProviderDB.saveEmpRecord(emp);

    }

    public void testDeleteEmpRecord() {
    }

    public void testSaveProductRecord() {
    }

    public void testDeleteProductRecord() {
    }

    public void testGetEmpById() {
    }

    public void testGetProductById() {
    }

    public void testInitDataSource() {
    }

    public void testUpdateEmpRecord() {
    }

    public void testUpdateProductRecord() {
    }
}