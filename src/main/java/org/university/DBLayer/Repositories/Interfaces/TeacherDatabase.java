package org.university.DBLayer.Repositories.Interfaces;

import org.university.Model.Teacher;

import java.util.Collection;
import java.util.Optional;

public interface TeacherDatabase extends Dao<Teacher, Integer> {
    Collection<Teacher> getByName(String name);
    Collection<Teacher> getMostExperienced();
    Integer getAmount();
    Optional<Teacher> getMaxExperienced();
    Boolean professorize();
}

