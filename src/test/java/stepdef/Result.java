package stepdef;

import io.cucumber.java.en.Given;
import utilities.ExcelUtils;
import java.util.ArrayList;
import java.util.List;

public class Result {

    List<Double> asinprice = new ArrayList<>();
    @Given("automation for result")
    public void automation_for_result() {

        String path = "./src/test/resources/testdata/result.xlsx";
        String sheetName = "Sheet1";
        ExcelUtils excelUtilsResult = new ExcelUtils(path,sheetName);


        for(int asinCount=1;asinCount<199;asinCount++){
            double min =0.0;
            double max = 100.0;
            excelUtilsResult.setCellData(max,asinCount,15);
            excelUtilsResult.setCellData(min,asinCount,16);

            for(int i=0;i<14;i++){
                String paths = "./src/test/resources/testdata/seller"+i+".xlsx";
                ExcelUtils excelUtils = new ExcelUtils(paths,"Sheet1");
                String runtime = excelUtils.getCellData(0,1);
                double runTime = Double.valueOf(runtime);
                //System.out.println(runTime);

                String cellcondition = excelUtils.cellBlankorNot(asinCount+1,(int) runTime+1);


                if(runTime!=0.0 && cellcondition.equals("int")){

                    double price = excelUtils.getCellDataint(asinCount+1,(int) runTime+1);
                    excelUtilsResult.setCellData(price,asinCount,i+1);
                    asinprice.add(price);

                    if(price<max){
                        excelUtilsResult.setCellData(price,asinCount,15);
                        max=price;
                    }

                    if(price>min){
                        excelUtilsResult.setCellData(price,asinCount,16);
                        min=price;
                    }

                }
            }
        }
    }
}
