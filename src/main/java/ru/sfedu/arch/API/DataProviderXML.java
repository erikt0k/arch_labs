package ru.sfedu.arch.API;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.arch.Constants;
import ru.sfedu.arch.Client;
import ru.sfedu.arch.Emp;
import ru.sfedu.arch.Product;
import ru.sfedu.arch.utils.ConfigurationUtil;
import ru.sfedu.arch.utils.WrapperXML;

import java.io.*;
import java.util.List;
import java.util.function.Predicate;

public class DataProviderXML implements IDataProvider{
    private static final Logger log = LogManager.getLogger(Client.class);


    List<Emp> empBeans = null;
    List<Product> productBeans = null;
    
    Emp empBean = new Emp();
    Product productBean = new Product();


    @Override
    public Emp getEmpById(long id) {
        try {
            log.debug("Start receiving record by id");
            empBeans = loadBeanList(Constants.EMP_XML_DS_PATH);
            empBean = empBeans.stream()
                    .filter(bean -> bean.getId() == id)
                    .findAny()
                    .get();
            log.trace("Receiving record complete");
        }
        catch(Exception e) {
            log.error("Receiving record by id Error ");
            log.error(e.getClass().getName() + ": " + e.getMessage());
        }
        return empBean;
    }

    @Override
    public Product getProductById(long id) {
        try {
            log.debug("Start receiving record by id");
            productBeans = loadBeanList(Constants.PROD_XML_DS_PATH);
            productBean = productBeans.stream()
                    .filter(bean -> bean.getId() == id)
                    .findAny()
                    .get();
            log.trace("Receiving record complete");
        }
        catch(Exception e) {
            log.error("Receiving record by id Error ");
            log.error(e.getClass().getName() + ": " + e.getMessage());
        }
        return productBean;
    }



    @Override
    public boolean deleteEmpRecord(long id) {
        try {
            log.debug("Start deleting record: reading file");
            empBeans = loadBeanList(Constants.EMP_XML_DS_PATH);
            log.trace("Searching required record");
            Predicate<Emp> isDeletable = emp -> emp.getId() == id;
            log.trace("Removing required record");
            empBeans.removeIf(isDeletable);
            log.trace("Saving");
            saveFile(empBeans);
        }
        catch(Exception e) {
            log.error("Deleting Error");
            log.error(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteProductRecord(long id) {
        try {
            log.debug("Start deleting record: reading file");
            productBeans = loadBeanList(Constants.PROD_XML_DS_PATH);
            log.trace("Searching required record");
            Predicate<Product> isDeletable = product -> product.getId() == id;
            log.trace("Removing required record");
            productBeans.removeIf(isDeletable);
            log.trace("Saving");
            saveFile(productBeans);
        }
        catch(Exception e) {
            log.error("Deleting Error");
            log.error(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return true;
    }



    @Override
    public boolean updateEmpRecord(long id, Emp emp) {
        try {
            log.debug("Start updating record: reading file");
            empBeans = loadBeanList(Constants.EMP_XML_DS_PATH);
            log.trace("Searching required record: searching id");
            int index = empBeans.indexOf(getEmpById(id));
            log.trace("Insert new values");
            empBeans.set(index, emp);
            saveFile(empBeans);
            log.trace("Updating complete");
        }
        catch(Exception e) {
            log.error("Updating Error");
            log.error(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean updateProductRecord(long id, Product product) {
        try {
            productBeans = loadBeanList(Constants.PROD_XML_DS_PATH);
            log.info(productBeans);
            int index = productBeans.indexOf(getProductById(id));
            log.debug(index);
            log.trace("Insert new values");
            productBeans.set(index, product);
            saveFile(productBeans);
            log.trace("Updating complete");
        }
        catch(Exception e) {
            log.error(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return true;
    }



    @Override
    public boolean saveEmpRecord(Emp emp) {
        try {
            log.debug("Start adding record: reading file");
            empBeans = loadBeanList(Constants.EMP_XML_DS_PATH);
            log.trace("Adding record");
            empBeans.add(emp);
            log.trace("Adding complete");
            saveFile(empBeans);
        }
        catch(Exception e) {
            log.error(" Adding Error");
            log.error(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean saveProductRecord(Product product) {
        try {
            log.debug("Start adding record: reading file");
            productBeans = loadBeanList(Constants.PROD_XML_DS_PATH);
            log.trace("Adding record");
            productBeans.add(product);
            log.trace("Adding complete");
            saveFile(productBeans);
        }
        catch(Exception e) {
            log.error(" Adding Error");
            log.error(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return true;
    }



    private <T> boolean saveFile(List <T> beans){
        try {
            log.debug("start writing");
            Serializer serializer = new Persister();
            File result = new File(ConfigurationUtil.
                    getConfigurationEntry(findPath(beans)));
            Writer writer = new FileWriter(result);
            WrapperXML<T> xml = new WrapperXML<>(beans);
            serializer.write(xml, writer);
        }
        catch (Exception e){
            log.error("Writing file Error");
            log.error(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return true;
    }


    private <T> List<T> loadBeanList(String path) {
        List<T> loadedBeans = null;
        try {
            log.debug("Start reading file");
            Serializer serializer = new Persister();
            FileReader file = new FileReader(ConfigurationUtil.getConfigurationEntry(path));
            WrapperXML<T> xml;
            xml = serializer.read(WrapperXML.class, file);
            loadedBeans = xml.getList();
            file.close();
        }
        catch(Exception e){
            log.error("Loading Error");
            log.error(e.getClass().getName() + ": " + e.getMessage());
        }
        return loadedBeans;
    }


    private  <T> String findPath(List<T> bean){
        log.debug("Class of elements inside list: "+bean.get(0).getClass().getSimpleName());
        switch(bean.get(0).getClass().getSimpleName()) {
            case "Emp":
                return Constants.EMP_XML_DS_PATH;
            case "Product":
                return Constants.PROD_XML_DS_PATH;
            default: return Constants.UNKNOWN_SOURCE_XML;

        }
    }



    @Override
    public boolean initDataSource() {
        return false;
    }


}
