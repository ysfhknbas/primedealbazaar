package stepdef;

import io.cucumber.java.en.Given;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import utilities.Driver;
import utilities.ExcelUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Deneme {

    List<Map<String,String>> datalist;
    @Given("aotumation for seller")
    public void aotumation_for_seller() throws InterruptedException {

        //--------------------------------------------
        LocalDateTime now = LocalDateTime.now();
        int day = now.getDayOfMonth();
        int month = now.getMonthValue();
        int hour = now.getHour();
        int minutes = now.getMinute();
        String date = day+"/"+month+"--"+hour+":"+minutes;
        //--------------------------------------------


        String path = "./src/test/resources/testdata/deneme.xlsx";
        String sheetName = "Sheet1";
        ExcelUtils excelUtils = new ExcelUtils(path,sheetName);
        datalist = excelUtils.getDataList();
        //WebElement price1 = Driver.getDriver().findElement(By.xpath("//span[@class='a-price aok-align-center']//span[@class='a-offscreen']"));
        String runtime = excelUtils.getCellData(0,1);
        double runTime = Double.valueOf(runtime);
        //System.out.println(runTime);



        for (Map<String,String> eachdata : datalist)
        {

            int row = datalist.indexOf(eachdata);
            String amazonurl = "https://www.amazon.com/dp/";
            Driver.getDriver().get(amazonurl+eachdata.get("asin")+"/?m="+eachdata.get("marketid"));
            Thread.sleep(1000);
            try {
                WebElement price2 = Driver.getDriver().findElement(By.xpath("//span[@class='a-price aok-align-center']"));
                String pricestr2 = price2.getText();
                String pricel= pricestr2.replaceAll("[^0-9]","");
                int num = Integer.valueOf(pricel);
                double price = (double) num/100;
                //System.out.println(eachdata.get("asin")+"-->"+price+" "+row);

                excelUtils.setCellData(price,row+2, (int) runTime+2);
                String cellcondition = excelUtils.cellBlankorNot(row+2,(int) runTime+2);



                if(runTime!=0.0 && cellcondition.equals("int")){

                    double previous = excelUtils.getCellDataint(row+2,(int) runTime+1);
                    double last = excelUtils.getCellDataint(row+2,(int) runTime+2);
                    FileOutputStream file = new FileOutputStream(path);

                    if(last>previous){
                        Cell cell;
                        CellStyle cellStyle = excelUtils.workBook.createCellStyle();
                        cell = excelUtils.workSheet.getRow(row+2).getCell((int) runTime+2);
                        cellStyle.setFillForegroundColor(IndexedColors.TURQUOISE.getIndex());
                        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        cell.setCellStyle(cellStyle);
                        excelUtils.workBook.write(file);

                    }
                    else if (last<previous) {
                        Cell cell1;
                        CellStyle cellStyle1 = excelUtils.workBook.createCellStyle();
                        cell1 = excelUtils.workSheet.getRow(row+2).getCell((int) runTime+2);
                        cellStyle1.setFillForegroundColor(IndexedColors.RED.getIndex());
                        cellStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        cell1.setCellStyle(cellStyle1);
                        excelUtils.workBook.write(file);
                    }

                }

            }
            catch (NoSuchElementException | FileNotFoundException e){ } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
        excelUtils.setCellData((int) runTime+1,0,1);
        excelUtils.setCellDataString(date,1,(int)runTime+2);

        Driver.closeDriver();


    }
}
