package org.university.Model;

/*
CREATE TABLE TEACHER (
  TEACHER_ID     INT PRIMARY KEY     NOT NULL,
  NAME                       TEXT    NOT NULL,
  LAST_NAME                  TEXT    NOT NULL,
  MIDDLE_NAME                TEXT    NOT NULL,
  POSITION                   TEXT    NOT NULL,
  EXPERIENCE                 INT     NOT NULL
);
 */

public class Teacher {
    private Integer id;
    private String name;
    private String lastName;
    private String middleName;
    private String position;
    private Integer experience;

    public Teacher(Integer id, String name, String lastName, String middleName, String position, Integer experience) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.middleName = middleName;
        this.position = position;
        this.experience = experience;
    }

    public Teacher(String name, String lastName, String middleName, String position, Integer experience) {
        this.name = name;
        this.lastName = lastName;
        this.middleName = middleName;
        this.position = position;
        this.experience = experience;
    }

    public Teacher(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", position='" + position + '\'' +
                ", experience=" + experience +
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }
}
