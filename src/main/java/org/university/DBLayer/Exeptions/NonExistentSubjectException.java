package org.university.DBLayer.Exeptions;

public class NonExistentSubjectException extends NonExistentEntityException {
    private static final long serialVersionUID = 8633588908169766378L;

    public NonExistentSubjectException() {
        super("Subject does not exist");
    }
}
