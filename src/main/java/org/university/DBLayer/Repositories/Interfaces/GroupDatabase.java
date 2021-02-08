package org.university.DBLayer.Repositories.Interfaces;

import org.university.Model.Group;

import java.util.Collection;

public interface GroupDatabase extends Dao<Group, Integer> {
    Collection<Group> getByFaculty(String faculty);
}
