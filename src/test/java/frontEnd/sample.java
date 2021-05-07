package frontEnd;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class sample {

    //div[starts-with(@class,'sc-1mxz8p6-10 iXekDn')]//div[contains(@class,'sc-16r8icm-0 tu1guj-0 hueEpF')] --> click Filter
    //div[starts-with(@class,'sc-16r8icm-0 sc-1f0grbq-0 jvQpLZ')]//button[contains(@class,'sc-1ejyco6-0 igBkAX')][1]
    //No of rows = //div//table[starts-with(@class,'cmc-table cmc-table___11lFC cmc-table-homepage___2_guh')]//tbody//tr
    //div//table[starts-with(@class,'cmc-table cmc-table___11lFC cmc-table-homepage___2_guh')]//tbody//tr[100]//td[2]/p

    public static WebDriver driver;
    public static WebDriverWait wait;
    public static int totalResults = 0;
    public String isStarred = null;
    public String parentTab = null;
    public String childTab = null;
    public ArrayList<String> headingList;
    public ArrayList<String> name;
    public ArrayList<String> price;
    public ArrayList<String> TwentyFourPercentage;
    public ArrayList<String> sevenDaysPercentage;
    public ArrayList<String> marketCap;
    public ArrayList<String> VolumeInTwentyFourHrs;
    public ArrayList<String> circulatingSupply;
    // Name = //table//tr[1]//td[3]//div[contains(@class,'sc-16r8icm-0 sc-1teo54s-1 lgwUsc')]/p
    // Price = //table//tr[1]//td[4]//div[contains(@class,'price___3rj7O')]/a
    // 24 % = //table//tr[1]//td[5]//span[contains(@class,'sc-1v2ivon-0 fJLBDK')]
    // 7d % = //table//tr[1]//td[6]//span[contains(@class,'sc-1v2ivon-0 fJLBDK')]
    // Market Cap = //table//tr[1]//td[7]/p
    // Volume(24h) = //table//tr[1]//td[8]//a/p
    // Circulating Supply = //table//tr[1]//td[9]//p
    @BeforeTest
    public void setUp() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        wait=new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test(priority = 0)
    public void verifyHundredResultsDisplayed() throws InterruptedException {

        // Open coinmarketapp in the browser
        driver.get("https://coinmarketcap.com/");
        driver.manage().window().maximize();

        // Close Cookie Banner
        driver.findElement(By.xpath("//div[@class='cmc-cookie-policy-banner__close']")).click();

        // Login as existing user
        driver.findElement(By.xpath("//button[@class='sc-1ejyco6-0 eQMwpO']")).click();
        driver.findElement(By.xpath("//input[@class='cxm5lu-0 bOgmnN']")).sendKeys("iambaharulislam@gmail.com");
        driver.findElement(By.xpath("//div[contains(@class,'last')]//input[@class='cxm5lu-0 bOgmnN']")).sendKeys("Pdf@2016");
        driver.findElement(By.xpath("//button[contains(@class,'sc-1ejyco6-0 iGZjcz')]")).click();

        // apply Filter for the first 100 results
        driver.findElement(By.xpath("//div[starts-with(@class,'sc-1mxz8p6-10 iXekDn')]//div[contains(@class,'sc-16r8icm-0 tu1guj-0 hueEpF')]")).click();
        driver.findElement(By.xpath("//div[starts-with(@class,'sc-16r8icm-0 sc-1f0grbq-0 jvQpLZ')]//button[contains(@class,'sc-1ejyco6-0 igBkAX')][1]")).click();

        //Verify 100 results are displayed in the web table
        totalResults = driver.findElements(By.xpath("//div//table[starts-with(@class,'cmc-table cmc-table___11lFC cmc-table-homepage___2_guh')]//tbody//tr")).size();
        System.out.println("First Check Total No Of Results Displayed after selecting the filter ==> " + totalResults);
        driver.findElement(By.xpath("//div[@class='sc-57oli2-0 dEqHl cmc-body-wrapper']")).click();

        // Scroll down to the bottom of the page and verify the last row is 100
        boolean hundredthRow = driver.findElements(By.xpath("//div//table[starts-with(@class,'cmc-table cmc-table___11lFC cmc-table-homepage___2_guh')]//tbody//tr[100]//td[2]/p")).size() > 0;
        System.out.println("ELEMENT PRESENT ============ " + hundredthRow);
        if (!hundredthRow) {
            System.out.println("$$$$$$$$$$$$$$$$$$ coming inside $$$$$$$$$$$$$$$$$$");
            WebElement lastRowTable = driver.findElement(By.xpath("//div[@class='seo-text__title']"));
            Coordinates cor = ((Locatable) lastRowTable).getCoordinates();
            cor.inViewPort();
        }
        Thread.sleep(2000);
        String takeHundred = driver.findElement(By.xpath("//div//table[starts-with(@class,'cmc-table cmc-table___11lFC cmc-table-homepage___2_guh')]//tbody//tr" + "[" + totalResults + "]" + "//td[2]/p")).getText();
        if (takeHundred.equalsIgnoreCase("100")) {
            System.out.println("Double Check Total No Of Results Displayed by selecting last row in the Table ==> " + takeHundred);
        }
    }

    @Test(priority = 1)
    public void addToWatchList() throws InterruptedException {
        int counter = 0;
        for (int i = 1; i <= 5; i++) {
            driver.findElement(By.xpath("//div//table[starts-with(@class,'cmc-table cmc-table___11lFC cmc-table-homepage___2_guh')]//tbody//tr" + "[" + i + "]" + "//td[1]/span")).click();
            if(driver.findElements(By.xpath("//button[@class='sc-1ejyco6-0 czBWYA']")).size() > 0 && counter == 0){
                driver.findElement(By.xpath("//button[@class='sc-1ejyco6-0 czBWYA']")).click();
                driver.findElement(By.xpath("//div//table[starts-with(@class,'cmc-table cmc-table___11lFC cmc-table-homepage___2_guh')]//tbody//tr" + "[" + i + "]" + "//td[1]/span")).click();
            }
            counter = counter + 1;
            WebElement starredicon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div//table[starts-with(@class,'cmc-table cmc-table___11lFC cmc-table-homepage___2_guh')]//tbody//tr" + "[" + i + "]" + "//td[1]/span[contains(@class,'is-starred')]")));
            boolean isStarred = driver.findElements(By.xpath("//div//table[starts-with(@class,'cmc-table cmc-table___11lFC cmc-table-homepage___2_guh')]//tbody//tr" + "[" + i + "]" + "//td[1]/span[contains(@class,'is-starred')]")).size() > 0;
            if(isStarred){
                String coinName = driver.findElement(By.xpath("//div//table//tbody//tr" + "[" + i + "]" + "//td[3]//div[@class='sc-16r8icm-0 sc-1teo54s-1 lgwUsc']/p")).getText();
                System.out.println(coinName + " added to WatchList!!");
            }else{
                counter = counter + 1;
                if(counter < 4){
                    driver.findElement(By.xpath("//div//table[starts-with(@class,'cmc-table cmc-table___11lFC cmc-table-homepage___2_guh')]//tbody//tr" + "[" + i + "]" + "//td[1]")).click();
                }
            }

        }

        parentTab = driver.getWindowHandle();
        WebDriver newTab = driver.switchTo().newWindow(WindowType.TAB);
        childTab = newTab.getWindowHandle();
        driver.navigate().to("https://coinmarketcap.com/watchlist/");
        driver.switchTo().window(parentTab);
        Thread.sleep(3000);
    }

    @Test(priority = 2)
    public void compareDataAfterApplyingFilter() {
        // Name = //table//tr[1]//td[3]//div[contains(@class,'sc-16r8icm-0 sc-1teo54s-1 lgwUsc')]/p
        // Price = //table//tr[1]//td[4]//div[contains(@class,'price___3rj7O')]/a
        // 24 % = //table//tr[1]//td[5]//span[contains(@class,'sc-1v2ivon-0 fJLBDK')]
        // 7d % = //table//tr[1]//td[6]//span[contains(@class,'sc-1v2ivon-0 fJLBDK')]
        // Market Cap = //table//tr[1]//td[7]/p
        // Volume(24h) = //table//tr[1]//td[8]//a/p
        // Circulating Supply = //table//tr[1]//td[9]//p
        /*
            public ArrayList<String> name;
            public ArrayList<String> price;
            public ArrayList<String> TwentyFourPercentage;
            public ArrayList<String> sevenDaysPercentage;
            public ArrayList<String> marketCap;
            public ArrayList<String> VolumeInTwentyFourHrs;
            public ArrayList<String> circulatingSupply;
         */

        WebElement tableHeading = driver.findElement(By.xpath("//div//table[starts-with(@class,'cmc-table cmc-table___11lFC cmc-table-homepage___2_guh')]//th//p"));
        headingList = new ArrayList<String>();
        for (int i = 3; i <= 9; i++) {
            String tblHeading = driver.findElement(By.xpath("//div//table[starts-with(@class,'cmc-table cmc-table___11lFC cmc-table-homepage___2_guh')]//th//p")).getText();
            headingList.add(tblHeading);
            System.out.println("Heading List ==> "+headingList.size());
        }

    }

    @AfterTest
    public void tearDown() {
        //driver.quit();
    }
}
