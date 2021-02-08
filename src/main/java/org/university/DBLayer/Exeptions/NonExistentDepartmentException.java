package org.university.DBLayer.Exeptions;

public class NonExistentDepartmentException extends NonExistentEntityException{
        private static final long serialVersionUID = 8633588908169766368L;

        public NonExistentDepartmentException() {
            super("Department does not exist");
        }
}

