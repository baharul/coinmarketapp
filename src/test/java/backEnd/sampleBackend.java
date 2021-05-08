package backEnd;

import io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.hamcrest.Matchers.*;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class sampleBackend {

    private static Response response;
    private static String jsonString;
    private static HashMap<String, Integer> currencyIds;
    private static String _id;

//    @BeforeClass
//    public void setup() {
//        RestAssured.baseURI = "https://pro-api.coinmarketcap.com/v1";
//        RestAssured.basePath = "/cryptocurrency/map";
//    }

    //@Test(priority = 0)
    public void getCoinIds() {
        Response res = given()
                .header("Content-Type", "application/json")
                .header("X-CMC_PRO_API_KEY", "fb253c1b-98ef-4a11-86f5-a26d35b4760e")
                .when()
                .get("https://pro-api.coinmarketcap.com/v1/cryptocurrency/map");

        JsonPath js = new JsonPath(res.asString());

        //Retrieve size of array
        int size = js.getInt("data.size()");
        System.out.println("Array size is: " + size);
        String symbol = null;
        currencyIds = new HashMap<String, Integer>();
        for (int i = 0; i <= size - 1; i++) {
            symbol = js.getString("data[" + i + "].symbol");
            switch (symbol) {
                case "BTC":
                    currencyIds.put("BTC", js.getInt("data[" + i + "].id"));
                    break;
                case "USDT":
                    currencyIds.put("USDT", js.getInt("data[" + i + "].id"));
                    break;
                case "ETH":
                    currencyIds.put("ETH", js.getInt("data[" + i + "].id"));
                    break;
                default:
                    break;
            }
        }
        System.out.println("Total Currency Ids: " + currencyIds.size());
    }

    // @Test(priority = 1)
    public void convertToBolivianCurrency() {

        // Price of 1 BTC
        Response resOne = given()
                .header("Content-Type", "application/json")
                .header("X-CMC_PRO_API_KEY", "fb253c1b-98ef-4a11-86f5-a26d35b4760e")
                .queryParam("amount", 1)
                .queryParam("id", currencyIds.get("BTC"))
                .queryParam("convert", "BOB")
                .when()
                .get("https://pro-api.coinmarketcap.com/v1/tools/price-conversion");
        JsonPath jsOne = new JsonPath(resOne.asString());
        float price_of_btc_into_bob_currency = jsOne.getFloat("data.quote.BOB.price");
        System.out.println("Price of 1 BTC coin in BOB currency: " + price_of_btc_into_bob_currency);

        Response resTwo = given()
                .header("Content-Type", "application/json")
                .header("X-CMC_PRO_API_KEY", "fb253c1b-98ef-4a11-86f5-a26d35b4760e")
                .queryParam("amount", 1)
                .queryParam("id", currencyIds.get("USDT"))
                .queryParam("convert", "BOB")
                .when()
                .get("https://pro-api.coinmarketcap.com/v1/tools/price-conversion");
        JsonPath jsTwo = new JsonPath(resTwo.asString());
        float price_of_usdt_into_bob_currency = jsTwo.getFloat("data.quote.BOB.price");
        System.out.println("Price of 1 USDT coin in BOB currency: " + price_of_usdt_into_bob_currency);

        Response resThree = given()
                .header("Content-Type", "application/json")
                .header("X-CMC_PRO_API_KEY", "fb253c1b-98ef-4a11-86f5-a26d35b4760e")
                .queryParam("amount", 1)
                .queryParam("id", currencyIds.get("ETH"))
                .queryParam("convert", "BOB")
                .when()
                .get("https://pro-api.coinmarketcap.com/v1/tools/price-conversion");
        JsonPath jsThree = new JsonPath(resThree.asString());
        float price_of_eth_into_bob_currency = jsThree.getFloat("data.quote.BOB.price");
        System.out.println("Price of 1 ETH coin in BOB currency: " + price_of_eth_into_bob_currency);
    }

    //@Test(priority = 3)
    public void retrieveInfoOfEthereum() {
        given()
                .header("Content-Type", "application/json")
                .header("X-CMC_PRO_API_KEY", "fb253c1b-98ef-4a11-86f5-a26d35b4760e")
                .queryParam("id", 1027)
                .when()
                .get("https://pro-api.coinmarketcap.com/v1/cryptocurrency/info")
                .then()
                .body("data.1027.logo", equalTo("https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png"))
                .body("data.1027.symbol", equalTo("ETH"))
                .body("data.1027.tags[0]", equalTo("mineable"))
                .body("data.1027.platform", isEmptyOrNullString())
                .body("data.1027.date_added", equalTo("2015-08-07T00:00:00.000Z"))
                .body("data.1027.urls.technical_doc[0]", equalTo("https://github.com/ethereum/wiki/wiki/White-Paper"));

    }

    @Test(priority = 3)
    public void getMineableTagsAndCurrencyInfoOfFirstTenCurrencies() {
        Response res = given()
                .header("Content-Type", "application/json")
                .header("X-CMC_PRO_API_KEY", "fb253c1b-98ef-4a11-86f5-a26d35b4760e")
                .queryParam("id", "1,2,3,4,5,6,7,8,9,10")
                .when()
                .get("https://pro-api.coinmarketcap.com/v1/cryptocurrency/info");

        JsonPath js = new JsonPath(res.asString());
        int size = js.getInt("data.size()");
        System.out.println("Array size is: " + size);

        for (int i = 1; i <= size; i++) {
            String name = js.getString("data." + i + ".name");
            String hasMineableTag = js.getString("data." + i + ".tags[0]");
            String description = js.getString("data." + i + ".description");
            if (hasMineableTag != null && !hasMineableTag.trim().isEmpty() && hasMineableTag.equalsIgnoreCase("mineable")) {
                System.out.println("Currency " + name + " has the Mineable Tag associated with it! \nDescription as Follows: "+description);
                System.out.println("------------------------------------------------------------------------------------------------------");
            }
        }
    }

}


