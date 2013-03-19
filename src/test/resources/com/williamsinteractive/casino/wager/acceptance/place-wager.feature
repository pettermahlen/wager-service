Feature: Game Service places a wager

  Scenario: happy case

    Given an empty live wagers repository

    And a money service that always succeeds

    When a place wager request for wager round id 78612 and wager id 675 arrives

    Then the response indicates "SUCCESS"

    And the live wagers repository contains a confirmed wager for round 78612 with id 675


  Scenario: duplicate wager id

    Given a live wager repository with the following wager data
    | wagerRoundId | wagerId |
    | 7652         | 89789   |

    When a place wager request for wager round id 78612 and wager id 675 arrives

    Then the response indicates "DUPLICATE_WAGER_ID"

  Scenario: money service fails - TODO: won't work as there's no real money service implementation

    Given an empty live wagers repository

    And a money service that always fails

    When a place wager request for wager round id 5523 and wager id 67822 arrives

    Then the response indicates "FAILURE"

    And the live wagers repository contains an unconfirmed wager for round 5523 with id 67822
