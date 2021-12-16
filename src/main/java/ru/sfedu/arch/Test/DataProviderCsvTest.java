package ru.sfedu.arch.Test;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import ru.sfedu.arch.API.DataProviderCsv;
import ru.sfedu.arch.Emp;
import ru.sfedu.arch.Product;

import java.io.IOException;

public class DataProviderCsvTest extends TestCase {
    private static Logger log = LogManager.getLogger(DataProviderCsvTest.class);
    Emp empBean = new Emp();
    Product productBean = new Product();
    DataProviderCsv dataProviderCsv = new DataProviderCsv();

    @Test
    public void getEmpById() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Emp empBean = new Emp();
        log.info("getEmpById");
        empBean.setId();
        empBean.setAge(21);
        empBean.setName("Erik");
        dataProviderCsv.saveEmpRecord(empBean);
        TestCase.assertEquals(empBean, dataProviderCsv.getEmpById(empBean.getId()));
        // log.info(dataProviderCsv.getEmpById(empBean.getId()));
        dataProviderCsv.deleteEmpRecord(empBean.getId());
    }

    @Test
    public void getEmpByIdERROR() throws IOException {
        Emp empBean = new Emp();
        log.info("getEmpById");

        TestCase.assertEquals(empBean, dataProviderCsv.getEmpById(999999999999L));

    }
    @Test
    public void getProductById() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Product productBean = new Product();
        log.info("getProductById");
        productBean.setId();
        productBean.setName("MEH-MAT12");
        productBean.setPrice(501);
        dataProviderCsv.saveProductRecord(productBean);
        dataProviderCsv.deleteProductRecord(productBean.getId());
    }

    @Test
    public void getProductByIdERROR() throws IOException {
        Product productBean = new Product();
        log.info("getProductById");

        TestCase.assertEquals(productBean, dataProviderCsv.getProductById(16379086875877L));
        // log.info(dataProviderCsv.getProductById(1637759919375L));

    }

    @Test
    public void saveEmpRecord() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Emp empBean = new Emp();
        log.info("saveEmpRecord");
        empBean.setId();
        empBean.setAge(211);
        empBean.setName("Vova3");
        TestCase.assertTrue(dataProviderCsv.saveEmpRecord(empBean));
        dataProviderCsv.deleteEmpRecord(empBean.getId());
    }

    @Test
    public void saveProductRecord() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Product productBean = new Product();
        log.info("saveProductRecord");
        productBean.setId();
        productBean.setName("HVT-27");
        productBean.setPrice(5000);
        TestCase.assertTrue(dataProviderCsv.saveProductRecord(productBean));
        dataProviderCsv.getAllProduct();
        dataProviderCsv.deleteProductRecord(productBean.getId());
        dataProviderCsv.getAllProduct();
    }

    @Test
    public void deleteEmpRecord() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Emp empBean = new Emp();
        log.info("deleteEmpRecord");
        empBean.setId();
        empBean.setAge(30);
        empBean.setName("Pasha");
        dataProviderCsv.saveEmpRecord(empBean);
        dataProviderCsv.getAllEmp();
        TestCase.assertTrue(dataProviderCsv.deleteEmpRecord(empBean.getId()));
        dataProviderCsv.getAllEmp();

    }




    @Test
    public void deleteProductRecord() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Product productBean = new Product();
        log.info("deleteProductRecord");
        productBean.setId();
        productBean.setName("DGTU");
        productBean.setPrice(1000);
        dataProviderCsv.saveProductRecord(productBean);
        TestCase.assertTrue(dataProviderCsv.deleteProductRecord(productBean.getId()));

    }

    @Test
    public void updateEmpRecord() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Emp empBean = new Emp();
        log.info("updateEmpRecord");
        empBean.setId();
        empBean.setAge(200);
        empBean.setName("Vova1");
        dataProviderCsv.saveEmpRecord(empBean);
        empBean.setName("Vova2");
        TestCase.assertTrue(dataProviderCsv.updateEmpRecord(empBean.getId(),empBean));
        dataProviderCsv.deleteEmpRecord(empBean.getId());
    }
    @Test
    public void updateEmpRecordERROR() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Emp empBean = new Emp();
        log.info("updateEmpRecord");
        empBean.setId();
        empBean.setAge(200);
        empBean.setName("Vova1");
        dataProviderCsv.saveEmpRecord(empBean);
        log.info("Before update:");
        dataProviderCsv.getAllEmp();
        empBean.setName("Vova2");
        TestCase.assertFalse(dataProviderCsv.updateEmpRecord(999999999999L,empBean));
        log.info("After update:");
        dataProviderCsv.getAllEmp();
        dataProviderCsv.deleteEmpRecord(empBean.getId());
    }

    @Test
    public void updateProductRecord() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Product productBean = new Product();
        log.info("updateProductRecord");
        productBean.setId();
        productBean.setName("MehMat");
        productBean.setPrice(101);
        dataProviderCsv.saveProductRecord(productBean);
        log.info("Before update:");
        dataProviderCsv.getAllProduct();
        productBean.setPrice(201);
        TestCase.assertTrue(dataProviderCsv.updateProductRecord(productBean.getId(), productBean));
        log.info("After update:");
        dataProviderCsv.getAllProduct();
        dataProviderCsv.deleteProductRecord(productBean.getId());
    }

    @Test
    public void updateProductRecordERROR() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Product productBean = new Product();
        log.info("updateProductRecord");
        productBean.setId();
        productBean.setName("MehMat");
        productBean.setPrice(101);
        dataProviderCsv.saveProductRecord(productBean);
        log.info("Before update:");
        dataProviderCsv.getAllProduct();
        productBean.setPrice(201);
        TestCase.assertFalse(dataProviderCsv.updateProductRecord(999999999999999L, productBean));
        log.info("After update:");
        dataProviderCsv.getAllProduct();
        dataProviderCsv.deleteProductRecord(productBean.getId());
    }


    @Test
    public void getAllEmp() {
        log.info("getAllEmp");
        dataProviderCsv.getAllEmp();

    }

    @Test
    public void getAllProduct() {
        log.info("getAllProduct");
        dataProviderCsv.getAllProduct();
    }
}