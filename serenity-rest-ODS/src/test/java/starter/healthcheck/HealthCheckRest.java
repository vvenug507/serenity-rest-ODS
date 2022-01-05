package starter.healthcheck;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.core.Serenity;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.util.List;


@Component
public class HealthCheckRest {


    @Value("${ExcelPath}")
    private String value;

    @Autowired
    XSSFWorkbook workBook;

    @Autowired
    FileOutputStream os;

    XSSFSheet workSheet;
    Response responseValue;

    @Autowired
    List<Response> responseList;

    public void readServiceURLs()
    {
        try
        {
            workSheet = workBook.getSheetAt(0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void executeServiceURLs()
    {
        try
        {
            for (int i = 1; i <= workSheet.getLastRowNum(); i++)
            {
                RestAssured(workSheet.getRow(i));
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void writeExcelResult()
    {
        try
        {
            for (int i = 0; i < responseList.size(); i++)
            {
                Response response = responseList.get(i);
                Row row = workSheet.getRow(i+1);
                if(response.getStatusCode()==500)
                {
                    CellStyle cellStyle = cellFail();
                    System.err.println("SERVICE IS DOWN - "+response.getStatusCode());
                    //To Print details in Excel
                    Cell cell1 = row.createCell(3);
                    Cell cell2 = row.createCell(4);
                    cell1.setCellStyle(cellStyle);
                    cell2.setCellStyle(cellStyle);
                    cell1.setCellValue("Failed");
                    cell2.setCellValue(response.getStatusLine());
                    Serenity.recordReportData().withTitle(row.getCell(0).getStringCellValue()).andContents(row.getCell(4).getStringCellValue());





                }
                else if(response.getStatusCode()==200 || response.getStatusCode()==201)
                {
                    CellStyle cellStyle= cellPass();
                    System.err.println("SERVICE IS UP - "+response.getStatusCode());
                    //To Print details in Excel
                    Cell cell1 = row.createCell(3);
                    Cell cell2 = row.createCell(4);
                    cell1.setCellStyle(cellStyle);
                    cell2.setCellStyle(cellStyle);
                    cell1.setCellValue("Pass");
                    cell2.setCellValue(response.getStatusLine());
                    Serenity.recordReportData().withTitle(row.getCell(0).getStringCellValue()).andContents(row.getCell(4).getStringCellValue());




                }
                else if(response.getStatusCode()==400 || response.getStatusCode()==404 || response.getStatusCode()==403)
                {
                    CellStyle cellStyle = cellOtherFail();
                    System.err.println("Check Request Payload - "+response.getStatusCode());
                    //To Print details in Excel
                    Cell cell1 = row.createCell(3);
                    Cell cell2 = row.createCell(4);
                    cell1.setCellStyle(cellStyle);
                    cell2.setCellStyle(cellStyle);
                    cell1.setCellValue("Pass");
                    cell2.setCellValue(response.getStatusLine());
                    Serenity.recordReportData().withTitle(row.getCell(0).getStringCellValue()).andContents(row.getCell(4).getStringCellValue());


                }

            }

            workBook.write(os);
            System.out.println("Writing on Excel file Finished ...");
            workBook.close();
            os.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void RestAssured(Row row)
    {
        try {
            RestAssured.baseURI = row.getCell(1).toString() + row.getCell(2).toString();
            System.out.println("Path - "+RestAssured.baseURI);
            System.out.println("BASE URI - "+RestAssured.baseURI+RestAssured.basePath);

            //Modify below for Auth Specific End Points
            RequestSpecification request = RestAssured.given();
            Response response = request.get();
            responseList.add(response);

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    private CellStyle cellPass()
    {
        try
        {
            CellStyle style = workBook.createCellStyle();
            style.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
            style.setFillPattern(FillPatternType.FINE_DOTS);
            return style;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private CellStyle cellFail()
    {
        try
        {
            CellStyle style = workBook.createCellStyle();
            style.setFillBackgroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.FINE_DOTS);
            return style;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private CellStyle cellOtherFail()
    {
        try {
            CellStyle style = workBook.createCellStyle();
            style.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
            style.setFillPattern(FillPatternType.FINE_DOTS);
            return style;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }





}
