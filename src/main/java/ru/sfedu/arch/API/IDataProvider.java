package ru.sfedu.arch.API;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.arch.Emp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IDataProvider {
    boolean saveRecord(Emp emp) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;
    boolean deleteRecord(long id) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;
    Emp getRecordById(long id) throws IOException;
    boolean initDataSource();
    <T> List<T> loadBeanList() throws IOException;
}
