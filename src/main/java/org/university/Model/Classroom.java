package org.university.Model;

/*
CREATE TABLE CLASSROOM (
  CLASSROOM_ID         INT PRIMARY KEY   NOT NULL,
  BUILDING                         INT   NOT NULL,
  BUILDING_FLOOR                   INT   NOT NULL,
  TYPE                             TEXT  NOT NULL,
  CAPACITY                         INT   NOT NULL
);
 */

public class Classroom {
    private Integer id;
    private Integer building;
    private Integer floor;
    private String type;
    private Integer capacity;

    public Classroom(Integer id, Integer building, Integer floor, String type, Integer capacity) {
        this.id = id;
        this.building = building;
        this.floor = floor;
        this.type = type;
        this.capacity = capacity;
    }

    public Classroom(Integer building, Integer floor, String type, Integer capacity) {
        this.building = building;
        this.floor = floor;
        this.type = type;
        this.capacity = capacity;
    }

    public Classroom(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "id=" + id +
                ", building=" + building +
                ", floor=" + floor +
                ", type='" + type + '\'' +
                ", capacity=" + capacity +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBuilding() {
        return building;
    }

    public void setBuilding(Integer building) {
        this.building = building;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
