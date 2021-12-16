package ru.sfedu.arch;

public class Constants {
    public static final String DEFAULT_CONFIG_PATH = "/main/resources/environment.properties";
    public static final String OWNER_FIRST_NAME = "OWNER.FIRSTNAME";
    public static final String OWNER_SECOND_NAME = "OWNER.SECONDNAME";
    public static final String EMP_CSV_DS_PATH = "EMP.CSV.PATH";
    public static final String PROD_CSV_DS_PATH = "PROD.CSV.PATH";
    public static final String EMP_XML_DS_PATH = "EMP.XML.PATH";
    public static final String PROD_XML_DS_PATH = "PROD.XML.PATH";
    public static final String UNKNOWN_SOURCE_XML = "BEBRA";

    public static final String INSERT_INTO_EMP = "INSERT INTO EMP VALUES (%s,'%s',%s)";
    public static final String INSERT_INTO_PROD = "INSERT INTO PRODUCT VALUES (%s,'%s',%s)";
}
