package gradle.cucumber;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.core.util.Assert;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Assertions;
import uc.seng301.eventapp.accessor.EventAccessor;
import uc.seng301.eventapp.accessor.ParticipantAccessor;
import uc.seng301.eventapp.handler.EventHandler;
import uc.seng301.eventapp.handler.EventHandlerImpl;
import uc.seng301.eventapp.model.Event;
import uc.seng301.eventapp.model.EventStatus;
import uc.seng301.eventapp.model.Participant;
import uc.seng301.eventapp.model.ScheduledEvent;
import uc.seng301.eventapp.util.DateUtil;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class UpdateStatusFeature {

    private SessionFactory sessionFactory;
    private EventAccessor eventAccessor;
    private EventHandler eventHandler;
    private ParticipantAccessor participantAccessor;
    private Participant participant;
    private Event event;
    private Long eventId;

    private Participant participant1;
    private Participant participant2;
    private Long participantId;


    public UpdateStatusFeature() {
    }

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
    // U4 - AC1
    //

    @Given("There is a scheduled event with name {string}, description {string}, type {string} and date {string}")
    public void there_is_a_scheduled_event_with_name_description_type_and_date(String name, String description, String type, String date) {
        event = eventHandler.createEvent(name,description, date, type);
        eventId = eventAccessor.persistEvent(event);
        Assertions.assertNotNull(event);
        Assertions.assertNotNull(eventId);
    }

    @Given("There is a participant with name {string} joins the event")
    public void there_is_a_participant_with_name_joins_the_event(String name) {
        participant = new Participant(name);
        participantId = participantAccessor.persistParticipant(participant);
        Assertions.assertNotNull(participantAccessor.getParticipantByName(name));

        event.addParticipant(participant);
        eventAccessor.persistEventAndParticipants(event);
        Assertions.assertNotNull(event.getParticipants());
    }

    @When("The status of the event changes to cancel")
    public void the_status_of_the_event_changes_to_cancel() {
        event = eventHandler.updateEventStatus(event, EventStatus.CANCELED);
    }

    @Then("Then Allen receive a notification with the event name {string} and the new status {string}")
    public void then_allen_receive_a_notification_with_the_event_name_and_the_new_status(String eventName, String status) {
        Assertions.assertTrue(participant.getNotify);
        Assertions.assertEquals(participant.outputEventName, eventName);
        Assertions.assertEquals(participant.outputStatus, status);
    }


    //
    // U4 - AC2
    //

    @Then("Printing a message containing participant's name {string}, the event name {string} and the new status {string}")
    public void printing_a_message_containing_participant_s_name_the_event_name_and_the_new_status(String name, String eventName, String status) {
        Assertions.assertTrue(participant.getNotify);
        Assertions.assertEquals(participant.getName(), name);
        Assertions.assertEquals(participant.outputEventName, eventName);
        Assertions.assertEquals(participant.outputStatus, status);
        Assertions.assertEquals(participant.notification, name + ", " + eventName + " has changed the status to " + status);
    }


    //
    // U4 - AC3
    //

    @Given("There are participants with name {string} and {string} join the event")
    public void there_are_participants_with_name_and_join_the_event(String name1, String name2) {
        participant1 = new Participant(name1);
        participantAccessor.persistParticipant(participant1);
        Assertions.assertNotNull(participantAccessor.getParticipantByName(name1));

        participant2 = new Participant(name2);
        participantAccessor.persistParticipant(participant2);
        Assertions.assertNotNull(participantAccessor.getParticipantByName(name2));

        event.addParticipant(participant1);
        event.addParticipant(participant2);
        eventAccessor.persistEventAndParticipants(event);
        Assertions.assertNotNull(event.getParticipants());
    }

    @Then("Remove {string} from the participants list")
    public void remove_from_the_participants_list(String name) {
        participant = participantAccessor.getParticipantByName(name);

        event.removeParticipant(participant);
        eventAccessor.persistEventAndParticipants(event);
        System.out.print(event.getParticipants());

        Assertions.assertFalse(event.getParticipants().contains(participant));
    }


    //
    // U4 - AC4
    //

    @When("The status of the event changes to archived")
    public void the_status_of_the_event_changes_to_archived() {
        event = eventHandler.updateEventStatus(event, EventStatus.ARCHIVED);
    }

    @Then("Remove all participants from the event")
    public void remove_all_participants_from_the_event() {
        Assertions.assertTrue(event.getParticipants().isEmpty());
    }


}
