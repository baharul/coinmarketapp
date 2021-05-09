package stepDefinitions;

import backEnd.sampleBackEnd;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

public class backEndStepDefinitions {
    sampleBackEnd api = new sampleBackEnd();

    @Given("User is able to retrieve ID's of given cryptocurrencies")
    public void userIsAbleToRetrieveIDSOfGivenCryptocurrencies() {
        api.getCoinIds();
    }

    @And("User is using those id for price conversion api")
    public void userIsUsingThoseIdForPriceConversionApi() {
        api.convertToBolivianCurrency();
    }

    @Given("User is able to retrieve Ethereum technical documentation")
    public void userIsAbleToRetrieveEthereumTechnicalDocumentation() {
        api.retrieveInfoOfEthereum();
    }

    @And("User is able to verify all given information in ethereum api")
    public void userIsAbleToVerifyAllGivenInformationInEthereumApi() {
        api.verifyInfoOfEthereum();
    }

    @Given("User is able to retrieve first ten currencies")
    public void userIsAbleToRetrieveFirstTenCurrencies() {
        api.retrieveInfoOfFirstTenCurrencies();
    }

    @And("User is able to verify all given information in cryptocurrency info api")
    public void userIsAbleToVerifyAllGivenInformationInCryptocurrencyInfoApi() {
        api.checkMineableTagsAndCurrencyInfoOfFirstTenCurrencies();
    }
}
