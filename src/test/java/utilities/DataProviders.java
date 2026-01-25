package utilities;

import lombok.Data;
import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {

    @DataProvider(name="EmailLogin")
    public Object[][] getData() throws IOException {
        String path=System.getProperty("user.dir")+"\\TestData\\EmailData.xlsx";
        ExcelUtility eu= new ExcelUtility(path);
        String data=eu.getCellValue("Sheet1",1,0);
        return new Object[][]{{data}};
    }

}
