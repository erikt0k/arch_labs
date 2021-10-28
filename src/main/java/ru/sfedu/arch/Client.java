package ru.sfedu.arch;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.arch.API.DataProviderCsv;
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


        try {

            Reader reader = new FileReader(ConfigurationUtil.getConfigurationEntry(Constants.EMP_CSV_DS_PATH));
            CSVReader csvReader = new CSVReader(reader);
            log.info(csvReader);
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                log.info(Arrays.toString(line));
            }

            log.info("--------Beans--------");

            List<Product> newMan = dataProviderCsv.loadBeanList();
            //dataProviderCsv.getPathByBean(newMan);
            log.info("NEW CHEL------"+dataProviderCsv.getPathByList(newMan));
            dataProviderCsv.deleteRecord(1);


        } catch (Exception e) {
            log.info(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
