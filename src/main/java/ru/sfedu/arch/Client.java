package ru.sfedu.arch;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.arch.API.DataProviderCsv;
import ru.sfedu.arch.API.DataProviderDB;
import ru.sfedu.arch.API.DataProviderXML;
import ru.sfedu.arch.utils.ConfigurationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client {
    private static final Logger log = LogManager.getLogger(Client.class);

    public static void main(String[] args) throws IOException {
        DataProviderCsv dataProviderCsv = new DataProviderCsv();
        DataProviderXML dataProviderXML = new DataProviderXML();
        DataProviderDB dataProviderDB = new DataProviderDB();


        try {
           Product pivo = new Product();
            pivo.setName("Baltika 9");
            pivo.setPrice(70);
            pivo.setId();
            log.info("im starting to save pivo");
            dataProviderDB.saveProductRecord(pivo);


        } catch (Exception e) {
            log.info(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
