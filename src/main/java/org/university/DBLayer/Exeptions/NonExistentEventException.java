package org.university.DBLayer.Exeptions;

public class NonExistentEventException extends NonExistentEntityException {
    private static final long serialVersionUID = 8633588908169766399L;

    public NonExistentEventException() {
        super("Event does not exist");
    }
}
