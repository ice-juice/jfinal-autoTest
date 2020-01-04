package com.test; /**
 * @author:Helen
 * @date：2018年4月7日
 * @Description: 百度搜索测试
 */

import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.thread.HfAutoPayOrder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;


public class myTestNg extends Controller {
    private static ConcurrentHashMap<String, WebDriver> driverMap = new ConcurrentHashMap<>();
    private static CopyOnWriteArraySet<WebDriver> WebDriverSet = new CopyOnWriteArraySet<WebDriver>();
    public static List<WebDriver> driverList;
Prop p= PropKit.use("accountList.properties");
    public void login() {
        driverList=new ArrayList<>();
        String num=get(0);
        WebDriver driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", "C:/Program Files (x86)/Google/Chrome/Application/chromedriver.exe");
        driver=Hf168Page.login(driver,"http://hf168.in",p.get("hfAccount"+num),p.get("hfPassword"+num));
        driverList.add(driver);
        driverMap.put(num,driver);
        renderText("OK");
    }

    public void order() throws InterruptedException {
        Map<String, String> paramMap = new HashMap<>();
        String notifyDataStr = HttpKit.readData(getRequest());
        JSONObject objectJson = JSONObject.fromObject(notifyDataStr);
        paramMap.put("totalScore",objectJson.getString("totalScore"));

        String totalScore = objectJson.getString("totalScore");
        String singleScore = objectJson.getString("singleScore");
        String playTypeCode = objectJson.getString("type");

        List<String> nums = objectJson.getJSONArray("numbers");

        if (driverMap==null){
            renderText("网盘未登陆");
            return;
        }
        Integer i=0;
        while (driverMap.size()<1){
            if (i>10){
                renderText("网盘连接超时");
                return;
            }
            Thread.sleep(1000);
            i++;
        }
        String accountNum =null;
        WebDriver driver=null;
        for(ConcurrentHashMap.Entry<String, WebDriver> it: driverMap.entrySet()){
            accountNum = it.getKey();
            driver=it.getValue();
            break;
        }
        driverMap.remove(accountNum);

        if (driver == null){
            renderText("FAIL");
            return;
        }

        //如果网盘登录超时或被挤下线，则重新登录
        try {
            Alert alert = driver.switchTo().alert();
            System.out.println(alert.getText());
            alert.accept();
            driver=Hf168Page.login(driver,"http://hf168.in",p.get("hfAccount"+accountNum),p.get("hfPassword"+accountNum));

        }catch (Exception e)
        {
            System.out.println("网盘登录正常");
        }

        try {
            long time = System.currentTimeMillis();
            WebDriverWait wait = new WebDriverWait(driver, 10, 1);
            String playType= "特码,色波";
            String type = "G";
            System.out.println(driver.getPageSource());
           String oldScore= driver.findElement(By.id("mem_money")).getAttribute("innerHTML");

            driver.findElement(By.partialLinkText(playType)).click();
            //下单号码
            for (String num : nums) {
                List<WebElement> elementList= driver.findElements(By.xpath("//input[@name='" +type+ num + "']"));
                if (elementList!=null &&  elementList.size()>0){
                    elementList.get(0).sendKeys(singleScore);
                }
            }
            driver.findElement(By.id("SubBtn")).submit();
            wait.until(ExpectedConditions.alertIsPresent());

            Alert alert = driver.switchTo().alert();
            System.out.println(alert.getText());
            alert.accept();

            wait.until(new ExpectedCondition<WebElement>(){
                @Override
                public WebElement apply(WebDriver text) {
                    return text.findElement(By.id("mem_money"));
                }
            });
           //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mem_money")));
          String newScore=  driver.findElement(By.id("mem_money")).getAttribute("innerHTML");
          System.out.println("耗时:"+(System.currentTimeMillis()-time));
          System.out.println(oldScore+"-"+newScore);

            renderText("SUCCESS");
        }catch (Exception e){
            renderText("FAIL");
        }finally {
            driverList.add(driver);
        }

    }

    public void CaptureScreenshot(String fileName, WebDriver driver) {
        String dirName = "D:\\screenshot";
        if (!(new File(dirName).isDirectory())) {
            new File(dirName).mkdir();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String time = sdf.format(new Date());
        TakesScreenshot tsDriver = (TakesScreenshot) driver;
        File image = new File(dirName + File.separator + time + "_" + fileName==null?"":fileName + ".png");
        tsDriver.getScreenshotAs(OutputType.FILE).renameTo(image);

        File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile,image);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void baiduLogin(){
        WebDriver driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", "C:/Program Files (x86)/Google/Chrome/Application/chromedriver.exe");
        BaiduPage page = new BaiduPage(driver);
         driver = page.login();
         driverList.add(driver);
    }
    public void search() throws InterruptedException {
        String keyWord = get("key");
        while (driverList.size()<1){
            Thread.sleep(1);
        }
        WebDriver driver = driverList.get(0);
        driverList.remove(0);

        /*    HfAutoPayOrder.putOrder("","","",nums,"10",driver);*/
        if (driver != null){
            WebDriverWait wait = new WebDriverWait(driver,10,1);
            wait.until(new ExpectedCondition<WebElement>(){
                @Override
                public WebElement apply(WebDriver text) {
                    return text.findElement(By.id("kw"));
                }
            });
           driver.findElement(By.id("kw")).clear();
            driver.findElement(By.id("kw")).sendKeys(keyWord);
            wait.until(new ExpectedCondition<WebElement>(){
                @Override
                public WebElement apply(WebDriver text) {
                    return text.findElement(By.id("su"));
                }
            });
            driver.findElement(By.id("su")).click();
        }
        Thread.sleep(10*1000);

        driverList.add(driver);
        renderText("OK");

    }


}