package org.university.Model;

/*
CREATE TABLE STUDENTS_GROUP (
  GROUP_ID         INT PRIMARY KEY   NOT NULL,
  FACULTY                      TEXT  NOT NULL,
  STUDENTS_AMOUNT              INT   NOT NULL
);
 */

public class Group {
    private Integer id;
    private String faculty;
    private Integer amount;

    public Group(Integer id, String faculty, Integer amount) {
        this.id = id;
        this.faculty = faculty;
        this.amount = amount;
    }

    public Group(String faculty, Integer amount) {
        this.faculty = faculty;
        this.amount = amount;
    }

    public Group(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", faculty='" + faculty + '\'' +
                ", amount=" + amount +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
