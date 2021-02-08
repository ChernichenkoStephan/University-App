package org.university.DBLayer.Repositories.Interfaces;

import org.university.Model.Event;

import java.util.Collection;

public interface ClassDatabase extends Dao<Event, Integer> {
    Collection<Event> classesByTime();
}
