import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class registerUserExcel {
    String username;
    String email;
    String password;
    double age;
    String invalidemail;
    String invalidpassword;

    public void readUser() throws IOException {
        File file = new File("C:\\Users\\pyogeshbhai\\Documents\\HU_API_MAIN_ASSIGNMENT\\testUserData.xlsx");
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheet("Sheet1");
        XSSFRow row1 = sheet.getRow(1);
        XSSFCell cell1 = row1.getCell(0);
        XSSFCell cell2 = row1.getCell(1);
        XSSFCell cell3 = row1.getCell(2);
        XSSFCell cell4 = row1.getCell(3);
        username = cell1.getStringCellValue();
        email = cell2.getStringCellValue();
        password = cell3.getStringCellValue();
        age = cell4.getNumericCellValue();
    }

    public void login() throws IOException {
        File file = new File("C:\\Users\\pyogeshbhai\\Documents\\HU_API_MAIN_ASSIGNMENT\\InvalidloginDetails.xlsx");
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheet("Sheet1");
        XSSFRow row1 = sheet.getRow(1);
        XSSFCell cell1 = row1.getCell(0);
        XSSFCell cell2 = row1.getCell(1);
        invalidemail = cell1.getStringCellValue();
        invalidpassword = cell2.getStringCellValue();
    }

    public void invalidRegisterDetails() throws IOException {
        File file = new File("C:\\Users\\pyogeshbhai\\Documents\\HU_API_MAIN_ASSIGNMENT\\testUserData.xlsx");
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheet("Sheet1");
        XSSFRow row1 = sheet.getRow(2);
        XSSFCell cell1 = row1.getCell(0);
        XSSFCell cell2 = row1.getCell(1);
        XSSFCell cell3 = row1.getCell(2);
        XSSFCell cell4 = row1.getCell(3);
        username = cell1.getStringCellValue();
        email = cell2.getStringCellValue();
        password = cell3.getStringCellValue();
        age = cell4.getNumericCellValue();
    }

}
