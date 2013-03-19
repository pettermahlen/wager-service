Feature: Game Service indicates an outcome

  Scenario: happy case

    Given a live wager repository with the following wager data
      | wagerRoundId | wagerId | confirmed  |
      | 763          | 89789   | 2013-03-19 |
      | 763          | 89989   | 2013-03-19 |

    And a money service that always succeeds

    When an outcome request for wager round id 763 arrives

    Then the response indicates "SUCCESS"

    And the live wagers repository contains a confirmed outcome for round 763

    And the transaction logger has a completed wager round with id 763 and wagers:
    | wagerId |
    | 89789   |
    | 89989   |
