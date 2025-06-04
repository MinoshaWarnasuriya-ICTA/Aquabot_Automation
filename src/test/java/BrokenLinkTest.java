import io.github.bonigarcia.wdm.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

public class BrokenLinkTest {
    public static void main(String[] args) throws IOException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("https://aquabottesting.com/index.html");
        driver.manage().window().maximize();

        List<WebElement> links = driver.findElements(By.tagName("a"));

        for(WebElement link:links)
        {
           String url = link.getAttribute("href");
          HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
          conn.setRequestMethod("HEAD");
          conn.connect();
         int responseCode = conn.getResponseCode();

         if(responseCode>400){
             System.out.println("Link with the url: "+url+ " is broken with status code :"+responseCode);
         }

        }

    }
}
