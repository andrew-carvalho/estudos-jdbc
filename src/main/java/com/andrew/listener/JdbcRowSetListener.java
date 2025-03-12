package com.andrew.listener;

import lombok.extern.log4j.Log4j2;

import javax.sql.RowSet;
import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import java.sql.SQLException;

@Log4j2
public class JdbcRowSetListener implements RowSetListener {

    @Override
    public void rowSetChanged(RowSetEvent event) {
        log.info("Row set changed");
    }

    @Override
    public void rowChanged(RowSetEvent event) {
        if (event.getSource() instanceof RowSet) {
            try {
                ((RowSet) event.getSource()).execute();
                log.info("Updated rows");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void cursorMoved(RowSetEvent event) {
        log.info("Cursor moved");
    }
}
