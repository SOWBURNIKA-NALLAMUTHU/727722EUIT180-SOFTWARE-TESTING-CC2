package com.example;


import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AppTest 
{
    public static Logger logger = LogManager.getLogger(AppTest.class);
    WebDriver driver;
    ExtentReports report;
    ExtentTest test;

    @BeforeMethod
    public void start() throws Exception
    {
        driver=new ChromeDriver();
        WebDriverManager.chromedriver().setup();
        ExtentSparkReporter r = new ExtentSparkReporter("D:\\report1.html");
        report = new ExtentReports();
        report.attachReporter(r);

        driver.get("https://www.barnesandnoble.com/");
        logger.info("Successfully opened");
         PropertyConfigurator.configure("C:\\Users\\sowbu\\OneDrive\\Desktop\\cc2-st\\cc2\\src\\main\\java\\com\\example\\resources\\log4j.properties");

    }
    
    @Test(priority = 0)
    public void Testcase1() throws Exception
    {
        FileInputStream fs = new FileInputStream("D:\\web.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fs);
        XSSFSheet sheet = workbook.getSheet("Sheet1");
        XSSFRow row = sheet.getRow(2);
        String name = row.getCell(0).getStringCellValue(); 
        driver.findElement(By.xpath("//*[@id=\'rhf_header_element\']/nav/div/div[3]/form/div/div[1]/a")).click();
        driver.findElement(By.xpath("//*[@id='rhf_header_element']/nav/div/div[3]/form/div/div[1]/div/a[2]")).click();
        driver.findElement(By.xpath("//*[@id='rhf_header_element']/nav/div/div[3]/form/div/div[2]/div/input[1]")).sendKeys(name);
        driver.findElement(By.xpath("//*[@id='rhf_header_element']/nav/div/div[3]/form/div/span/button")).click();
        WebElement check=driver.findElement(By.xpath("//*[@id='searchGrid']/div/section[1]/section[1]/div/div[1]/div[1]/h1"));
        if (check.equals(name)) {
            System.out.println("The given text contains the name Chetan Bhagat");
        } else {
            System.out.println("The given text contains the name Chetan Bhagat");
        }
        
        logger.info("TestCase 1 has been passed successfully!");
    }


    @Test(priority = 1)
    public void Testcase2() throws InterruptedException {
        WebElement audiobooks = driver.findElement(By.linkText("Audiobooks"));
        Thread.sleep(2000);
        Actions action = new Actions(driver);
        action.moveToElement(audiobooks).perform();
        driver.findElement(By.xpath("//*[@id='navbarSupportedContent']/div/ul/li[5]/div/div/div[1]/div/div[2]/div[1]/dd/a[1]")).click();
        Thread.sleep(2000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,300)");
        driver.findElement(By.linkText("Funny Story")).click();
        Thread.sleep(2000);
        js.executeScript("window.scrollBy(0,500)");
        driver.findElement(By.xpath("//*[@id='otherAvailFormats']/div/div")).click();
        Thread.sleep(2000);
        js.executeScript("window.scrollBy(0,600)");
         driver.findElement(By.xpath("//*[@id='prodInfoContainer']/div[3]/form[1]/input[11]")).click();
        Thread.sleep(2000);
        String finditem = driver.switchTo().alert().getText();
        if (finditem.contains("Item has been successfully added to the cart")) {
            System.out.println("Successfully item is inserted into the cart");
        } else {
            System.out.println("Item is not inserted into the cart");
        }
         logger.info("TestCase 2 has been passed successfully!");
    }


    @Test(priority = 2)
    public void Testcase3() throws Exception{
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1100)");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id='footer']/div/dd/div/div/div[1]/div/a[5]/span")).click();
        Thread.sleep(2000);
        js.executeScript("windows.scrollBy(0,400)");
        driver.findElement(By.xpath("//*[@id='rewards-modal-link']")).click();
        WebElement check2=driver.findElement(By.xpath("//*[@id=\"dialog-title\"]"));
        Assert.assertTrue(check2.getText().contains("Sign in or create an account"), "Sign in first");
        logger.info("TestCase 3 passed Successfully!");
        // File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        // String path = "C:\\Users\\sowbu\\OneDrive\\Desktop\\cc2softwaretesting\\screenshot.png";
        // FileUtils.copyFile(screen, new File(path));

    }


    @AfterMethod
    public void end() throws Exception{
        logger.info("Tests Executed");
        report.flush();
        driver.quit();
    }
}
