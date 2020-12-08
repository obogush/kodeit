@All
Feature: Validate title and other elements inside the practice page

  Background:
    Given I am on the homepage
    When I open Practice page

  @Title
  Scenario: Validate title of the page
    Then I test the website title
    Then I validate title is correct
    Then I take a screenshot of the page
#    Then I close the browser windows

  @Title-fail
  Scenario: Fail on purpose for the title of the page
    Then I test the website title
    Then I validate title is not correct
    Then I take a screenshot of the page


  @bmw
  Scenario: Testing the vehicle radio buttons if they are enabled
    When I click on BMW button
    Then I check if it's enabled
    Then I take a screenshot of the page
#    Then I close the browser windows

  @honda
  Scenario: Test dropdown select options
    When I select honda from the list of options
    Then I take a screenshot of the page
    Then I validate "honda" is selected
#    Then I close the browser windows

  @apple-orange
  Scenario: Test multi select and click two options and validate text
    When I select on "apple" and "orange"
    Then I take a screenshot of the page
    Then I get text of the options and validate both
#      Then I close the browser windows

  @multi-select
  Scenario: Select all check boxes and take screenshot
    When I select options <honda> and <bmw> and <benz> and validate
    Then I take a screenshot of the page
#    Then I close the browser windows


  Scenario: Alert test
    When I click on "alert button"
    Then I dismiss and handle the problem
    Then I take a screenshot of the page
#      Then I close the browser windows


  Scenario: Read from excel file and send it to input box
    When I create excel file with this name
    Then I write a message in sheet
    Then I enter a message to the input box and click confirm
    Then I validate text is entered
    Then I close the browser windows






