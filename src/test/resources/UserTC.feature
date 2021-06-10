Feature: Title of your feature

  Scenario: User is able to access 
    Given A list of Users are available
    When Status Code should be sucess
    Then All users must have a name, username, and email.
    And Their Email must be valid.
    And Their Company catchphrase must have less than characters.
