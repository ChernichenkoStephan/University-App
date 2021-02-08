package org.university.DBLayer.Repositories.Interfaces;

import org.university.Model.Subject;

import java.util.Collection;

public interface SubjectDatabase extends Dao<Subject, Integer> {
    Collection<Subject> getByName(String name);
    Integer getFullCourse();
}
