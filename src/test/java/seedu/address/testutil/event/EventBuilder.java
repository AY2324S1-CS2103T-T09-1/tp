package seedu.address.testutil.event;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.address.Address;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.name.Name;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.person.PersonBuilder;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {
    public static final String DEFAULT_EVENT_NAME = "NUS Career Fair 2023";
    public static final String DEFAULT_EVENT_DATE = "2023-12-12";
    public static final String DEFAULT_EVENT_ADDRESS = "311, Clementi Ave 2, #02-25";

    private Name name;
    private EventDate date;
    private Address address;
    private Set<Person> contacts;

    /**
     * Creates a {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        name = new Name(DEFAULT_EVENT_NAME);
        date = new EventDate(DEFAULT_EVENT_DATE);
        address = new Address(DEFAULT_EVENT_ADDRESS);
        contacts = new HashSet<>();
        contacts.add(new PersonBuilder().build());
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        date = eventToCopy.getDate();
        address = eventToCopy.getAddress();
        contacts = eventToCopy.getContacts();
    }

    /**
     * Sets the {@code Name} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code EventDate} of the {@code Event} that we are building.
     */
    public EventBuilder withEventDate(String eventDate) {
        this.date = new EventDate(eventDate);
        return this;
    }

    /**
     * Sets the {@code EventAddress} of the {@code Event} that we are building.
     */
    public EventBuilder withEventAddress(String eventAddress) {
        this.address = new Address(eventAddress);
        return this;
    }

    /**
     * Parses the {@code contacts} into a {@code Set<Person>} and set it to the {@code Event} that we are building.
     */
    public EventBuilder withEventContacts(Person ... contacts) {
        this.contacts = SampleDataUtil.getPersonSet(contacts);
        return this;
    }
    public Event build() {
        return new Event(name, date, address, contacts);
    }


}
