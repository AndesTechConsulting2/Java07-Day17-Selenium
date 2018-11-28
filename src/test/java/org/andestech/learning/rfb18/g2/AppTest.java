package org.andestech.learning.rfb18.g2;


import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

public class AppTest
{
    private WebDriver wd = null;
    private ChromeOptions option = null;

    @BeforeClass
    public void initData(){
    System.setProperty("webdriver.chrome.driver",
            "E:\\drivers\\selenium\\chromedriver.exe");
    System.out.println("+++ Class: " + this);

    String myProfile = "C:\\Users\\and\\AppData\\Local\\Google\\Chrome\\User Data";
    myProfile =    "C:\\Users\\and\\AppData\\Local\\Chromium\\User Data";

    option = new ChromeOptions();

       // option.setPageLoadStrategy(PageLoadStrategy.NONE);
      //  option.addArguments("user-data-dir=" + myProfile);
        option.setBinary("E:\\progs\\chrome-win\\chrome.exe");
    }

    @Test
    public void testCaseChrome01() throws InterruptedException {
        wd = new ChromeDriver(option);

        wd.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        wd.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);


        String yandexListItem=
                "body ul > li > div > div.organic__subtitle.typo.typo_type_greenurl > div.path.organic__path > a:nth-child(1)";

        // String yandexListItem2 = "body ul > li > div > div > div > a:nth-child(1)";

        String yandexListItem2 = "body ul > li > div > div > div > a:nth-child(1)";

        wd.get("https://yandex.ru");
        WebElement searchTextElement = wd.findElement(By.id("text"));
        //searchTextElement.sendKeys("банки кредит Омск");
        searchTextElement.sendKeys("банки вклады");
//        searchTextElement.clear();
//        searchTextElement.sendKeys("addd");

        searchTextElement.submit();

        //------------------------------
        int n = 1;
        ArrayList<Integer> bankRating = new ArrayList<>();
        ArrayList<String> siteFound = new ArrayList<>();


        List<WebElement> sites = null; //wd.findElements(By.cssSelector(yandexListItem2));

        int N = 30;

        for(int i =0; i<N; i++)
        {
           if(i!=0) wd.findElement(By.linkText("дальше")).click();
           sites = wd.findElements(By.cssSelector(yandexListItem));


            for(WebElement siteElement: sites)
            {

                WebElement data = null;
                try {
                    data = siteElement.findElement(By.tagName("b"));
                }
                catch (org.openqa.selenium.NoSuchElementException el)
                {
                    data = siteElement;
                }

                String siteInfo = data.getText();
                if(siteInfo.contains("raiffeisen.ru")) bankRating.add(n);

                String host = siteElement.getAttribute("host");
                String href = siteElement.getAttribute("href");
              //  if(host.contains("yandex")) continue;

                String outInfo = n +  " -> " + siteInfo + " -> " + href;

                System.out.println(outInfo);
                siteFound.add(outInfo);

                n++;
            }
            System.out.println("---------------------- " + i + " --------------------------------");
        }

        System.out.println("--------------------------");
        if(!bankRating.isEmpty()) System.out.println("Found!!" + bankRating);

      //  Thread.sleep(2000);



        assertTrue( true );
    }


    @AfterClass
    public void tearDown()
    {
      if(wd != null) wd.quit();
      System.out.println("--- Class: " + this);
    }

}
