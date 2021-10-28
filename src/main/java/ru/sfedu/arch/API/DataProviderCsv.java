package ru.sfedu.arch.API;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.arch.Client;
import ru.sfedu.arch.Constants;
import ru.sfedu.arch.Emp;
import ru.sfedu.arch.utils.ConfigurationUtil;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class DataProviderCsv implements IDataProvider {
    private static final Logger log = LogManager.getLogger(Client.class);
    List<Emp> beans = null;

    /**
     * Creates a line in <code>CSV_DS_PATH</code> generated from entered Emp.class object
     */
    @Override
    public boolean saveRecord(Emp emp) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        beans = loadBeanList();
        log.info(beans);
        beans.add(emp);
        return writeCSV(beans);
    }

    /**
     * Deletes a line with entered id from <code>CSV_DS_PATH</code>
     */
    @Override
    public boolean deleteRecord(long id) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        beans = loadBeanList();
        log.info(beans);
        Predicate<Emp> isDeletable = emp -> emp.getId() == id;
        beans.removeIf(isDeletable);
        return writeCSV(beans);
    }

    public boolean updateRecord(long id, int newAge, String newName) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        beans = loadBeanList();
        beans.get(beans.indexOf(getRecordById(id))).setAge(newAge);
        beans.get(beans.indexOf(getRecordById(id))).setName(newName);
        return writeCSV(beans);
    }
    public boolean updateRecord(long id, int newAge) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        beans = loadBeanList();
        beans.get(beans.indexOf(getRecordById(id))).setAge(newAge);
        return writeCSV(beans);
    }
    public boolean updateRecord(long id, String newName) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        beans = loadBeanList();
        beans.get(beans.indexOf(getRecordById(id))).setName(newName);
        return writeCSV(beans);
    }

    /**
     * Returns bean loaded from <code>CSV_DS_PATH</code> by id
     * @return Emp object with id as in parameter
     */
    @Override
    public Emp getRecordById(long id) throws IOException {
        return (Emp)loadBeanList().stream()
                    .filter(bean-> ((Emp) bean).getId() == id)
                    .findAny().get();
    }

    @Override
    public boolean initDataSource() {
        return false;
    }

    /**
     * Loads List of Emp.class from CSV file placed in <code>CSV_DS_PATH</code>
     * todo: сделать входным параметр с типом, в зависимости от него менять путь до csv для выгрузки
     */
    public <T> List<T> loadBeanList() throws IOException {
        List<T> beans = new CsvToBeanBuilder(
                new FileReader(ConfigurationUtil
                        .getConfigurationEntry(Constants.EMP_CSV_DS_PATH)))
                .withType(Emp.class)
                .build()
                .parse();

        return beans;
    }

    /**
     * Writes CSV to <code>CSV_DS_PATH</code>
     */
    private <T> boolean writeCSV(List<T> beans) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        FileWriter sw = new FileWriter(ConfigurationUtil
                    .getConfigurationEntry(getPathByList(beans)));
        log.info("Will write to:" + ConfigurationUtil.getConfigurationEntry(getPathByList(beans)));
        CSVWriter writer = new CSVWriter(sw);
        StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer).build();
        log.info("----WRITING----");
        log.info(beans);
        beanToCsv.write(beans);
        writer.close();
        return true;
    }
    /**
     * Returns a constant with path based on type of bean
     * Just a prototype (может вообще не понадобится)
     */
    public <T> String getPathByBean(T bean){
        log.info(bean.getClass());
        return Constants.EMP_CSV_DS_PATH;
    }
    /**
     * Returns a constant with path based on type of elements in list
     */
    public <T> String getPathByList(List<T> bean){
        log.info("Class of elements inside list: "+bean.get(0).getClass().getSimpleName());
        switch(bean.get(0).getClass().getSimpleName()) {
            case "Emp":
                return Constants.EMP_CSV_DS_PATH;
            case "Product":
                return Constants.PROD_CSV_DS_PATH;
            //ниже временная штука, надо поправить (по сути всегда будет либо emp либо product)
            // просто хочется сделать синтаксис с кейсом на перспективу, мб можно и if-ами
            default: return Constants.OWNER_FIRST_NAME;
        }
    }
}
