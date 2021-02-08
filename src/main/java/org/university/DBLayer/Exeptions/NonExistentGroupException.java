package org.university.DBLayer.Exeptions;

public class NonExistentGroupException extends NonExistentEntityException {
    private static final long serialVersionUID = 8633588908169766388L;

    public NonExistentGroupException() {
        super("Group does not exist");
    }
}
