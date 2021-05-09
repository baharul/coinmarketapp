@backend
Feature: This is Backend API feature Testing

  @betcone
  Scenario: Verify user is able to retrieve ID's of BTC,USDT and ETH and use it for price conversion Api
    Given User is able to retrieve ID's of given cryptocurrencies
    And User is using those id for price conversion api

  @betctwo
  Scenario: Verify user is able to retrieve Ethereum technical documentation and respective information
    Given User is able to retrieve Ethereum technical documentation
    And User is able to verify all given information in ethereum api

  @betcthree
  Scenario:  Verify user is able to retrieve first 10 currencies and check mineable tags along with correct cryptocurrencies
    Given User is able to retrieve first ten currencies
    And User is able to verify all given information in cryptocurrency info api

