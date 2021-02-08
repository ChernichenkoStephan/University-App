package org.university.DBLayer.Exeptions;

public class NonExistentTeacherException extends NonExistentEntityException{
    private static final long serialVersionUID = 8633588908169766369L;

    public NonExistentTeacherException() {
        super("Teacher does not exist");
    }
}


