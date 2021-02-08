package org.university.DBLayer.Exeptions;

public class NonExistentClassroomException extends NonExistentEntityException {
    private static final long serialVersionUID = 8633588908169766368L;

    public NonExistentClassroomException() {
        super("Classroom does not exist");
    }
}
