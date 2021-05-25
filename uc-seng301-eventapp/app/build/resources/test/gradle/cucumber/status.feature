Feature: U4 - Get notified when the status of an event changes

  Scenario: AC1 - Get a notification of the new status of the event which i am attending when it changes
    Given There is a scheduled event with name "SENG301 Asg 3", description "Let's learn some patterns", type "assignment" and date "07/06/2021"
    When The status of the event changes to cancel
    Then Then i receive a notification with the event name "SENG301 Asg 3" and the new status "CANCELED"