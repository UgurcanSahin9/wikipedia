import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class wikipedia {
    private String baseURL="https://en.wikipedia.org/wiki/Main_Page";
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        return driver;
    }

    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    @Test
    public void t1(){
        getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        getDriver().get(baseURL);
        List<WebElement> links=getDriver().findElements(By.tagName("a"));
        List<String> linksURL=new ArrayList<>();
        Set<String> visitedURL=new HashSet<>();
        links.forEach(each->linksURL.add(each.getAttribute("href")));
        Set<String> result=new HashSet<>();
        int n=9;


        for (int i=0;i<n;i++) {
            if(linksURL.get(i)!=null){
                List<String> eachPageLinks=new ArrayList<>();
                if(linksURL.get(i).contains("wikipedia.org")){
                    if(!visitedURL.contains(linksURL.get(i))){
                        getDriver().get(linksURL.get(i));
                        visitedURL.add(linksURL.get(i));
                        List<WebElement> eachPageElements=getDriver().findElements(By.tagName("a"));
                        eachPageElements.forEach(l->eachPageLinks.add(l.getAttribute("href")));
                    }

                }
                eachPageLinks.forEach(k->result.add(k));
                result.add(linksURL.get(i));

            }else{
                i++;
                n++;
            }

        }


        result.forEach(System.out::println);
    }
}
