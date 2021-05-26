package gradle.cucumber;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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
import java.util.logging.Logger;

public class UpdateStatusFeature {

    private SessionFactory sessionFactory;
    private EventAccessor eventAccessor;
    private EventHandler eventHandler;
    private ParticipantAccessor participantAccessor;
    private Participant participant;
    private Event event;
    private Long participantId;
    private Long eventId;
    private String participantName;
    private Date eventDate;
    private EventStatus eventStatus;



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
        event = eventHandler.createEvent(name, description, date, type);
        eventId = eventAccessor.persistEvent(event);
        eventDate = DateUtil.getInstance().convertToDate(date);

        Assertions.assertNotNull(event);
        Assertions.assertNotNull(eventId);
    }

    @When("The status of the event changes to cancel")
    public void the_status_of_the_event_changes_to_cancel() {
        event = eventHandler.updateEventStatus(event, EventStatus.CANCELED, eventDate);
        eventStatus = EventStatus.CANCELED;
        Assertions.assertNotNull(event);

    }

    @Then("Then i receive a notification with the event name {string} and the new status {string}")
    public void then_i_receive_a_notification_with_the_event_name_and_the_new_status(String name, String status) {
        Assertions.assertEquals(name, event.getName());
        Assertions.assertEquals(status, eventStatus.name());

    }


    //
    // U4 - AC2
    //







}
