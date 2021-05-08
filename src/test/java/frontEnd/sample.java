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
    public ArrayList<String> name = new ArrayList<String>();
    ;
    public ArrayList<String> price = new ArrayList<String>();
    ;
    public ArrayList<String> TwentyFourPercentage = new ArrayList<String>();
    ;
    public ArrayList<String> sevenDaysPercentage = new ArrayList<String>();
    ;
    public ArrayList<String> marketCap = new ArrayList<String>();
    ;
    public ArrayList<String> VolumeInTwentyFourHrs = new ArrayList<String>();
    ;
    public ArrayList<String> circulatingSupply = new ArrayList<String>();
    ;


    @BeforeTest
    public void setUp() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
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
        Thread.sleep(2000);
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
            if (driver.findElements(By.xpath("//button[@class='sc-1ejyco6-0 czBWYA']")).size() > 0 && counter == 0) {
                driver.findElement(By.xpath("//button[@class='sc-1ejyco6-0 czBWYA']")).click();
                driver.findElement(By.xpath("//div//table[starts-with(@class,'cmc-table cmc-table___11lFC cmc-table-homepage___2_guh')]//tbody//tr" + "[" + i + "]" + "//td[1]/span")).click();
            }
            counter = counter + 1;
            WebElement starredicon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div//table[starts-with(@class,'cmc-table cmc-table___11lFC cmc-table-homepage___2_guh')]//tbody//tr" + "[" + i + "]" + "//td[1]/span[contains(@class,'is-starred')]")));
            boolean isStarred = driver.findElements(By.xpath("//div//table[starts-with(@class,'cmc-table cmc-table___11lFC cmc-table-homepage___2_guh')]//tbody//tr" + "[" + i + "]" + "//td[1]/span[contains(@class,'is-starred')]")).size() > 0;
            if (isStarred) {
                String coinName = driver.findElement(By.xpath("//div//table//tbody//tr" + "[" + i + "]" + "//td[3]//div[@class='sc-16r8icm-0 sc-1teo54s-1 lgwUsc']/p")).getText();
                System.out.println(coinName + " added to WatchList!!");
            } else {
                counter = counter + 1;
                if (counter < 4) {
                    driver.findElement(By.xpath("//div//table[starts-with(@class,'cmc-table cmc-table___11lFC cmc-table-homepage___2_guh')]//tbody//tr" + "[" + i + "]" + "//td[1]")).click();
                }
            }

        }

        parentTab = driver.getWindowHandle();
        WebDriver newTab = driver.switchTo().newWindow(WindowType.TAB);
        childTab = newTab.getWindowHandle();
        driver.navigate().to("https://coinmarketcap.com/watchlist/");
        Thread.sleep(3000);
        driver.switchTo().window(parentTab);
        Thread.sleep(3000);
    }

    @Test(priority = 2)
    public void extractDataFromWebTable() {

        WebElement tableHeading = driver.findElement(By.xpath("//div//table[starts-with(@class,'cmc-table cmc-table___11lFC cmc-table-homepage___2_guh')]//th//p"));
        headingList = new ArrayList<String>();
        for (int i = 3; i <= 9; i++) {
            String tblHeading = driver.findElement(By.xpath("//div//table[starts-with(@class,'cmc-table cmc-table___11lFC cmc-table-homepage___2_guh')]//th//p")).getText();
            headingList.add(tblHeading);
            System.out.println("Heading List ==> " + headingList.size());
        }

        for (int j = 1; j <= totalResults; j++) {

            WebElement _name_ = driver.findElement(By.xpath("//table//tbody//tr" + "[" + j + "]" + "//td[3]//div[contains(@class,'sc-16r8icm-0')]/p"));
            Coordinates cor = ((Locatable) _name_).getCoordinates();
            cor.inViewPort();
            WebElement _price_ = driver.findElement(By.xpath("//table//tbody//tr" + "[" + j + "]" + "//td[4]//div[contains(@class,'price___3rj7O')]/a"));
            WebElement _twentyFourPercentage_ = driver.findElement(By.xpath("//table//tbody//tr" + "[" + j + "]" + "//td[5]//span[contains(@class,'sc-1v2ivon-0')]"));
            WebElement _sevenDaysPercentage_ = driver.findElement(By.xpath("//table//tbody//tr" + "[" + j + "]" + "//td[6]//span[contains(@class,'sc-1v2ivon-0')]"));
            WebElement _marketCap_ = driver.findElement(By.xpath("//table//tbody//tr" + "[" + j + "]" + "//td[7]/p"));
            WebElement _volumenInTwentyFourHrs_ = driver.findElement(By.xpath("//table//tbody//tr" + "[" + j + "]" + "//td[8]//a/p"));
            WebElement _circulatingSupply_ = driver.findElement(By.xpath("//table//tbody//tr" + "[" + j + "]" + "//td[9]//p"));


            String _name = _name_.getText();
            String _price = _price_.getText();
            String _twentyFourPercentage = _twentyFourPercentage_.getText();
            String _sevenDaysPercentage = _sevenDaysPercentage_.getText();
            String _marketCap = _marketCap_.getText();
            String _volumenInTwentyFourHrs = _volumenInTwentyFourHrs_.getText();
            String _circulatingSupply = _circulatingSupply_.getText();

            name.add(_name);
            price.add(_price);
            TwentyFourPercentage.add(_twentyFourPercentage);
            sevenDaysPercentage.add(_sevenDaysPercentage);
            marketCap.add(_marketCap);
            VolumeInTwentyFourHrs.add(_volumenInTwentyFourHrs);
            circulatingSupply.add(_circulatingSupply);
            System.out.println(name.size() + " -- " + _name + " -- " + _price + " -- " + _twentyFourPercentage + " -- " + _sevenDaysPercentage + " -- " + _marketCap + " -- " + _volumenInTwentyFourHrs + " -- " + _circulatingSupply);
        }

    }

    @Test(priority = 3)
    public void compareDataAfterFilter() throws InterruptedException {
        WebElement filterBtn = driver.findElement(By.xpath("//div[@class='sc-1mxz8p6-8 gcSysL']//button[contains(@class,'table-control-filter')]"));
        Coordinates cor = ((Locatable) filterBtn).getCoordinates();
        cor.inViewPort();


        Actions action = new Actions(driver);
        WebElement cryptoCurrencyMenuOption = driver.findElement(By.xpath("//ul[@class='sc-1evth2q-1 hpzJjv']//li[1]"));
        action.moveToElement(cryptoCurrencyMenuOption).build().perform();
        Thread.sleep(15000);
        WebElement subMenuOption = driver.findElement(By.xpath("//div[@class='tippy-box']//div[starts-with(@class,'sc-134')]//h6"));
        action.moveToElement(subMenuOption).build().perform();
        Thread.sleep(5000);

        WebElement logo = driver.findElement(By.xpath("//div[@class='sc-7i7lua-0 iuycCs cmc-logo cmc-logo--size-large']"));
        action.moveToElement(logo).build().perform();
        Thread.sleep(5000);


        driver.findElement(By.xpath("//h1[@class='sc-1q9q90x-0 dlDcED']")).click();
        filterBtn.click();
        WebElement addFilterBtn = driver.findElement(By.xpath("//ul[@class='container___QEYqH']//li[5]/button"));
        addFilterBtn.click();


        int totalDrpDwn = driver.findElements(By.xpath("//div[@class='sc-16r8icm-0 cUoQSu filter-area']//div[@class='szoamt-0 buxHoi']/button")).size();
        System.out.println("DrpDwn Size ===> " + totalDrpDwn);

        /*
             Applying filters for
             All Cryptocurrencies as "coin"
             which is having Price as "0$-1$" ,
             Volume as "> 10B",
             Circulating Supply as "> 10B"
             in which result should show only "DOGECOIN"
         */

        for (int drpDwn = 1; drpDwn <= totalDrpDwn; drpDwn++) {
            WebElement _drpDwn = driver.findElement(By.xpath("//div[@class='sc-16r8icm-0 cUoQSu filter-area']//div[@class='szoamt-0 buxHoi']" + "[" + drpDwn + "]" + "/button"));
            String drpDwnName = _drpDwn.getText();

            switch (drpDwnName) {
                case "All Cryptocurrencies":
                    _drpDwn.click();
                    driver.findElement(By.xpath("//div[@data-qa-id='range-filter-crypto']//div[2]//button[contains(@class,'cmc-option-button')]")).click();
                    break;
                case "Price":
                case "Circulating Supply":
                case "Volume":
                    _drpDwn.click();
                    driver.findElement(By.xpath("//div[@class='cmc-filter-presets']/button[1]")).click();
                    driver.findElement(By.xpath("//button[@data-qa-id='filter-dd-button-apply']")).click();
                    break;
                default:
                    break;
            }
    }

        driver.findElement(By.xpath("//button[contains(@class,'sc-1ejyco6-0 dgwIZo cmc-filter-button')]")).click();


        String _nameAfterFilter_ = driver.findElement(By.xpath("//table//tbody//tr[1]//td[3]//div[contains(@class,'sc-16r8icm-0')]/p")).getText();
        System.out.println("First Coin displayed after filter ==> "+ _nameAfterFilter_);

        if(name.contains(_nameAfterFilter_)){
            System.out.println("Hurray, As Expected DogeCoin Has filtered Out and results were compared successfully!!!!!!!!!!!!!!!!");
        }


    }

    @AfterTest
    public void tearDown() throws InterruptedException {
        driver.quit();
    }
}
