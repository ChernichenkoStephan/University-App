package org.university.View;

import org.university.Model.Teacher;

import javax.swing.*;
import java.util.Collection;

public class TableView extends View {

    protected JScrollPane table;
    protected Collection data;

    public TableView(Collection data) {
        super();
        this.data = data;
    }

    public void refreshTable(Collection data) {}

    @Override
    protected void prepareGUI() {
        super.prepareGUI();
    }
}
