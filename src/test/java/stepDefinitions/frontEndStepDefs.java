package stepDefinitions;


import frontEnd.sampleFrontEnd;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class frontEndStepDefs extends sampleFrontEnd{
    sampleFrontEnd web = new sampleFrontEnd();

    @Given("User launches coinmarketapp website")
    public void userLaunchesCoinmarketappWebsite() throws InterruptedException {
        web.setUp();
    }

    @Given("User is on the landing page")
    public void userIsOnTheLandingPage() throws InterruptedException {
        web.userIsOnHomePage();
    }

    @And("User logged with his credentials")
    public void userLoggedWithHisCredentials()  throws InterruptedException  {
        web.userLogin();
    }

    @When("User selects hundred results only in the filter")
    public void userSelectsResultsOnlyInTheFilter() throws InterruptedException {
        web.applyHundredRowsFilter();
    }

    @Then("User sees only hundred results in webtable")
    public void userSeesOnlyResultsInWebtable() throws InterruptedException {
        web.verifyHundredResultsDisplayed();
    }

    @And("User closes the browser")
    public void userClosesTheBrowser() throws InterruptedException {
        web.tearDown();
    }


    @Then("User add few coins to watchlist")
    public void userAddFewCoinsToWatchlist() throws InterruptedException {
        web.addToWatchList();
    }

    @And("User opens watchlist in new tab")
    public void userOpensWatchlistInNewTab() throws InterruptedException {
        web.openWatchListInNewTab();
    }


    @When("User selects any ranking under cryptocurrencies drpdown")
    public void userSelectsAnyRankingUnderCryptocurrenciesDrpdown() throws InterruptedException {
        web.selectAnyCryptoCurrency();
    }

    @And("User extracts data from the webtable from the parent tab")
    public void userExtractsDataFromTheWebtableFromTheParentTab() {
        web.extractDataFromWebTable();
    }

    @And("User applies random filters")
    public void userAppliesRandomFilters() throws InterruptedException {
        web.applyingRandomFilters();
    }

    @Then("User compares data after applying random filters")
    public void userComparesDataAfterApplyingRandomFilters() {
        web.compareDataAfterFilter();
    }


}
