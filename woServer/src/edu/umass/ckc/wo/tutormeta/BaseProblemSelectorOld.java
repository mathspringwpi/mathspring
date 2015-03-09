package edu.umass.ckc.wo.tutormeta;

import edu.umass.ckc.wo.content.Problem;
import edu.umass.ckc.wo.db.DbProblem;
import edu.umass.ckc.wo.smgr.SessionManager;
import edu.umass.ckc.wo.smgr.StudentState;
import edu.umass.ckc.wo.interventions.SelectProblemSpecs;
import edu.umass.ckc.wo.exc.DeveloperException;
import edu.umass.ckc.wo.cache.ProblemMgr;
import edu.umass.ckc.wo.event.tutorhut.NextProblemEvent;
import edu.umass.ckc.wo.tutor.probSel.PedagogicalModelParameters;
import edu.umass.ckc.wo.tutor.probSel.TopicLoader;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Copyright (c) University of Massachusetts
 * Written by: David Marshall
 * Date: Sep 19, 2005
 * Time: 10:29:31 AM
 */
public abstract class BaseProblemSelectorOld implements ProblemSelector {


}
