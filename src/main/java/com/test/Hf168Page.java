package com.test; /**
 * @author:Helen
 * @date：2018年4月7日
 * @Description: 百度页面，对象定位和操作，继承BasePage
 */

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Hf168Page {
    public Hf168Page() {

    }

    /**
     * selenium-webdriver中获取页面元素的方式有很多，使用注解获取页面元素是其中一种途径，
     * 方式有3种：@FindBy、@FindBys、@FindAll。下文对3中类型的区别和使用场景进行介绍
     *1、
     * @Findbys({
     *     @FindBy(className = "A"),
     *     @FindBy(className = "B")
     * })
     * FindBys 相当于是取交集，是先根据第一个注解获取到对应元素，然后根据第二个注解再帅选出对应的页面元素。
     * 如先找到符合classname=A的元素，再在这些元素集中找到classname=B的所有元素
     *
     * 2、
     * @FindAll({
     *     @FindBy(id = "A"),
     *     @FindBy(id = "B")
     * })
     * FindAll相当于是取并集，如找到id=A和id=B的所有元素
     * */





    public static WebDriver login(WebDriver driver, String url,String username, String password){
        driver.get("http://hf168.in");
        WebDriverWait wait = new WebDriverWait(driver,10,1);
        wait.until(new ExpectedCondition<WebElement>(){
            @Override
            public WebElement apply(WebDriver text) {
                return text.findElement(By.xpath("//input[@name='LoginCode']"));
            }
        });

        String loginCode = "668899";
        WebElement loginInput =driver.findElement(By.name("LoginCode"));
        loginInput.clear();
        loginInput.sendKeys(loginCode);

        wait.until(new ExpectedCondition<WebElement>(){
            @Override
            public WebElement apply(WebDriver text) {
                return text.findElement(By.id("submit2"));
            }
        });
        WebElement submitEle = driver.findElement(By.id("submit2"));
        submitEle.click();

        List<WebElement> linesDiv=driver.findElements(By.className("css_tr"));
        for (WebElement e : linesDiv){
            if (e.getText().contains("最快")){
                System.out.println(e.getText());
                e.findElements(By.cssSelector(".css_td > a")).get(0).click();
                break;
            }
        }

        wait.until(new ExpectedCondition<WebElement>(){
            @Override
            public WebElement apply(WebDriver text) {
                return text.findElement(By.xpath("//frame"));
            }
        });

        driver.switchTo().frame(0);

        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[@name='Account']"))));// 加入显式等待

        driver.findElement(By.id("Account")).clear();
        driver.findElement(By.id("Account")).sendKeys(username);

        driver.findElement(By.id("PassWD")).clear();
        driver.findElement(By.id("PassWD")).sendKeys(password);

        List<WebElement> loginBtns=driver.findElements(By.cssSelector(".login .btn1"));
        if (loginBtns!=null && loginBtns.size()>0){
            loginBtns.get(0).click();
        }else {
            return null;
        }

        //同意书
        wait.until(new ExpectedCondition<WebElement>(){
            @Override
            public WebElement apply(WebDriver text) {
                return text.findElement(By.name("Submit2"));
            }
        });
          driver.findElement(By.name("Submit2")).click();

        //进入主页
        driver.findElement(By.id("toclose")).click();
        driver.switchTo().frame(0);
        wait.until(new ExpectedCondition<WebElement>(){
            @Override
            public WebElement apply(WebDriver text) {
                return text.findElement(By.partialLinkText("六合彩"));
            }
        });
        driver.findElement(By.partialLinkText("六合彩")).click();
        System.out.println(driver.getPageSource());
        wait.until(new ExpectedCondition<WebElement>(){
            @Override
            public WebElement apply(WebDriver text) {
                return text.findElement(By.name("mem_index"));
            }
        });
        driver.switchTo().frame(0);
        try {
            driver.switchTo().frame("mainFrame");
            System.out.println(driver.getPageSource());
            driver.switchTo().frame("mainFramesetIndexFrame");
            System.out.println(driver.getPageSource());
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return driver;
    }
}