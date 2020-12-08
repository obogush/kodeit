Feature: test 2


  Background:
    Given I am on the homepage
    When I open Practice page

  Scenario: Create a random file name using java faker and random class
    When I create a file name using <funnyName> and <number>

  Scenario Outline: test
    When I create excel file with <name>
    Then I enter <message> to the input box and click confirm
    Then I validate text is entered
    Then I close the browser windows
    Examples:
      | name         | message                |
      | "file2.xlsx" | "Let's kode it rocks!" |


  Scenario Outline: Scenario outline test
    When I create excel file with <name>
    Then I write <message> in <sheet>
    Then I enter <message> to the input box and click confirm
    Then I validate text is entered
#    Then I close the browser windows
    Examples:
      | name         | message                | sheet   |
      | "file3.xlsx" | "Let's kode it rocks!" | "sheet" |