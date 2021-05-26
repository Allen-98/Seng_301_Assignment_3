# SENG301 Assignment 1 (2021) - Student answers

**Xiaojie Wang, Student ID: 18928477**

## Task 1.b - Write acceptance tests for U3 - Add participants to events

### Feature file (Cucumber Scenarios)

**participant.feature**

### Java class implementing the acceptance tests

**AddParticipantFeature.java**

## Task 2 - Identify the patterns in the code

### Pattern 1

#### What pattern is it?

**State pattern**

#### What is its goal in the code?

**Allows an event to alter its behavior when its state changes**

#### Name of UML Class diagram attached:

**YOUR ANSWER**

#### Mapping to GoF pattern elements:

| GoF element | Code element |
| ----------- | ------------ |
|    Context  | EventHandler |
| Request() | updateEventStatus() |
| State | Event |
| ConcreteStateA | ArchivedEvent |
| ConcreteStateB | CanceledEvent |
| ConcreteStateC | PastEvent |
| ConcreteStateD | ScheduledEvent |
| handle() | cancel() |
| handle() | happen() |
| handle() | reschedule() |
| handle() | archive() |

### Pattern 2

#### What pattern is it?

**Factory method pattern**

#### What is its goal in the code?

**Defines an interface for creating an object but let subclasses decide which class to
instantiate**

#### Name of UML Class diagram attached:

**YOUR ANSWER**

#### Mapping to GoF pattern elements:

| GoF element | Code element |
| ----------- | ------------ |
| AbstractCreator |  EventHandler |
| ConcreteCreator | EventHandlerImpl |
| AbstractProduct | Event |
| ConcreteProduct | CanceledEvent |
| ConcreteProduct | ArchivedEvent |
| ConcreteProduct | PastEvent |
| ConcreteProduct | ScheduledEvent |
| FactoryMethod | createEvent() |

## Task 3 - Full UML Class diagram

### Name of file of full UML Class diagram attached

**YOUR ANSWER**

## Task 4 - Implement new feature

### What pattern fulfils the need for the feature?

**Observer pattern**

### What is its goal and why is it needed here?

**Defines a one-to-many dependency between events and participants so that when one event changes state all its participants are notified and updated automatically**


### Name of UML Class diagram attached:

**YOUR ANSWER**

### Mapping to GoF pattern elements:

| GoF element | Code element |
| ----------- | ------------ |
|             |              |

## Task 5 - BONUS - Acceptance tests for Task 4

### Feature file (Cucumber Scenarios)

**NAME OF FEATURE FILE**

### Java class implementing the acceptance tests

**NAME OF JAVA FILE**
