package org.university.DBLayer.Repositories.Interfaces;

import org.university.Model.Classroom;

import java.util.Collection;

public interface ClassroomDatabase extends Dao<Classroom, Integer> {
    Collection<Classroom> getLectures();
    Collection<Classroom> getPractices();
    Collection<Classroom> getLaboratories();
    Collection<Classroom> getComputers();
    Collection<Classroom> getSmall();
}
