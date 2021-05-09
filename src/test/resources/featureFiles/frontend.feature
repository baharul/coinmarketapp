
@frontend
Feature: This is FrontEnd feature Testing

  Background:
    Given User launches coinmarketapp website

  @fetcone
  Scenario: Verify only 100 rows of results displayed
    Given User is on the landing page
    And User logged with his credentials
    When User selects hundred results only in the filter
    Then User sees only hundred results in webtable
    And User closes the browser

  @fetctwo
  Scenario: Verify user is able to add cryptos to watchlist and extract data
    Given User is on the landing page
    And User logged with his credentials
    Then User add few coins to watchlist
    And User opens watchlist in new tab
    And User extracts data from the webtable from the parent tab
    And User closes the browser

  @fetcthree
  Scenario: Verify user is able to compare data of selecting random ranking vs data of applying random filters
    Given User is on the landing page
    And User logged with his credentials
    When User selects any ranking under cryptocurrencies drpdown
    And User extracts data from the webtable from the parent tab
    And User applies random filters
    Then User compares data after applying random filters
    And User closes the browser

