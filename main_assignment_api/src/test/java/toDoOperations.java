import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;
@Listeners({TestExtent.class, TestListeners.class})
public class toDoOperations extends registerUserExcel {
    public String tokenGenerated;
    static Logger log = Logger.getLogger(String.valueOf(toDoOperations.class));

    @Test
    public void registerUser() throws IOException  {
        readUser();
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        String payload = "{\n" +
                "  \"name\" : \""+username+"\",\n" +
                "  \"email\" : \""+email+"\",\n" +
                "  \"password\" : \""+password+"\",\n" +
                "  \"age\" : \""+age+"\"\n" +
                "}";
        request.header("Content-Type", "application/json");
        Response responsefromGeneratedToken = request.body(payload).post("/user/register");
        responsefromGeneratedToken.prettyPrint();
        String jsonString = responsefromGeneratedToken.getBody().asString();
        tokenGenerated = JsonPath.from(jsonString).get("token");
        request.header("Authorization", "Bearer" + tokenGenerated)
                .header("Content-Type", "application/json");
        int statusCode = responsefromGeneratedToken.getStatusCode();
        Assert.assertEquals(statusCode /*actual value*/, 201 /*expected value*/);
        log.info("User Registered Successfully");
    }
    @Test
    public void loginUser(){
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + tokenGenerated)
                .header("Content-Type", "application/json");
        String loginDetails = "{\n" +
                "  \"email\" : \""+email+"\",\n" +
                "  \"password\" : \""+password+"\"\n" +
                "}";
        Response responseLogin = request.body(loginDetails).post("/user/login");
        responseLogin.prettyPrint();
        int statusCode = responseLogin.getStatusCode();
        Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/);
        log.info("User logged in successfully");
    }
    @Test
    public void validateUser(){
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        request.header("Authorization","Bearer "+ tokenGenerated)
                .header("Content-Type","application/json");
        Response responsevalidateUser = request.get("/user/me");
        responsevalidateUser.prettyPrint();
        int statusCode = responsevalidateUser.getStatusCode();
        Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/);
        log.info("User Validated Successfully");
    }

    @Test
    public void addTask() throws IOException {
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + tokenGenerated)
                .header("Content-Type", "application/json");
        FileInputStream inputStream = new FileInputStream("C:\\Users\\pyogeshbhai\\Documents\\HU_API_MAIN_ASSIGNMENT\\tasks.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheet("Sheet1");
        int rows = sheet.getPhysicalNumberOfRows();
        int cols = sheet.getRow(0).getLastCellNum();
        String description = null;
        String task = null;
        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (j == 0) {
                    description = sheet.getRow(i).getCell(j).getStringCellValue();
                }
                if (j == 1) {
                    task = sheet.getRow(i).getCell(j).getStringCellValue();
                }
            }
            String addTaskJson = "{\n" +
                    "\t\""+description+"\": \""+task+"\"\n" +
                    "}";
            Response responseaddTask = request.body(addTaskJson).post("/task");
            responseaddTask.prettyPrint();
            String str = responseaddTask.getBody().asString();
            String lnu = JsonPath.from(str).get("data.description");
            if (task.contains(lnu) == true) {
                System.out.println("Validated");
            } else {
                System.out.println(" Not Validated");
            }
            int statusCode = responseaddTask.getStatusCode();
            Assert.assertEquals(statusCode /*actual value*/, 201 /*expected value*/);
        }
        log.info("20 Tasks added successfully");
    }

    @Test
    public void getAllTask(){
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        request.header("Authorization","Bearer "+ tokenGenerated)
                .header("Content-Type","application/json");
        Response responsegetTask = request.get("/task");
        responsegetTask.prettyPrint();
        int statusCode = responsegetTask.getStatusCode();
        Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/);
        log.info("List of all tasks fetched successfully");
    }
    @Test
    public void paginationFor2(){
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request2 = RestAssured.given();
        request2.header("Authorization","Bearer "+ tokenGenerated)
                .header("Content-Type","application/json");
        Response response2 = request2.get("/task?limit=2");
        response2.prettyPrint();
        int statusCode = response2.getStatusCode();
        Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/);
        log.info("2 values displayed");
    }
    @Test
    public void paginationFor5(){
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request5 = RestAssured.given();
        request5.header("Authorization","Bearer "+ tokenGenerated)
                .header("Content-Type","application/json");
        Response response5 = request5.get("/task?limit=5");
        response5.prettyPrint();
        int statusCode = response5.getStatusCode();
        Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/);
        log.info("5 values displayed");
    }
    @Test
    public void paginationFor10(){
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request10 = RestAssured.given();
        request10.header("Authorization","Bearer "+ tokenGenerated)
                .header("Content-Type","application/json");
        Response response10 = request10.get("/task?limit=10");
        response10.prettyPrint();
        int statusCode = response10.getStatusCode();
        Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/);
        log.info("10 values displayed");
    }
    @Test
    public void duplicateRegistrationDetails() throws IOException {
        invalidRegisterDetails();
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        String payload = "{\n" +
                "  \"name\" : \""+username+"\",\n" +
                "  \"email\" : \""+email+"\",\n" +
                "  \"password\" : \""+password+"\",\n" +
                "  \"age\" : \""+age+"\"\n" +
                "}";
        request.header("Content-Type", "application/json");
        Response responsefromGeneratedToken = request.body(payload).post("/user/register");
        responsefromGeneratedToken.prettyPrint();
        Assert.assertEquals(responsefromGeneratedToken.statusCode(),400);
        log.info("Entered details are already present");
    }
    @Test
    public void invalidLoginDetails() throws IOException {
        login();
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        String invalidloginDetails = "{\n" +
                "  \"email\" : \""+invalidemail+"\",\n" +
                "  \"password\" : \""+invalidpassword+"\"\n" +
                "}";
        Response responseInvalidLogin = request.body(invalidloginDetails).post("/user/login");
        /*String actual = responseInvalidLogin.getBody().asString();
        String expected ="Unable to login";
        Assert.assertNotEquals(actual,expected);*/
        responseInvalidLogin.prettyPrint();
        Assert.assertEquals(responseInvalidLogin.statusCode(),400);
        log.info("User not registered");

    }
    @Test
    public void invalidTaskBody(){
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + tokenGenerated)
                .header("Content-Type", "application/json");
        String addTaskJson = "{\n" +
                "\t\"name\": \"nihar\"\n" +
                "}";
        Response responseaddTask = request.body(addTaskJson).post("/task");
        /*String actual = responseaddTask.getBody().asString();
        String expected = "Task validation failed: description: Path `description` is required.";
        Assert.assertNotEquals(actual,expected);*/
        responseaddTask.prettyPrint();
        Assert.assertEquals(responseaddTask.statusCode(),400);
        log.info("Wrong request Body");
    }
}
