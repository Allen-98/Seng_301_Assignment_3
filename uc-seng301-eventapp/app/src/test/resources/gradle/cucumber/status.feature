Feature: U4 - Get notified when the status of an event changes

  Scenario: AC1 - Get a notification of the new status of the event which i am attending when it changes
    Given There is a scheduled event with name "SENG301 Asg 3", description "Let's learn some patterns", type "assignment" and date "07/06/2021"
    When The status of the event changes to cancel
    Then Then i receive a notification with the event name "SENG301 Asg 3" and the new status "CANCELED"


  Scenario: AC2 - Print a message containing my name, the event name and the new status when being notified of a status change
    Given There is a scheduled event with name "SENG301 Asg 3", description "Let's learn some patterns", type "assignment" and date "07/06/2021"
    And There is a participant with name "Allen" joins the event
    When The status of the event changes to cancel
    Then Printing a message containing participant's name "Allen", the event name "SENG301 Asg 3" and the new status "CANCELED"


  Scenario: AC3 - Remove myself from the participants list of the event when receiving the notification
    Given There is a scheduled event with name "SENG301 Asg 3", description "Let's learn some patterns", type "assignment" and date "07/06/2021"
    And There are participants with name "Allen" and "Alex" join the event
    When The status of the event changes to cancel
    Then Remove "Allen" from the participants list


  Scenario: AC4 - Remove all participants from the event when it is "ARCHIVED"
    Given There is a scheduled event with name "SENG301 Asg 3", description "Let's learn some patterns", type "assignment" and date "07/06/2021"
    And There are participants with name "Allen" and "Alex" join the event
    When The status of the event changes to archived
    Then Remove all participants from the event