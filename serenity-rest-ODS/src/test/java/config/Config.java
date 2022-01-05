package config;

import io.restassured.response.Response;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@Configuration
//@PropertySource("") -> To Customize Property File .Provide Name here
public class Config {

    @Value("${ExcelPath}")
    private String excelPath;

    @Bean
    public FileOutputStream output()
    {
        try
        {
            File file = new File(excelPath);
            FileOutputStream os = new FileOutputStream(file);
            return os;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;

        }

    }

    @Bean
    public List<Response> arrayList()
    {
        try {
            List<Response> list = new ArrayList<>();
            return list;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Bean
    public XSSFWorkbook workbook()
    {
        try
        {
            File file = new File(excelPath);
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook workBook = new XSSFWorkbook(fis);
            return workBook;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }










}
