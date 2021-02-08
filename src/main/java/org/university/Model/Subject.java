package org.university.Model;

/*
CREATE TABLE SUBJECT (
  SUBJECT_ID           INT PRIMARY KEY     NOT NULL,
  NAME                             TEXT    NOT NULL,
  SHORT_NAME                       TEXT    NOT NULL,
  HOURS_AMOUNT                     INT     NOT NULL
);
 */

public class Subject {
    private Integer id;
    private String name;
    private String shortName;
    private Integer hoursAmount;

    public Subject(Integer id, String name, String shortName, Integer hoursAmount) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.hoursAmount = hoursAmount;
    }

    public Subject(String name, String shortName, Integer hoursAmount) {
        this.name = name;
        this.shortName = shortName;
        this.hoursAmount = hoursAmount;
    }

    public Subject(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", hoursAmount=" + hoursAmount +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getHoursAmount() {
        return hoursAmount;
    }

    public void setHoursAmount(Integer hoursAmount) {
        this.hoursAmount = hoursAmount;
    }
}
