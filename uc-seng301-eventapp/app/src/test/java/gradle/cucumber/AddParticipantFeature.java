package gradle.cucumber;

import java.util.Date;

import org.apache.logging.log4j.core.util.Assert;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Assertions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uc.seng301.eventapp.accessor.EventAccessor;
import uc.seng301.eventapp.accessor.ParticipantAccessor;
import uc.seng301.eventapp.handler.EventHandler;
import uc.seng301.eventapp.handler.EventHandlerImpl;
import uc.seng301.eventapp.model.Event;
import uc.seng301.eventapp.model.Participant;
import uc.seng301.eventapp.util.DateUtil;


public class AddParticipantFeature {

    private SessionFactory sessionFactory;
    private EventAccessor eventAccessor;
    private EventHandler eventHandler;
    private ParticipantAccessor participantAccessor;
    private Participant participant;
    private Event event;
    private Long participantId;
    private Long eventId;
    private String participantName;

    private Participant firstParticipant;
    private Participant secondParticipant;


    @Before
    public void setup() {
        Configuration configuration = new Configuration();
        configuration.configure();
        sessionFactory = configuration.buildSessionFactory();
        eventAccessor = new EventAccessor(sessionFactory, new ParticipantAccessor(sessionFactory));
        eventHandler = new EventHandlerImpl(eventAccessor);
        participantAccessor = new ParticipantAccessor(sessionFactory);
    }


    //
    // U3 - AC1
    //

    @Given("There is a participant with name {string}")
    public void there_is_a_participant_with_name(String name) {
        participant = new Participant(name);
        participantId = participantAccessor.persistParticipant(participant);
        Assertions.assertNotNull(participantAccessor.getParticipantByName(name));
    }

    @When("I add {string} to an event as a participant")
    public void i_add_to_an_event_as_a_participant(String name) {
        event = eventHandler.createEvent("Seng301","description", "01/06/2021", "course");
        eventId = eventAccessor.persistEvent(event);
        Assertions.assertNotNull(event);
        Assertions.assertNotNull(eventId);

        event.addParticipant(participantAccessor.getParticipantByName(name));
        eventId = eventAccessor.persistEventAndParticipants(event);
    }

    @Then("This event has a participant names {string} that is persisted when saving the event")
    public void this_event_has_a_participant_names_that_is_persisted_when_saving_the_event(String name) {

        Assertions.assertNotNull(event.getParticipants());
        participantName = name;
        Assertions.assertEquals(event.getParticipants().get(0).getName(), participantName);


    }


    //
    // U3 - AC2
    //

    @Given("There is no participant with name {string} in the event")
    public void there_is_no_participant_with_name_in_the_event(String name) {
        event = eventHandler.createEvent("Seng301","description", "01/06/2021", "course");
        eventId = eventAccessor.persistEvent(event);
        Assertions.assertNotNull(event);
        Assertions.assertNotNull(eventId);

        Assertions.assertNull(participantAccessor.getParticipantByName(name));

    }

    @When("I add a participant with name {string} to the event")
    public void i_add_a_participant_with_name_to_the_event(String name) {
        event.addParticipant(new Participant(name));
        eventId = eventAccessor.persistEventAndParticipants(event);
    }

    @Then("There is a participant with name {string} has been created")
    public void there_is_a_participant_with_name_has_been_created(String name) {

        Assertions.assertNotNull(participantAccessor.getParticipantByName(name));
    }

    //
    // U3 - AC3
    //

    @Given("There is no participant with name {string} and there is an empty participant")
    public void there_is_no_participant_with_name_and_there_is_an_empty_participant(String name) {
        Assertions.assertNull(participantAccessor.getParticipantByName(name));
        Assertions.assertNull(firstParticipant);
    }

    @When("I add one participant with name {string} and one empty participant")
    public void i_add_one_participant_with_name_and_one_empty_participant(String name) {
        secondParticipant = new Participant(name);
        Assertions.assertNotNull(secondParticipant);
        Assertions.assertNull(firstParticipant);
    }

    @Then("I expect an exception that disallow me to add any of those participants")
    public void i_expect_an_exception_that_disallow_me_to_add_any_of_those_participants() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> participantAccessor.persistParticipant(firstParticipant));
        Assertions.assertThrows(IllegalArgumentException.class, () -> participantAccessor.persistParticipant(secondParticipant));

    }


}
