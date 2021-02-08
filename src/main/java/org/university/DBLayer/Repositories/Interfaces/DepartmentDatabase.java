package org.university.DBLayer.Repositories.Interfaces;

import org.university.Model.Department;

import java.util.Collection;


public interface DepartmentDatabase extends Dao<Department, Integer> {
    Collection<Department> getByName(String name);
}
