import io.github.bonigarcia.wdm.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.interactions.*;
import org.openqa.selenium.support.ui.*;
import org.testng.*;
import org.testng.annotations.*;

import java.time.*;
import java.util.*;

public class FunctionalTest {
    WebDriver driver;

    @BeforeMethod
    public void driverInitialization() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();}

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    public void clickDiscoverBtn(WebDriver driver)
    {
        WebElement discoverBtn = driver.findElement(By.xpath("//*[text()='DISCOVER']"));
        discoverBtn.click();
    }

    public void waitForElementToBeClickable(WebDriver driver,WebElement element)
    {
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementToBeVisible(WebDriver driver,WebElement element)
    {
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public JavascriptExecutor getJavaScriptExecutor(WebDriver driver)
    {
  JavascriptExecutor js = (JavascriptExecutor)driver;
 return js;
    }

    public void scrollDownToElement(WebDriver driver,WebElement element)
    {
FunctionalTest ft = new FunctionalTest();
JavascriptExecutor js =ft.getJavaScriptExecutor(driver);
js.executeScript("arguments[0].scrollIntoView();",element);
    }


    @Test
    public void validateBannerTest() {
        String expectedBannerText = "Automated Testing Playground";
        driver.get("https://aquabottesting.com/index.html");
        WebElement textContainer = driver.findElement(By.cssSelector(".text-container h1"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(textContainer));
        String bannerText = textContainer.getText();
        System.out.println(bannerText);
        Assert.assertEquals(bannerText, expectedBannerText);
    }

    @Test
    public void btnTextChange() {
        driver.get("https://aquabottesting.com/index.html");
        WebElement discoverBtn = driver.findElement(By.xpath("//*[text()='DISCOVER']"));
        discoverBtn.click();
        WebElement typesOfBtnsTitle = driver.findElement(By.xpath("//*[text()='Types of Buttons']"));
        Assert.assertTrue(typesOfBtnsTitle.isDisplayed());
        WebElement changeBtn = driver.findElement(By.cssSelector("#text-change"));
        String btnText = changeBtn.getAttribute("value");
        Assert.assertEquals(btnText, "CHANGE TEXT");
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(changeBtn));
        changeBtn.click();
        String btnTextAfterClick = changeBtn.getAttribute("value");
       Assert.assertEquals(btnTextAfterClick, "BUTTON CLICKED");

    }

    @Test
    public void popupTest()
    {
        String color = "ppl";
        String confirmationMsgText = "All fields were validated!";
        driver.get("https://aquabottesting.com/index.html");
        WebElement discoverBtn = driver.findElement(By.xpath("//*[text()='DISCOVER']"));
        discoverBtn.click();
        WebElement popupBtn = driver.findElement(By.xpath("//a[text()='POP-UP']"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(popupBtn));
        popupBtn.click();
        WebElement popupWindow = driver.findElement(By.id("pop-up"));
        Assert.assertTrue(popupWindow.isDisplayed());
        driver.findElement(By.id("checkbox")).click();
        driver.findElement(By.xpath("//form[@id='popupForm']/div[2]/input")).sendKeys("Minari");
        driver.findElement(By.xpath("//form[@id='popupForm']/div[3]/input")).sendKeys("Warnakula");
        WebElement dropdown = driver.findElement(By.xpath("//select[@id='colors']"));
        Select select = new Select(dropdown);
        select.selectByValue(color);
        driver.findElement(By.cssSelector("button[id='validate']")).click();
WebElement confirmationMsge = driver.findElement(By.cssSelector("#pmsgSubmit"));
Assert.assertEquals(confirmationMsge.getText(),confirmationMsgText);
    }

@Test
    public void acceptAlert()
{
    driver.get("https://aquabottesting.com/index.html");
  FunctionalTest ft = new FunctionalTest();
  ft.clickDiscoverBtn(driver);
  WebElement alertBtn= driver.findElement(By.xpath("//*[text()='ALERT']"));
  ft.waitForElementToBeClickable(driver,alertBtn);
  alertBtn.click();
    Assert.assertEquals(driver.switchTo().alert().getText(),"You triggered an alert. Button was pressed.");
  driver.switchTo().alert().accept();
}

@Test
    public void declineAlert()
{
    driver.get("https://aquabottesting.com/index.html");
    FunctionalTest ft = new FunctionalTest();
    ft.clickDiscoverBtn(driver);
    WebElement alertBtn= driver.findElement(By.xpath("//*[text()='ALERT']"));
    ft.waitForElementToBeClickable(driver,alertBtn);
    alertBtn.click();
    driver.switchTo().alert().dismiss();
}

@Test
    public void openANewWindow() throws InterruptedException {
    driver.get("https://aquabottesting.com/index.html");
    FunctionalTest ft = new FunctionalTest();
    ft.clickDiscoverBtn(driver);
    WebElement newWindowBtn = driver.findElement(By.cssSelector("#new-window-btn"));
    ft.waitForElementToBeClickable(driver,newWindowBtn);
    newWindowBtn.click();
   Set<String> handles = driver.getWindowHandles();
    Iterator<String> it = handles.iterator();

  if(it.hasNext())
    {
        String parentWindow = it.next();
        String newWindow = it.next();
        driver.switchTo().window(newWindow);
    }
  Thread.sleep(2000);
    Assert.assertEquals(driver.getCurrentUrl(),"https://aquabottesting.com/new-window.html");
    String newWindowText = driver.findElement(By.tagName("h1")).getText();
    Assert.assertEquals(newWindowText,"You opened a new window.");
    driver.findElement(By.xpath("//a[text()='CLOSE']")).click();
}

@Test
    public void fillDemoForm() throws InterruptedException {
    String name = "De Silva";
    String email = "silva@gmail.com";
    String phone = "8906789034";
    String message = "This is my first message!";
    String dropdownOption = "Programming Languages";
    String toastMsge = "Message Failed!";
    driver.get("https://aquabottesting.com/index.html");
    FunctionalTest ft = new FunctionalTest();
    ft.clickDiscoverBtn(driver);
//    Thread.sleep(3000);
    WebElement linkToSection = driver.findElement(By.linkText("LINK TO A SECTION"));
//    ft.waitForElementToBeClickable(driver,linkToSection);
    linkToSection.click();
    WebElement demoForm = driver.findElement(By.xpath("//*[@id='contactForm']"));
    ft.waitForElementToBeVisible(driver,demoForm);
    Assert.assertTrue(demoForm.isDisplayed());
    WebElement nameInput = driver.findElement(By.id("cname"));
JavascriptExecutor js = ft.getJavaScriptExecutor(driver);
js.executeScript("window.scrollBy(0,-100)");
ft.waitForElementToBeVisible(driver,nameInput);
  nameInput.sendKeys(name);
driver.findElement(By.cssSelector("input[type='email']")).sendKeys(email);
driver.findElement(By.id("cphone")).sendKeys(phone);
driver.findElement(By.cssSelector(".radio-container:nth-of-type(2)")).click();
driver.findElement(By.xpath("//*[@id='cmessage']")).sendKeys(message);
WebElement dropDown = driver.findElement(By.cssSelector("[id='cselect']"));
Select select =new Select(dropDown);
select.selectByVisibleText(dropdownOption);
driver.findElement(By.id("cerror")).click();
driver.findElement(By.xpath("//*[text()='SUBMIT']")).click();
Assert.assertEquals(driver.findElement(By.id("cmsgSubmit")).getText(),toastMsge);
}

@Test
    public void linkToExternalSite() throws InterruptedException {
    driver.get("https://aquabottesting.com/index.html");
    FunctionalTest ft = new FunctionalTest();
    ft.clickDiscoverBtn(driver);
WebElement linkToExternalSite =  driver.findElement(By.linkText("LINK TO EXTERNAL SITE"));
   ft.waitForElementToBeClickable(driver,linkToExternalSite);
   linkToExternalSite.click();
    String parentWindow = driver.getWindowHandle();
   Set<String> windowHandles= driver.getWindowHandles();
  Iterator<String> it =  windowHandles.iterator();

while (it.hasNext())
    {
        String childWindow = it.next();
        if(!childWindow.equals(parentWindow))
        {
            driver.switchTo().window(childWindow);
        }
    }

  Assert.assertEquals(driver.getCurrentUrl(),"https://www.google.com/");

}

@Test
    public void openInCurrentWindow() throws InterruptedException {
    driver.get("https://aquabottesting.com/index.html");
    FunctionalTest ft = new FunctionalTest();
    ft.clickDiscoverBtn(driver);
    WebElement linkInCurrentWindow = driver.findElement(By.id("new-window-link"));
    waitForElementToBeClickable(driver,linkInCurrentWindow);
   // Thread.sleep(3000);
    linkInCurrentWindow.click();
    WebElement title = driver.findElement(By.xpath("//h1"));
    waitForElementToBeVisible(driver, title);
    Assert.assertEquals(title.getText(),"You opened a new window.");
    driver.findElement(By.xpath("//*[text()='GO BACK']")).click();
    WebElement formTitle = driver.findElement(By.xpath("//h2"));
    waitForElementToBeVisible(driver,formTitle);
    Assert.assertEquals(formTitle.getText(),"Demo Contact Form");
}

    public void getStateWiseBalance(WebDriver driver,String state)
{
    driver.get("https://aquabottesting.com/index.html");
    Actions a = new Actions(driver);
    WebElement dropdown = driver.findElement(By.xpath("//a[@id='navbarDropdown2']"));
   a.moveToElement(dropdown).build().perform();
   WebElement tableDropdown = driver.findElement(By.xpath("(//*[@class='dropdown-menu'])[1]"));
   waitForElementToBeVisible(driver,tableDropdown);
   driver.findElement(By.xpath("(//*[@class='dropdown-menu'])[1]/a[1]")).click();
   WebElement table = driver.findElement(By.cssSelector("#js-sort-table"));
   waitForElementToBeVisible(driver,table);
   Assert.assertTrue(table.isDisplayed());

    List<WebElement> states = driver.findElements(By.xpath("//tbody/tr/td[2]"));

    List<WebElement> balances = driver.findElements(By.xpath("//tbody/tr/td[5]"));
    double total=0.0;

    for(int i=0;i<states.size();i++)
    {
        if(states.get(i).getText().equalsIgnoreCase(state))
        {
            String balance =   balances.get(i).getText().replace('$',' ').trim();
//            System.out.println(balance);
            try {
               double balanceDble = Double.parseDouble(balance);
                total=total+balanceDble;
            } catch(NumberFormatException nfe)
            {
                System.out.println("Invalid number format: "+balance);
            }

        }
    }
   System.out.println("Total balance of "+state+": "+total);
}

@Test
    public void getCATotal()
{
    String state ="CA";
    FunctionalTest ft = new FunctionalTest();
    ft.getStateWiseBalance(driver,state);
}

@Test
    public void getNVTotal()
{
    String state ="NV";
    FunctionalTest ft = new FunctionalTest();
    ft.getStateWiseBalance(driver,state);
}

    @Test
    public void getORTotal()
    {
        String state ="OR";
        FunctionalTest ft = new FunctionalTest();
        ft.getStateWiseBalance(driver,state);
    }

    @Test
    public void getTotalPriceOfAvailableProducts()
    {
        driver.get("https://aquabottesting.com/index.html");
        Actions a = new Actions(driver);
        a.moveToElement(driver.findElement(By.cssSelector("#navbarDropdown2"))).build().perform();
        WebElement table2 = driver.findElement(By.xpath("(//*[@class='dropdown-item']/span)[2]"));
        waitForElementToBeClickable(driver,table2);
        table2.click();
        WebElement priceTable = driver.findElement(By.cssSelector("#pricing-table"));
FunctionalTest ft =new FunctionalTest();
ft.scrollDownToElement(driver,priceTable);
Assert.assertTrue(priceTable.isDisplayed());
List<WebElement> avalRows = driver.findElements(By.cssSelector("#pricing-table tbody tr td:nth-of-type(5) img"));
List<WebElement> prices = driver.findElements(By.cssSelector("#pricing-table tbody tr td:nth-of-type(4)"));
double totalPrice = 0.0;
for(int i=0;i<avalRows.size();i++)
{
if(avalRows.get(i).getAttribute("alt").equalsIgnoreCase("checkmark"))
{
    String price = prices.get(i).getText().replace('$', ' ').trim();
   totalPrice=totalPrice+ Double.parseDouble(price);
}
}
        System.out.println("Total price of all available products is: "+totalPrice);
    }

    @Test
    public void filterCustomers()
    {
        String filterBy = "Jod";
        driver.get("https://aquabottesting.com/index.html");
        Actions a = new Actions(driver);
        a.moveToElement(driver.findElement(By.cssSelector("#navbarDropdown2"))).build().perform();
        WebElement table2 = driver.findElement(By.xpath("(//*[@class='dropdown-item']/span)[2]"));
        waitForElementToBeClickable(driver,table2);
        table2.click();
        driver.findElement(By.id("filter-input")).sendKeys(filterBy);
        boolean flag = false;
        List<WebElement> customers =  driver.findElements(By.xpath("//*[@id='filter-table']/tbody/tr"));
         WebElement filteredCustomer=null;

        for(WebElement customer:customers)
        {
            if(!customer.getAttribute("style").equalsIgnoreCase("display: none;"))
            {
                filteredCustomer=customer;
            }
        }

        String cName =filteredCustomer.getText();
        if(cName.contains(filterBy))
        {
            flag = true;
        }
Assert.assertTrue(flag);

    }
}


