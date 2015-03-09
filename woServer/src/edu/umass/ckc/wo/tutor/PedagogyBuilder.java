package edu.umass.ckc.wo.tutor;

import edu.umass.ckc.wo.admin.PedagogyRetriever;
import edu.umass.ckc.wo.exc.AdminException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Responsible for building the selectors that do the work for a particular Pedagogy.
 * Copyright (c) University of Massachusetts
 * Written by: David Marshall
 * Date: Jan 2, 2008
 * Time: 11:36:09 AM
 */
public class PedagogyBuilder {


    /**
     * Given info about the student, his Pedagogy is found and augmented with three selector objects.
     * @return the Pedagogy (containing selector objects) for a given student
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public TutoringStrategy buildPedagogySelectors (Connection conn, int studId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, AdminException {
            TutoringStrategy ped = PedagogyRetriever.getTutoringStrategy(conn, studId);
//            ped.setProblemSelector((ProblemSelector) Class.forName(ped.getProblemSelectorClass()).newInstance());
//            ped.setHintSelector((HintSelector) Class.forName(ped.getHintSelectorClass()).newInstance());

            return ped;

    }


}
