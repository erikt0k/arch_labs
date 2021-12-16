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
import ru.sfedu.arch.Product;
import ru.sfedu.arch.utils.ConfigurationUtil;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class DataProviderCsv implements IDataProvider {
    private static final Logger log = LogManager.getLogger(Client.class);
    public List<Emp> beansEmp = null;
    public List<Product> beansProduct = null;
    Emp empBean = new Emp();
    Product productBean = new Product();

    public List<Emp> getAllEmp() {
        List<Emp> empBeans = null;
        Emp empBean = new Emp();

        try {
            empBeans= loadBeanList(Constants.EMP_CSV_DS_PATH, empBean);
        }
        catch(Exception e){
            log.error(e.getClass().getName() + ": " + e.getMessage());
            return empBeans;
        }
        return empBeans;
    }


    public List<Product> getAllProduct() {
        List<Product> requestBeans = null;
        Product requestBean = new Product();
        try {
            requestBeans = loadBeanList(Constants.PROD_CSV_DS_PATH, requestBean);
        }
        catch(Exception e){
            log.error(e.getClass().getName() + ": " + e.getMessage());
            return requestBeans;
        }
        return requestBeans;
    }

    /**
     * Creates a line in <code>CSV_DS_PATH</code> generated from entered Emp.class object
     */
    @Override
    public boolean saveEmpRecord(Emp emp){
        try{
            beansEmp = loadBeanList(Constants.EMP_CSV_DS_PATH, new Emp());
            log.info(beansEmp);
            beansEmp.add(emp);
            writeCSV(beansEmp);
        } catch (Exception e){
            log.error(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Deletes a line with entered id from <code>CSV_DS_PATH</code>
     */
    @Override
    public boolean deleteEmpRecord(long id) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        beansEmp = loadBeanList(Constants.EMP_CSV_DS_PATH, new Emp());
        log.info(beansEmp);
        Predicate<Emp> isDeletable = emp -> emp.getId() == id;
        beansEmp.removeIf(isDeletable);
        return writeCSV(beansEmp);
    }

    @Override
    public boolean updateEmpRecord(long id, Emp bean) {
        try {

            log.info("Start updating record: reading file");
            beansEmp = loadBeanList(Constants.EMP_CSV_DS_PATH, new Emp());
            log.info("Searching required record: searching id");
            int index = beansEmp.indexOf(getEmpById(id));
            log.info("Insert new values");
            beansEmp.set(index, bean);
            writeCSV(beansEmp);
            log.info("Updating complete");
        }
        catch(Exception e) {
            log.info("Updating Error");
            log.info(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean saveProductRecord(Product prod) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        beansProduct = loadBeanList(Constants.PROD_CSV_DS_PATH, new Product());
        log.info(beansProduct);
        beansProduct.add(prod);
        return writeCSV(beansProduct);
    }

    @Override
    public boolean deleteProductRecord(long id){
    try {
            beansProduct = loadBeanList(Constants.PROD_CSV_DS_PATH, new Product());
            log.info(beansProduct);
            Predicate<Product> isDeletable = prod -> prod.getId() == id;
            beansProduct.removeIf(isDeletable);
            writeCSV(beansProduct);
        } catch (Exception e){
            log.error(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean updateProductRecord(long id, Product prod) {
        try {

            log.info("Start updating record: reading file");
            beansProduct = loadBeanList(Constants.PROD_CSV_DS_PATH, new Product());
            log.info("Searching required record: searching id");
            int index = beansProduct.indexOf(getProductById(id));
            log.info("Insert new values");
            beansProduct.set(index, prod);
            writeCSV(beansProduct);
            log.info("Updating complete");
        }
        catch(Exception e) {
            log.info("Updating Error");
            log.info(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return true;
    }



    /**
     * Returns bean loaded from <code>CSV_DS_PATH</code> by id
     * @return Emp object with id as in parameter
     */
    @Override
    public Emp getEmpById(long id) throws IOException {
        return loadBeanList(Constants.EMP_CSV_DS_PATH, new Emp()).stream()
                .filter(bean-> bean.getId() == id)
                .findAny().get();
    }

    @Override
    public Product getProductById(long id) throws IOException {
        return loadBeanList(Constants.PROD_CSV_DS_PATH, new Product()).stream()
                .filter(bean-> bean.getId() == id)
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
    private <T> List<T> loadBeanList(String path, T beanClass) throws IOException {
        List<T> beans = new CsvToBeanBuilder(
                new FileReader(ConfigurationUtil
                        .getConfigurationEntry(path)))
                .withType(beanClass.getClass())
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
            default: return "Slomalos'";
        }
    }
}
