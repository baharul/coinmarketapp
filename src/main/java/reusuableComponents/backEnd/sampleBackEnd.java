package reusuableComponents.backEnd;

import io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class sampleBackEnd {

    private static Response response;
    private static String jsonString;
    private static HashMap<String, Integer> currencyIds;
    private static String _id;


    public void getCoinIds() {

        try {
            Response res = given()
                    .header("Content-Type", "application/json")
                    .header("X-CMC_PRO_API_KEY", "fb253c1b-98ef-4a11-86f5-a26d35b4760e")
                    .when()
                    .get("https://pro-api.coinmarketcap.com/v1/cryptocurrency/map");

            Assert.assertEquals(res.getStatusCode(), 200);
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
            Assert.assertTrue(currencyIds.size() > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void convertToBolivianCurrency() {

        try {
            // Price of 1 BTC
            Response resOne = given()
                    .header("Content-Type", "application/json")
                    .header("X-CMC_PRO_API_KEY", "fb253c1b-98ef-4a11-86f5-a26d35b4760e")
                    .queryParam("amount", 1)
                    .queryParam("id", currencyIds.get("BTC"))
                    .queryParam("convert", "BOB")
                    .when()
                    .get("https://pro-api.coinmarketcap.com/v1/tools/price-conversion");
            Assert.assertEquals(200, resOne.getStatusCode());
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
            Assert.assertEquals(200, resTwo.getStatusCode());
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
            Assert.assertEquals(200, resThree.getStatusCode());
            JsonPath jsThree = new JsonPath(resThree.asString());
            float price_of_eth_into_bob_currency = jsThree.getFloat("data.quote.BOB.price");
            System.out.println("Price of 1 ETH coin in BOB currency: " + price_of_eth_into_bob_currency);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void retrieveInfoOfEthereum() {

        try {
            given()
                    .header("Content-Type", "application/json")
                    .header("X-CMC_PRO_API_KEY", "fb253c1b-98ef-4a11-86f5-a26d35b4760e")
                    .queryParam("id", 1027)
                    .when()
                    .get("https://pro-api.coinmarketcap.com/v1/cryptocurrency/info")
                    .then().assertThat().statusCode(200);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void verifyInfoOfEthereum() {

        try{
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
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    public void retrieveInfoOfFirstTenCurrencies() {
        try{
            given()
                    .header("Content-Type", "application/json")
                    .header("X-CMC_PRO_API_KEY", "fb253c1b-98ef-4a11-86f5-a26d35b4760e")
                    .queryParam("id", "1,2,3,4,5,6,7,8,9,10")
                    .when()
                    .get("https://pro-api.coinmarketcap.com/v1/cryptocurrency/info").then().assertThat().statusCode(200);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void checkMineableTagsAndCurrencyInfoOfFirstTenCurrencies() {

        try{
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
                    System.out.println("Currency " + name + " has the Mineable Tag associated with it! \nDescription as Follows: " + description);
                    System.out.println("------------------------------------------------------------------------------------------------------");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}


