#Author: mr.amitksharma@gmail.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template

@DevTest
Feature: Test following features of Port Scan API
  Description : This feature file will focus on testing multiple features provided by PortScanAPI.
  a) Connectivity with REST API.
  b) HTTP Status Codes handled in REST implementation.
  c) Data validation implemented in REST
  d) Processing Logic implemented in REST as per business logic 
  e) Media Types handled in REST implementation.
  f) Error handling in API

  
  Background:
   Given REST-API end point is available as baseURL

  Scenario: Test Successful API connectivity
  	And REST-API end point is appended as baseURL + "?input=crowdstrike.com&isLatestScanRequired=true"
    When Connection is tried to reach out with given request
    Then Successful response is returned

   Scenario: Test data pull with status 200 for given API end point
   	And REST-API end point is appended as baseURL + "?input=yahoo.com&isLatestScanRequired=true"
    When Connection is tried to reach out with given request
    Then Response is returned
    And  status code is 200
    
      Scenario: Test data pull with status 200 for given API end point for first time request of a host
   	And REST-API end point is appended as baseURL + "?input=apple.com&isLatestScanRequired=true"
    When Connection is tried to reach out with given request
    Then Response is returned
    And  status code is 200
    And all ports are open in state.
    
    
    Scenario: Test data pull with status 200 for given API end point when expected data in JSON format.
    Given REST-API end point is appended as baseURL + "?input=google.com&isLatestScanRequired=true"
    When Connection is tried to reach out with given request
    Then Response is returned
    And  status code is 200
    And  data type is of application/json.
    
    
     Scenario: Test response status 404 for given API end point when request path is not correct.
    And REST-API end point is appended as baseURL + "/api/portlist/"
    When Connection is tried to reach out with given request
    Then Response is returned
    But  status code is 404
    
    
     Scenario: Test response status 400 for given API end point when host is incorrect
    Given REST-API end point is appended as baseURL + "?input=g.c&isLatestScanRequired=false"
    When Connection is tried to reach out with given request
    Then Response is returned
    But  status code is 400
    
     Scenario: Test response status 400 for given API end point when I.P address is incorrect
    Given REST-API end point is appended as baseURL + "?input=192.168&isLatestScanRequired=false"
    When Connection is tried to reach out with given request
    Then Response is returned
    But  status code is 400
    
     Scenario: Test data pull with history for given API end point for multiple requests of a host
   	And REST-API end point is appended as baseURL + "?input=apple.com&isLatestScanRequired=true"
    When Connection is tried to reach out with given request
    Then Response is returned
    And  status code is 200
    And results of the latest scan appear as well as the history of previous scans for that host

   
   Scenario Outline: Test response result with varying input and isLatestScanRequired parameters in request.
    And request is appended with <input> and <isLatestScanRequired>
    When Connection is tried to reach out with given request
    Then Response is returned
    And  status code is 200
Examples:
    | input   | isLatestScanRequired |
    | google.com | false |
    | akshima.in | false |