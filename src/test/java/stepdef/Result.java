package stepdef;

import io.cucumber.java.en.Given;
import utilities.ExcelUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Result {

    List<Double> asinprice = new ArrayList<>();
    @Given("automation for result")
    public void automation_for_result() {

        String path = "./src/test/resources/testdata/database.xlsx";
        String sheetName = "result";
        ExcelUtils excelUtilsResult = new ExcelUtils(path,sheetName);

        for(int asinCount=1;asinCount<6;asinCount++){

            for(int i=1;i<14;i++){

                ExcelUtils excelUtils = new ExcelUtils(path,"seller"+i);
                String runtime = excelUtils.getCellData(0,1);
                double runTime = Double.valueOf(runtime);
                //System.out.println(runTime);

                String cellcondition = excelUtils.cellBlankorNot(asinCount+1,(int) runTime+1);


                if(runTime!=0.0 && cellcondition.equals("int")){

                    double price = excelUtils.getCellDataint(asinCount+1,(int) runTime+1);
                    excelUtilsResult.setCellData(price,asinCount,i);
                    asinprice.add(price);

                }
            }
            excelUtilsResult.setCellData(asinprice.stream().distinct().sorted().findFirst().get(),asinCount,14);
            excelUtilsResult.setCellData(asinprice.stream().distinct().sorted(Comparator.reverseOrder()).findFirst().get(),asinCount,15);
            asinprice.clear();

        }







    }
}
