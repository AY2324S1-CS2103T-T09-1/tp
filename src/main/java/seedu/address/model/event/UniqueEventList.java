package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.name.Name;
import seedu.address.model.person.Person;

/**
 * A list of events that enforces uniqueness between its elements and does not allow nulls.
 * An event is considered unique by comparing using {@code Event#isSameEvent(Event)}. As such, adding and updating of
 * event uses Event#isSameEvent(Event) for equality so as to ensure that the event being added or updated is
 * unique in terms of identity in the UniqueEventList. However, the removal of a Event uses Event#equals(Object) so
 * as to ensure that the event with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Event#isSameEvent(Event)
 */
public class UniqueEventList implements Iterable<Event> {

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();
    private final ObservableList<Event> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent event as the given argument.
     */
    public boolean contains(Event toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEvent);
    }

    /**
     * Adds an Event to the list.
     * The person must not already exist in the list.
     */
    public void add(Event toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEventException();
        }
        internalList.add(toAdd);
    }

    /**
     * Returns the {@code Event} with given name from the list.
     * @param name The name of the desired {@code Event}.
     */
    public Event getByName(Name name) throws EventNotFoundException {
        Event toGet = null;
        for (int i = 0; i < internalList.size(); i++) {
            Event thisEvent = internalList.get(i);
            if (thisEvent.getName().equals(name)) {
                toGet = thisEvent;
            }
        }
        if (toGet == null) {
            throw new EventNotFoundException();
        } else {
            return toGet;
        }
    }

    /**
     * Removes the equivalent event from the list.
     * The event must exist in the list.
     */
    public void remove(Event toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EventNotFoundException();
        }
    }

    public void setEvent(Event targetEvent, Event editedEvent) {
        requireAllNonNull(targetEvent, editedEvent);

        int index = internalList.indexOf(targetEvent);
        if (index == -1) {
            throw new EventNotFoundException();
        }

        if (!targetEvent.isSameEvent(editedEvent) && contains(editedEvent)) {
            throw new DuplicateEventException();
        }

        internalList.set(index, editedEvent);
    }
    public void setEvents(UniqueEventList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code events}.
     * {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        requireAllNonNull(events);
        if (!areEventsUnique(events)) {
            throw new DuplicateEventException();
        }

        internalList.setAll(events);
    }

    /**
     * Replaces the event list to remove {@code person} from contact list.
     */
    public void updateContacts(Person person) {
        for (int i = 0; i < internalList.size(); i++) {
            Event curr = internalList.get(i);
            Set<Person> contactsList = curr.getContacts();
            Set<Person> updatedContactsList = new HashSet<>();
            if (contactsList.contains(person)) {
                for (Person p : contactsList) {
                    if (!p.equals(person)) {
                        updatedContactsList.add(p);
                    }
                }
            } else {
                updatedContactsList = contactsList;
            }
            Event updatedEvent = new Event(curr.getName(), curr.getDate(), curr.getAddress(), updatedContactsList);
            internalList.set(i, updatedEvent);
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Event> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Event> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueEventList)) {
            return false;
        }

        UniqueEventList otherUniqueEventList = (UniqueEventList) other;
        return internalList.equals(otherUniqueEventList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code events} contains only unique persons.
     */
    private boolean areEventsUnique(List<Event> events) {
        for (int i = 0; i < events.size() - 1; i++) {
            for (int j = i + 1; j < events.size(); j++) {
                if (events.get(i).isSameEvent(events.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

