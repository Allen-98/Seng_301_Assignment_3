package uc.seng301.eventapp.handler;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uc.seng301.eventapp.accessor.EventAccessor;
import uc.seng301.eventapp.model.*;
import uc.seng301.eventapp.util.DateUtil;

/**
 * This class implements the handling of {@link Event} abstract entities.
 */
public class EventHandlerImpl implements EventHandler {

  private static final Logger LOGGER = LogManager.getLogger(EventHandlerImpl.class);
  private final EventAccessor eventAccessor;

  /**
   * Default constructor.
   */
  public EventHandlerImpl(EventAccessor eventAccessor) {
    this.eventAccessor = eventAccessor;
  }

  @Override
  public void addParticipants(Event event, List<Participant> participants) {
    if (null == event || null == participants) {
      throw new IllegalArgumentException("event or participant list is null");
    }

    for (Participant participant : participants) {
      if (null == participant) {
        throw new IllegalArgumentException("one of the participant is null");
      }
      if (event.getParticipants().stream().noneMatch(p -> p.getName().equals(participant.getName()))) {
        LOGGER.debug("added {}: {}", participant, event.addParticipant(participant));
      }
    }
    LOGGER.debug(event);
    LOGGER.debug(participants);
    LOGGER.debug(event.getParticipants());
  }

  @Override
  public void removeParticipants(Event event, Participant participant) {
    if (null == event || null == participant) {
      throw new IllegalArgumentException("event or participant list is null");
    }


  }

  @Override
  public Event createEvent(String name, String description, String date, String type) throws IllegalArgumentException {
    return createEvent(name, description, date, type, 0.0, null);
  }

  @Override
  public Event createEvent(String name, String description, String date, String type, Double cost)
      throws IllegalArgumentException {
    return createEvent(name, description, date, type, cost, null);
  }

  @Override
  public Event createEvent(String name, String description, String date, String type, Double cost, Location location)
      throws IllegalArgumentException {
    if (null == name || name.isBlank()) {
      throw new IllegalArgumentException("name cannot be null or blank");
    }

    if (null == description || description.isBlank()) {
      throw new IllegalArgumentException("description cannot be null or blank");
    }

    Date convertedDate = DateUtil.getInstance().convertToDate(date);
    Calendar nextYear = Calendar.getInstance();
    nextYear.add(Calendar.YEAR, 1);
    if (null == convertedDate || convertedDate.before(new Date()) || convertedDate.after(nextYear.getTime())) {
      throw new IllegalArgumentException("date '" + date + "' does not follow expected format "
          + DateUtil.getInstance().getDefaultDateFormat() + ", is in the past or later than one year");
    }

    if (null == type || type.isBlank()) {
      throw new IllegalArgumentException("type cannot be null or blank");
    }

    if (null == cost || cost < 0.0 || cost > 999) {
      throw new IllegalArgumentException("cost '" + cost + "' is not between 0 and 999 (inclusive)");
    }

    if (null != location && (null == location.getName() || location.getName().isEmpty())) {
      throw new IllegalArgumentException("location name in '" + location + "'' cannot be null or blank");
    }

    return new ScheduledEvent(name, description, convertedDate, eventAccessor.getEventTypeFromName(type), cost,
        location);
  }

  @Override
  public void refreshEvents() {
    // could have been written as a lambda expression, kept as a for-loop for
    // readability
    for (Event event : eventAccessor.getAllEventsWithStatus(EventStatus.SCHEDULED)) {
      if (event.getDate().after(DateUtil.getInstance().getCurrentDate())) {
        updateEventStatus(event, EventStatus.PAST, null);
      }
    }
  }

  @Override
  public Event updateEventStatus(Event event, EventStatus newStatus, Date date) throws IllegalStateException {
    if (null == event || null == newStatus) {
      throw new IllegalArgumentException("event or status passed were null.");
    }

    LOGGER.info("update status of event with id:{} to status '{}'", event.getEventId(), newStatus);
    Event updatedEvent = null;
    switch (newStatus) {
    case SCHEDULED:
      if (null == date) {
        throw new IllegalArgumentException("Date cannot be null to reschedule event.");
      }
      updatedEvent = event.reschedule(date);
      updatedEvent.setStatus(newStatus);
      break;
    case ARCHIVED:
      updatedEvent = event.archive();
      updatedEvent.setStatus(newStatus);
      break;
    case CANCELED:
      updatedEvent = event.cancel();
      updatedEvent.setStatus(newStatus);
      break;
    case PAST:
      updatedEvent = event.happen();
      updatedEvent.setStatus(newStatus);
      break;

    default:
      LOGGER.error("unimplemented status '{}' passed", newStatus);
      // returned event will be null and a nullpointer may be raised higher.
      // Since we've been passed an unknown status, we can't do anything and let the
      // caller potentially crash as it is outside of this class knowledge
      break;
    }
    return updatedEvent;
  }

  @Override
  public Event updateEventStatus(Event event, EventStatus newStatus){
    if (null == event || null == newStatus) {
      throw new IllegalArgumentException("event or status passed were null.");
    }

    LOGGER.info("update status of event with id:{} to status '{}'", event.getEventId(), newStatus);
    //Event updatedEvent = null;
    switch (newStatus) {
      case ARCHIVED:
        ArchivedEvent updatedEvent = event.archive();
        updatedEvent.setStatus(newStatus);
        return updatedEvent;
        //break;
      case CANCELED:
        CanceledEvent updatedEvent2 = event.cancel();
        updatedEvent2.setStatus(newStatus);
        return updatedEvent2;
        //break;
      case PAST:
        PastEvent updatedEvent3 = event.happen();
        updatedEvent3.setStatus(newStatus);
        return updatedEvent3;
        //break;

      default:
        LOGGER.error("unimplemented status '{}' passed", newStatus);
        // returned event will be null and a nullpointer may be raised higher.
        // Since we've been passed an unknown status, we can't do anything and let the
        // caller potentially crash as it is outside of this class knowledge
        break;
    }
    return null;

  }

}
