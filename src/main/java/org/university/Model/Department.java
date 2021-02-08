package org.university.Model;

/*
CREATE TABLE DEPARTMENT (
  DEPARTMENT_ID    INT PRIMARY KEY   NOT NULL,
  NAME                         TEXT  NOT NULL,
  SHORT_NAME                   TEXT  NOT NULL,
  PLACES_AMOUNT                INT   NOT NULL
);
 */

public class Department {
    private Integer id;
    private String name;
    private String shortName;
    private Integer amount;

    public Department(Integer id, String name, String shortName, Integer amount) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.amount = amount;
    }

    public Department(String name, String shortName, Integer amount) {
        this.name = name;
        this.shortName = shortName;
        this.amount = amount;
    }

    public Department(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", amount=" + amount +
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
