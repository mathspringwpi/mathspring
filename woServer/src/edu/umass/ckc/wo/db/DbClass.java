package edu.umass.ckc.wo.db;

import ckc.servlet.servbase.UserException;
import edu.umass.ckc.wo.beans.ClassInfo;
import edu.umass.ckc.wo.beans.ClassConfig;
import edu.umass.ckc.wo.exc.AdminException;

import edu.umass.ckc.wo.handler.UserRegistrationHandler;
import edu.umass.ckc.wo.smgr.User;
import edu.umass.ckc.wo.tutor.Settings;
import edu.umass.ckc.wo.tutor.probSel.LessonModelParameters;
import edu.umass.ckc.wo.tutor.probSel.PedagogicalModelParameters;
import edu.umass.ckc.wo.tutor.probSel.TopicModelParameters;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) University of Massachusetts
 * Written by: David Marshall
 * Date: Jan 22, 2008
 * Time: 11:03:58 AM
 */
public class DbClass {

    public static final String GUEST_USER_CLASS_NAME = "GuestUserClass";
    public static final String ASSISTMENTS_CLASS_NAME = "AssistmentsUserClass";
    public static final String MARI_CLASS_NAME = "MariUserClass";

    public static ClassInfo getClass(Connection conn, int classId) throws SQLException {
        ResultSet rs = null;
        PreparedStatement s = null;

        try {
            String q = "select teacherId,school,schoolYear,name,town,section,teacher,propgroupid,logType,pretestPoolId," +
                    "f.statusReportIntervalDays, f.statusReportPeriodDays,f.studentEmailPeriodDays,f.studentEmailIntervalDays, c.flashClient, c.grade," +
                    "f.simplelc, f.simplecollab, f.simplelowdiff, f.simplehighdiff, f.simplediffRate, f.showPostSurvey from class c, classconfig f" +
                    " where c.id=? and f.classid=c.id";
            s = conn.prepareStatement(q);
            s.setInt(1, classId);
            rs = s.executeQuery();
            if (rs.next()) {
                int teacherId = rs.getInt(1);
                String sch = rs.getString(2);
                int yr = rs.getInt(3);
                String name = rs.getString(4);
                String town = rs.getString(5);
                String sec = rs.getString(6);
                String teacherName = rs.getString(7);
                int propgroupid = rs.getInt(8);
                int logType = rs.getInt(9);
                int pretestPoolId = rs.getInt(10);
                int emailInterval = rs.getInt(11);
                int statusReportPeriodDays = rs.getInt(12);
                int studentEmailPeriodDays = rs.getInt(13);
                int studentEmailIntervalDays = rs.getInt(14);
                String flashClient = rs.getString(15); // k12 or college
                String grade = rs.getString(16); // grade
                String simpleLc = rs.getString(17);
                String simpleCollab = rs.getString(18);
                String simpleLowDiff = rs.getString(19);
                String simpleHighDiff = rs.getString(20);
                String simpleDiffRate = rs.getString(21);
                boolean showPostSurvey = rs.getBoolean(22);
                ClassInfo ci = new ClassInfo(sch, yr, name, town, sec, classId, teacherId, teacherName, propgroupid, logType,
                        pretestPoolId, emailInterval, statusReportPeriodDays, studentEmailIntervalDays,
                        studentEmailPeriodDays,flashClient,grade);
                ci.setSimpleLC(simpleLc);
                ci.setSimpleCollab(simpleCollab);
                ci.setSimpleLowDiff(simpleLowDiff);
                ci.setSimpleHighDiff(simpleHighDiff);
                ci.setSimpleDiffRate(simpleDiffRate);
                ci.setShowPostSurvey(showPostSurvey);
                return ci;
            }
            return null;
        } finally {
            if (s != null)
                s.close();
            if (rs != null)
                rs.close();
        }
    }

    public static ClassInfo[] getAllClasses(Connection conn, boolean orderByTeacher) throws SQLException {
        ResultSet rs = null;
        PreparedStatement s = null;

        try {
            List<ClassInfo> classes = new ArrayList<ClassInfo>();
            String q = "select teacherId,school,schoolYear,name,town,section,teacher,propgroupid,logType,pretestPoolId," +
                    "f.statusReportIntervalDays, f.statusReportPeriodDays,f.studentEmailPeriodDays,f.studentEmailIntervalDays, c.flashClient, c.grade," +
                    "f.simplelc, f.simplecollab, f.simplelowdiff, f.simplehighdiff, f.simplediffRate, f.showPostSurvey, c.id from class c, classconfig f" +
                    " where f.classid=c.id order by " + ( orderByTeacher ? "c.teacher" : "c.id");
            s = conn.prepareStatement(q);
            rs = s.executeQuery();
            while (rs.next()) {
                int teacherId = rs.getInt(1);
                String sch = rs.getString(2);
                int yr = rs.getInt(3);
                String name = rs.getString(4);
                String town = rs.getString(5);
                String sec = rs.getString(6);
                String teacherName = rs.getString(7);
                int propgroupid = rs.getInt(8);
                int logType = rs.getInt(9);
                int pretestPoolId = rs.getInt(10);
                int emailInterval = rs.getInt(11);
                int statusReportPeriodDays = rs.getInt(12);
                int studentEmailPeriodDays = rs.getInt(13);
                int studentEmailIntervalDays = rs.getInt(14);
                String flashClient = rs.getString(15); // k12 or college
                String grade = rs.getString(16); // grade
                String simpleLc = rs.getString(17);
                String simpleCollab = rs.getString(18);
                String simpleLowDiff = rs.getString(19);
                String simpleHighDiff = rs.getString(20);
                String simpleDiffRate = rs.getString(21);
                boolean showPostSurvey = rs.getBoolean(22);
                int classId = rs.getInt(23);
                ClassInfo ci = new ClassInfo(sch, yr, name, town, sec, classId, teacherId, teacherName, propgroupid, logType,
                        pretestPoolId, emailInterval, statusReportPeriodDays, studentEmailIntervalDays,
                        studentEmailPeriodDays,flashClient,grade);
                ci.setSimpleLC(simpleLc);
                ci.setSimpleCollab(simpleCollab);
                ci.setSimpleLowDiff(simpleLowDiff);
                ci.setSimpleHighDiff(simpleHighDiff);
                ci.setSimpleDiffRate(simpleDiffRate);
                ci.setShowPostSurvey(showPostSurvey);
                classes.add(ci);
            }
            return classes.toArray(new ClassInfo[classes.size()]);
        } finally {
            if (s != null)
                s.close();
            if (rs != null)
                rs.close();
        }
    }

    public static ClassInfo[] getRecentClasses(Connection conn) throws SQLException {
        ResultSet rs = null;
        PreparedStatement s = null;

        try {
            List<ClassInfo> classes = new ArrayList<ClassInfo>();
            String q = "select teacherId,school,schoolYear,name,town,section,teacher,propgroupid,logType,pretestPoolId," +
                    "f.statusReportIntervalDays, f.statusReportPeriodDays,f.studentEmailPeriodDays,f.studentEmailIntervalDays, c.flashClient, c.grade," +
                    "f.simplelc, f.simplecollab, f.simplelowdiff, f.simplehighdiff, f.simplediffRate, f.showPostSurvey, c.id from class c, classconfig f" +
                    " where f.classid=c.id and c.createTimeStamp > date_add(now(), interval -2 year) order by c.id desc";
            s = conn.prepareStatement(q);
            rs = s.executeQuery();
            while (rs.next()) {
                int teacherId = rs.getInt(1);
                String sch = rs.getString(2);
                int yr = rs.getInt(3);
                String name = rs.getString(4);
                String town = rs.getString(5);
                String sec = rs.getString(6);
                String teacherName = rs.getString(7);
                int propgroupid = rs.getInt(8);
                int logType = rs.getInt(9);
                int pretestPoolId = rs.getInt(10);
                int emailInterval = rs.getInt(11);
                int statusReportPeriodDays = rs.getInt(12);
                int studentEmailPeriodDays = rs.getInt(13);
                int studentEmailIntervalDays = rs.getInt(14);
                String flashClient = rs.getString(15); // k12 or college
                String grade = rs.getString(16); // grade
                String simpleLc = rs.getString(17);
                String simpleCollab = rs.getString(18);
                String simpleLowDiff = rs.getString(19);
                String simpleHighDiff = rs.getString(20);
                String simpleDiffRate = rs.getString(21);
                boolean showPostSurvey = rs.getBoolean(22);
                int classId = rs.getInt(23);
                ClassInfo ci = new ClassInfo(sch, yr, name, town, sec, classId, teacherId, teacherName, propgroupid, logType,
                        pretestPoolId, emailInterval, statusReportPeriodDays, studentEmailIntervalDays,
                        studentEmailPeriodDays,flashClient,grade);
                ci.setSimpleLC(simpleLc);
                ci.setSimpleCollab(simpleCollab);
                ci.setSimpleLowDiff(simpleLowDiff);
                ci.setSimpleHighDiff(simpleHighDiff);
                ci.setSimpleDiffRate(simpleDiffRate);
                ci.setShowPostSurvey(showPostSurvey);
                classes.add(ci);
            }
            return classes.toArray(new ClassInfo[classes.size()]);
        } finally {
            if (s != null)
                s.close();
            if (rs != null)
                rs.close();
        }
    }

    /**
     * Given a name of a propgroup (typically "default") this will return the id
     *
     * @param conn
     * @param name
     * @return
     * @throws SQLException
     */
    public static int getPropGroupWithName(Connection conn, String name) throws SQLException, AdminException {
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String q = "select id from propgroup where name=?";
            ps = conn.prepareStatement(q);
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else
                throw new AdminException("There must be a propgroup row with name=default for class creation to work");
        } finally {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        }

    }

    public static String getTeacherName(Connection conn, int teacherID) throws SQLException {
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String q = "select fname,lname from teacher where ID=?";
            ps = conn.prepareStatement(q);
            ps.setInt(1, teacherID);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1) + " " + rs.getString(2);
            } else return null;
        } finally {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();

        }
    }


    public static int updateClass(Connection conn, int classId, String className,
                                  String school, String schoolYear,
                                  String town, String section, String grade) throws Exception {
        PreparedStatement s = null;
        try {
            String q = "update class set school=?, schoolYear=?, name=?, town=?, section=?, grade=? " +
                    "where id=?";
            s = conn.prepareStatement(q);
            s.setString(1, school);
            s.setInt(2, Integer.parseInt(schoolYear.trim()));
            s.setString(3, className);
            s.setString(4, town);
            s.setString(5, (section == null) ? "" : section);
            s.setString(6, grade);
            s.setInt(7, classId);
            return s.executeUpdate();
        } finally {
            if (s != null)
                s.close();
        }
    }

    public static int insertClass(Connection conn, String className,
                                  String school, String schoolYear,
                                  String town, String section, String teacherId, int propGroupId, int pretestPool, String grade) throws Exception {
        ResultSet newid = null;
        PreparedStatement s = null;
        try {
            String teacherName = getTeacherName(conn, Integer.parseInt(teacherId));
            String q = "insert into Class (teacherId,school,schoolYear,name,town,section,isActive," +
                    "teacher,propGroupId,logtype,pretestPoolId,grade) " +
                    "values (?,?,?,?,?,?,?,?,?,?,?,?)";
            s = conn.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
            s.setString(1, teacherId);
            s.setString(2, school);
            s.setInt(3, Integer.parseInt(schoolYear));
            s.setString(4, className);
            s.setString(5, town);
            s.setString(6, (section == null) ? "" : section);
            s.setInt(7, 1); //set class as active by default

            // for some reason a teacher name is replicated in the class table.
            // This should not have been done, but we have to live with it.
            s.setString(8, teacherName);
            s.setInt(9, propGroupId); // this is an id that gets us to a propgroup row (it is other info we
            // want to know about student (e.g. gender,age,race)
            s.setInt(10, 2);  // default log type is 2 indicating the eventlog table is where we store events for this class
            s.setInt(11, pretestPool);
            s.setString(12,grade);
            s.execute();
            newid = s.getGeneratedKeys();
            newid.next();
            int classId = newid.getInt(1);
            insertClassConfig(conn, classId);
            return newid.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            if (e.getErrorCode() == Settings.duplicateRowError || e.getErrorCode() == Settings.keyConstraintViolation)
                ;
            else throw e;
        } finally {
            if (newid != null)
                newid.close();
            if (s != null)
                s.close();
        }
        return -1;
    }


    public static void insertClassConfig(Connection conn, int classId) throws SQLException {
        PreparedStatement stmt = null;
        try {
            // relying on the default values defined in DB for the fields pretest,posttest,mfr,
            // spatialR,tutoring,
            String q = "insert into ClassConfig (classId) values (?)";
            stmt = conn.prepareStatement(q);
            stmt.setInt(1, classId);
            stmt.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == Settings.duplicateRowError || e.getErrorCode() == Settings.keyConstraintViolation)
                System.out.println("Row already exists.  Skipping.");
            else throw e;
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

//    public static ClassInfo[] getAllClasses (Connection conn) throws SQLException {
//        ResultSet rs=null;
//        PreparedStatement stmt=null;
//        try {
//            List<ClassInfo> result = new ArrayList<ClassInfo>();
//            String q = "select id from class";
//            stmt = conn.prepareStatement(q);
//            rs = stmt.executeQuery();
//            while (rs.next()) {
//                int classId= rs.getInt(1);
//                ClassInfo ci = getClass(conn,classId);
//                result.add(ci);
//            }
//            return result.toArray(new ClassInfo[result.size()]);
//        }
//        finally {
//            if (stmt != null)
//                stmt.close();
//            if (rs != null)
//                rs.close();
//        }
//    }

    // rewrote the method below so that it uses the getClass method to get each ClassInfo object.
    // This makes this method compatible with the ClassInfo objects built by getClass.
    public static ClassInfo[] getClasses (Connection conn, int teacherId) throws SQLException {
        ResultSet rs=null;
        PreparedStatement stmt=null;
        try {
            List<ClassInfo> result = new ArrayList<ClassInfo>();
            String q = "select id from class where teacherid=?";
            stmt = conn.prepareStatement(q);
            stmt.setInt(1,teacherId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int classId= rs.getInt(1);
                ClassInfo ci = getClass(conn,classId);
                if (ci != null)
                    result.add(ci);
            }
            return result.toArray(new ClassInfo[result.size()]);
        }
        finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
        }
    }

//    public static ClassInfo[] getClasses(Connection conn, int teacherId) throws SQLException {
//        ResultSet rs = null;
//        PreparedStatement s = null;
//
//        try {
//            List<ClassInfo> classes = new ArrayList<ClassInfo>();
//            String q = "select c.id,teacher,school,schoolYear,name,town,section,propgroupid,pretestPoolId, pool.description, " +
//                    "logType,f.statusReportIntervalDays,f.statusReportPeriodDays, f.studentEmailIntervalDays, f.studentEmailPeriodDays from class c, prepostpool pool, classconfig f where teacherId=? and pretestPoolId=pool.id and f.classid=c.id";
//            s = conn.prepareStatement(q);
//            s.setInt(1, teacherId);
//            rs = s.executeQuery();
//            while (rs.next()) {
//                int id = rs.getInt(1);
//                String teacherName = rs.getString(2);
//                String sch = rs.getString(3);
//                int yr = rs.getInt(4);
//                String name = rs.getString(5);
//                String town = rs.getString(6);
//                String sec = rs.getString(7);
//                int propgroupid = rs.getInt(8);
//                int pretestPoolId = rs.getInt(9);
//                String pretestPoolDescr = rs.getString(10);
//                int logType = rs.getInt(11);
//                int emailInterval = rs.getInt(12);
//                int statusReportPeriod = rs.getInt(13);
//                int studentEmailInterval = rs.getInt(14);
//                int studentEmailPeriod = rs.getInt(15);
//                classes.add(new ClassInfo(sch, yr, name, town, sec, id, teacherId, teacherName, propgroupid, pretestPoolId,
//                        pretestPoolDescr, logType, emailInterval, statusReportPeriod, studentEmailInterval, studentEmailPeriod, "5"));
//            }
//            return classes.toArray(new ClassInfo[classes.size()]);
//        } finally {
//            if (s != null)
//                s.close();
//            if (rs != null)
//                rs.close();
//        }
//    }

    /**
     *
     *
     *
     * @param conn
     * @param className
     * @return
     * @throws SQLException
     */
    public static ClassInfo getClassByName(Connection conn, String className) throws SQLException {
        ResultSet rs = null;
        PreparedStatement s = null;
        try {
            String q = "select c.id,teacher,school,schoolYear,name,town,section,propgroupid,pretestPoolId, pool.description, " +
                    "logType,teacherId, c.flashClient from class c, prepostpool pool where name='" + className + "' and pretestPoolId=pool.id";
            s = conn.prepareStatement(q);
            rs = s.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                String teacherName = rs.getString(2);
                String sch = rs.getString(3);
                int yr = rs.getInt(4);
                String name = rs.getString(5);
                String town = rs.getString(6);
                String sec = rs.getString(7);
                int propgroupid = rs.getInt(8);
                int pretestPoolId = rs.getInt(9);
                String pretestPoolDescr = rs.getString(10);
                int logType = rs.getInt(11);
                int teacherId = rs.getInt(12);
                String cl = rs.getString(13);
                ClassInfo c = new ClassInfo(sch, yr, name, town, sec, id, teacherId, teacherName, propgroupid, pretestPoolId,
                        pretestPoolDescr, logType, 0, 7, 0, 7, "5");
                c.setFlashClient(cl);
                return c;
            }
        } finally {
            if (s != null)
                s.close();
            if (rs != null)
                rs.close();

        }
        return null;
    }


    public static void deleteClassConfig(Connection conn, int classId) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("delete from classconfig where classid=?");
            ps.setInt(1, classId);
            ps.executeUpdate();
        } finally {
            if (ps != null)
                ps.close();
        }
    }

    public static void deleteClassStudents(Connection conn, int classId) throws SQLException {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            String q = "select id from student where classId=?";
            stmt = conn.prepareStatement(q);
            stmt.setInt(1, classId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int studId = rs.getInt(1);
                DbUser.deleteStudent(conn, studId);
            }
        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
        }
    }

    public static void deleteClass(Connection conn, int classId) throws SQLException {
        // must delete all child tables that relate to this through a foreign key.
        PreparedStatement ps = null;
        boolean error = false;
        try {
            deleteClassStudents(conn, classId);   // this happens in a transaction
            conn.setAutoCommit(false);
            deleteClassConfig(conn, classId);
            DbUser.deleteChildRows(conn, "classId", classId, "pretestAssignment");
            DbUser.deleteChildRows(conn, "classId", classId, "classConfig");
            DbUser.deleteChildRows(conn, "classId", classId, "pedagogyGroup");
            DbUser.deleteChildRows(conn, "classId", classId, "classPedagogies");
            DbUser.deleteChildRows(conn, "classId", classId, "student");
            DbUser.deleteChildRows(conn, "classId", classId, "classgroup");
            DbUser.deleteChildRows(conn, "classId", classId, "classomittedproblems");

            ps = conn.prepareStatement("delete from class where id=?");
            ps.setInt(1, classId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("DbClass.deleteClass: Error during deletion of class " + classId);
            error = true;
            conn.rollback();
            System.out.println("Rolling back");
            throw e;
        } finally {
            if (!error) {
                conn.commit();
            }
            conn.setAutoCommit(true);
            if (ps != null)
                ps.close();
        }
    }

    public static int getPretestPool(Connection conn, int classId) throws SQLException {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            String q = "select pretestPoolId from class where id=?";
            stmt = conn.prepareStatement(q);
            stmt.setInt(1, classId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int preId = rs.getInt(1);
                return preId;
            }
        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
        }
        return 0;
    }

    // sets the class's pretest pool
    public static int setPretestPool(Connection conn, int classId, int poolId) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String q = "update class set pretestPoolId=? where id=?";
            stmt = conn.prepareStatement(q);
            stmt.setInt(1, poolId);
            stmt.setInt(2, classId);
            return stmt.executeUpdate();
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }


    // sets the switch for a class's pretest
    public static void setGivePretest(Connection conn, int classId, boolean givePretest) throws SQLException {
        PreparedStatement stmt = null;
        try {
            // try to insert it (old classes may not have a ClassConfig row)
            String q = "insert into ClassConfig (classId, pretest) values (?,?)";
            stmt = conn.prepareStatement(q);
            stmt.setInt(1, classId);
            stmt.setInt(2, givePretest ? 1 : 0);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            // if a row already exists for the classId, then update it
            if (e.getErrorCode() == Settings.duplicateRowError || e.getErrorCode() == Settings.keyConstraintViolation) {
                String q = "update ClassConfig set pretest=? where classId=?";
                stmt = conn.prepareStatement(q);
                stmt.setInt(1, givePretest ? 1 : 0);
                stmt.setInt(2, classId);
                stmt.executeUpdate();
            } else throw e;


        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    public static int getClassPrePostTest(Connection conn, int classId, String testType) throws SQLException {
        ResultSet rs = null;
        PreparedStatement s = null;

        try {
            String q;
            if (testType.equals(DbPrePost.PRETEST))
                q = "select pretest from ClassConfig where classId=?";
            else q = "select posttest from ClassConfig where classId=?";
            s = conn.prepareStatement(q);
            s.setInt(1, classId);
            rs = s.executeQuery();
            if (rs.next()) {
                int givePretest = rs.getInt(1);
                return givePretest;
            }
            return -1;  // never should reach this line.
        } finally {
            if (s != null)
                s.close();
            if (rs != null)
                rs.close();
        }
    }

    // TODO have assumed only topicModels are being used for now
    public static LessonModelParameters getLessonModelParameters(Connection conn, int classId) throws SQLException {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            String q = "select maxNumberProbsToShowPerTopic,maxTimeInTopic,contentFailureThreshold,topicMastery," +
                    "minNumberProbsToShowPerTopic," +
                    "minTimeInTopic,difficultyRate,topicIntroFrequency," +
                    "exampleFrequency, lessonStyle" +
                    " from classconfig where classId=?";
            stmt = conn.prepareStatement(q);
            stmt.setInt(1, classId);
            rs = stmt.executeQuery();
            PedagogicalModelParameters params;
            if (rs.next()) {
                int maxProbsInTopic = rs.getInt(1);
                // If the class has one null param, treat them all as null and return defaults
                // since they should all get set to real values or all be null
                // TODO this makes no sense.   These are values set in teacher tools.   If some aren't set, shouldn't
                // we keep the defaults that were defined the control params of the Pedagogy defined the in the XML file?
                if (rs.wasNull())
                    return new TopicModelParameters();
                long maxTimeInTopic = rs.getLong("maxTimeInTopic");
                int thresh = rs.getInt("contentFailureThreshold");
                double mastery = rs.getDouble("topicMastery");
                int minProbsInTopic = rs.getInt("minNumberProbsToShowPerTopic");
                long minTimeInTopic = rs.getLong("minTimeInTopic");
                double difficultyRate = rs.getDouble("difficultyRate");
                String topicIntroFreq= rs.getString("topicIntroFrequency");
                if (rs.wasNull())
                    topicIntroFreq = null;
                String exampleFreq= rs.getString("exampleFrequency");
                if (rs.wasNull())
                    exampleFreq = null;

                String lessonStyle = rs.getString("lessonStyle");
                TopicModelParameters.frequency tif =  topicIntroFreq == null ? null : TopicModelParameters.convertTopicIntroFrequency(topicIntroFreq);
                TopicModelParameters.frequency ef =  exampleFreq == null ? null : TopicModelParameters.convertExampleFrequency(exampleFreq);
                return new TopicModelParameters(maxTimeInTopic, thresh, mastery, minProbsInTopic, minTimeInTopic, difficultyRate,  maxProbsInTopic,
                        tif,ef, lessonStyle);
            }
            return null;
        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
        }
    }

    public static PedagogicalModelParameters getPedagogicalModelParameters(Connection conn, int classId) throws SQLException {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            String q = "select maxNumberProbsToShowPerTopic,maxTimeInTopic,contentFailureThreshold,topicMastery," +
                    "minNumberProbsToShowPerTopic," +
                    "minTimeInTopic,difficultyRate, externalActivityTimeThreshold,topicIntroFrequency," +
                    "exampleFrequency, problemReuseIntervalSessions, problemReuseIntervalDays, lessonStyle" +
                    " from classconfig where classId=?";
            stmt = conn.prepareStatement(q);
            stmt.setInt(1, classId);
            rs = stmt.executeQuery();
            PedagogicalModelParameters params;
            if (rs.next()) {
                int maxProbsInTopic = rs.getInt(1);
                // If the class has one null param, treat them all as null and return defaults
                // since they should all get set to real values or all be null
                // TODO this makes no sense.   These are values set in teacher tools.   If some aren't set, shouldn't
                // we keep the defaults that were defined the control params of the Pedagogy defined the in the XML file?
                if (rs.wasNull())
                    return new PedagogicalModelParameters();
                long maxTimeInTopic = rs.getLong(2);
                int thresh = rs.getInt(3);
                double mastery = rs.getDouble(4);
                int minProbsInTopic = rs.getInt(5);
                long minTimeInTopic = rs.getLong(6);
                double difficultyRate = rs.getDouble(7);
                int externalActivityTimeThresh = rs.getInt(8);
                String topicIntroFreq= rs.getString(9);
                if (rs.wasNull())
                    topicIntroFreq = null;
                String exampleFreq= rs.getString(10);
                if (rs.wasNull())
                    exampleFreq = null;
                int probReuseIntervalSessions = rs.getInt(11);
                if (rs.wasNull())
                    probReuseIntervalSessions=-1;
                int probReuseIntervalDays = rs.getInt(12);
                if (rs.wasNull())
                    probReuseIntervalDays=-1;
                String lessonStyle = rs.getString("lessonStyle");
                TopicModelParameters.frequency tif =  topicIntroFreq == null ? null : PedagogicalModelParameters.convertTopicIntroFrequency(topicIntroFreq);
                TopicModelParameters.frequency ef =  exampleFreq == null ? null : PedagogicalModelParameters.convertExampleFrequency(exampleFreq);
                return new PedagogicalModelParameters(maxTimeInTopic, thresh, mastery, minProbsInTopic, minTimeInTopic, difficultyRate, externalActivityTimeThresh, maxProbsInTopic,
                        true, true,tif,ef,probReuseIntervalSessions, probReuseIntervalDays, lessonStyle);
            }
            return null;
        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
        }
    }

    // I tried making this just return the sessions pedagogical model params rather than going to classes, etc but it fails.
    // I think the problem is that the Guest user class has some parameters set (but not the new one showMPP) so the row is found
    // and then it builds a set of params with the default value of showMPP of true which then overloads the value set in the pedagogy.
    public static PedagogicalModelParameters getClassPedagogicalModelParameters(Connection conn, int classId) throws SQLException {
//        return this.pedagogicalModel.getParams();
        PedagogicalModelParameters params= DbClass.getPedagogicalModelParameters(conn, classId);
        // If parameters are not stored for this particular class, a default set should be stored
        // in classconfig table for classId=1.   If nothing there, then use the defaults created
        // in the default PedagogicalModelParameters constructor
        if (params == null) {
            params = DbClass.getPedagogicalModelParameters(conn, 1);
            if (params == null)
                params = new PedagogicalModelParameters();
        }
        return params;
    }

    public static LessonModelParameters getClassLessonModelParameters(Connection conn, int classId) throws SQLException {
//        return this.pedagogicalModel.getParams();
        LessonModelParameters params= DbClass.getLessonModelParameters(conn, classId);
        // If parameters are not stored for this particular class, a default set should be stored
        // in classconfig table for classId=1.   If nothing there, then use the defaults created
        // in the default PedagogicalModelParameters constructor
        if (params == null) {
            params = DbClass.getLessonModelParameters(conn, 1);
            if (params == null)
                params = new TopicModelParameters();
        }
        return params;
    }

    public static void setProblemSelectorParameters(Connection conn, int classId, PedagogicalModelParameters params) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String q = "update classconfig set maxNumberProbsToShowPerTopic=?, maxTimeInTopic=?, contentFailureThreshold=?, topicMastery=?," +
                    "minNumberProbsToShowPerTopic=?, minTimeInTopic=?, difficultyRate=?, externalActivityTimeThreshold=? where classid=?";
            stmt = conn.prepareStatement(q);
            stmt.setInt(1, params.getMaxNumberProbs());
            stmt.setLong(2, params.getMaxTimeInTopic());
            stmt.setInt(3, params.getContentFailureThreshold());
            stmt.setDouble(4, params.getTopicMastery());
            stmt.setInt(5, params.getMinNumberProbs());
            stmt.setLong(6, params.getMinTimeInTopic());
            stmt.setDouble(7, params.getDifficultyRate());
            stmt.setInt(8, params.getExternalActivityTimeThreshold());
            stmt.setInt(9, classId);
            stmt.executeUpdate();
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    public static void setClassConfig(Connection conn, int classId, ClassConfig config) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String q = "update classconfig set pretest=?, posttest=?,mfr=?,fantasy=?,spatialR=?," +
                    "tutoring=?,useDefaultHutActivationRules=? where classId=?";
            stmt = conn.prepareStatement(q);
            stmt.setInt(1, config.getPretest());
            stmt.setInt(2, config.getPosttest());
            stmt.setInt(3, config.getMfr());
            stmt.setInt(4, config.getFantasy());
            stmt.setInt(5, config.getSpatial());
            stmt.setInt(6, config.getTutoring());
            stmt.setBoolean(7, config.getUseDefaults());
            stmt.setInt(8, classId);
            stmt.executeUpdate();
        } finally {

            if (stmt != null)
                stmt.close();
        }

    }


    public static ClassConfig getClassConfig(Connection conn, int classId) throws SQLException {
        String q = "select pretest,posttest,fantasy,mfr,spatialR,tutoring,useDefaultHutActivationRules,showPostSurvey" +
                ",presurveyurl,postsurveyurl,postsurveywaittime from classconfig where classId=?";
        PreparedStatement ps = conn.prepareStatement(q);
        ps.setInt(1, classId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int pre = rs.getInt("pretest");
            int post = rs.getInt("posttest");
            int fant = rs.getInt("fantasy");
            int mfr = rs.getInt("mfr");
            int spat = rs.getInt("spatialR");
            int tut = rs.getInt("tutoring");
            boolean useDef = rs.getBoolean("useDefaultHutActivationRules");
            boolean showPostSurvey = rs.getBoolean("showPostSurvey");
            String presurveyurl = rs.getString("presurveyurl");
            String postsurveyurl = rs.getString("postsurveyurl");
            int postsurveyWaitTime = rs.getInt("postSurveyWaitTime");
            return new ClassConfig(pre, post, fant, mfr, spat, tut, useDef, showPostSurvey, presurveyurl, postsurveyurl, postsurveyWaitTime);
        } else return null;
    }



    public static List<User> getClassStudents(Connection conn, int classID) throws SQLException {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            String q = "select id,fname,lname,username,email,password from student where classid=?";
            stmt = conn.prepareStatement(q);
            stmt.setInt(1, classID);
            rs = stmt.executeQuery();
            List<User> res = new ArrayList<User>();
            while (rs.next()) {
                int id = rs.getInt(1);
                String fname = rs.getString(2);
                String lname = rs.getString(3);
                String uname = rs.getString(4);
                String email = rs.getString(5);
                String pw = rs.getString(6);
                res.add(new User(fname, lname, uname, email, pw, id));
            }
            return res;
        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
        }

    }

    public static void cloneConfig(Connection conn, int classId, int newClassId) throws SQLException {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        PreparedStatement ps = null;
        try {
            String q = "select * from classconfig where classid=?";
            stmt = conn.prepareStatement(q);
            stmt.setInt(1, classId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                boolean pretest = rs.getBoolean("pretest");
                boolean posttest = rs.getBoolean("posttest");
                boolean mfr = rs.getBoolean("mfr");
                boolean fantasy = rs.getBoolean("fantasy");
                boolean spatialR = rs.getBoolean("spatialR");
                boolean tutoring = rs.getBoolean("tutoring");
                boolean useDefaultHutActivationRules = rs.getBoolean("useDefaultHutActivationRules");
                int maxNumProbsPerTopic = rs.getInt("maxNumberProbsToShowPerTopic");
                int maxTimeInTopic = rs.getInt("maxTimeInTopic");
                int contentFailureThreshold = rs.getInt("contentFailureThreshold");
                double topicMastery = rs.getDouble("topicMastery");
                String q2 = "insert into classconfig (classId,pretest,posttest,mfr,fantasy," +
                        "spatialR,tutoring,useDefaultHutActivationRules,maxNumberProbsToShowPerTopic," +
                        "maxTimeInTopic,contentFailureThreshold,topicMastery) values " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?)";
                ps = conn.prepareStatement(q2);
                ps.setInt(1, newClassId);
                ps.setBoolean(2, pretest);
                ps.setBoolean(3, posttest);
                ps.setBoolean(4, mfr);
                ps.setBoolean(5, fantasy);
                ps.setBoolean(6, spatialR);
                ps.setBoolean(7, tutoring);
                ps.setBoolean(8, useDefaultHutActivationRules);
                // note that if nulls are in the db for the 4 params above, 0 will come back and that is what
                // will be inserted into the clone rather than null because the effect should be the same
                // in a class that is not using topic parameters.
                ps.setInt(9, maxNumProbsPerTopic);
                ps.setInt(10, maxTimeInTopic);
                ps.setInt(11, contentFailureThreshold);
                ps.setDouble(12, topicMastery);
                ps.executeUpdate();
                ps.close();
            }
        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
        }
    }

    public static int removeConfig(Connection conn, int classId) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String q = "delete from classconfig where classid=?";
            stmt = conn.prepareStatement(q);
            stmt.setInt(1, classId);
            return stmt.executeUpdate();
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    public static int setEmailInterval(Connection conn, int classId, int emailInterval) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String q = "update classconfig set statusReportIntervalDays=? where classid=?";
            stmt = conn.prepareStatement(q);
            stmt.setInt(1, emailInterval);
            stmt.setInt(2, classId);
            return stmt.executeUpdate();
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    public static List<User> addClassStudents (Connection conn, ClassInfo classInfo, int numStudentsToAdd) {
        return null;
    }


    public static boolean createClassStudents(Connection conn, ClassInfo classInfo, String prefix, String password, int beginNum, int endNum,
                                              String testUserPrefix, String testUserPassword) throws Exception {
        List<String> pedIds = DbClassPedagogies.getClassPedagogyIds(conn, classInfo.getClassid());
        if (!buildStudents(conn, classInfo, prefix, password, beginNum, endNum, pedIds))
            return false;


        // Build Test users if a prefix was given
        if (testUserPrefix != null && testUserPrefix.trim().length() != 0) {
            return buildTestUsers(conn, classInfo, testUserPrefix, testUserPassword, pedIds);
        }
        return true;
    }

    private static boolean buildTestUsers(Connection conn, ClassInfo classInfo, String testUserPrefix, String testUserPassword, List<String> pedIds) throws SQLException {
        // Get the test user with the same prefix and max ID.  Then we'll get the number off the end of this username
        // so that the new test users we build will be incremented from that starting point.
        ResultSet rs = null;
        PreparedStatement stmt = null;
        int startIx = 0;
        try {
            String q = "select username from student where trialUser=1 and username like '" + testUserPrefix + "-%' order by id desc";
            stmt = conn.prepareStatement(q);
            rs = stmt.executeQuery();
            if (rs.next()) {
                String un = rs.getString(1);
                startIx = Integer.parseInt(un.substring(un.indexOf('-') + 1)) + 1;
            }
        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
        }


        for (int thisUser = 0; thisUser < pedIds.size(); thisUser++) {

            StringBuffer testUser = new StringBuffer(testUserPrefix + "-" + (thisUser + startIx));
            stmt = null;
            try {
                String q = "INSERT into STUDENT (username, password, classid, pedagogyid, keepUser,keepData,updateStats,showTestControls, trialUser) values (?,?,?,?,1,1,0,1,1)";
                stmt = conn.prepareStatement(q);
                stmt.setString(1, testUser.toString());
                stmt.setString(2, testUserPassword);
                stmt.setInt(3, classInfo.getClassid());
                stmt.setInt(4, Integer.parseInt(pedIds.get(thisUser)));
                stmt.execute();
            } finally {
                if (stmt != null)
                    stmt.close();
            }

        }
        return true;
    }


    private static boolean buildStudents(Connection conn, ClassInfo classInfo, String prefix, String password, int beginNum, int endNum, List<String> pedIds) throws Exception {
        if (beginNum < 0 || endNum < 1 || endNum <= beginNum)
            throw new UserException("Begin/End numbers are invalid");
        for (int thisUser = beginNum, counterGroups = 0; thisUser <= endNum; thisUser++, counterGroups++) {
            if (counterGroups == pedIds.size())
                counterGroups = 0;

            StringBuffer username = new StringBuffer(prefix);
            username.append(thisUser);

            PreparedStatement stmt = null;
            try {
                // There is no primary key containing username to make sure that these don't get duplicated.   Therefore,
                // we need to manually check to see if that user exists first and throw an exception if it does.
                if (DbUser.getStudent(conn, username.toString(), password) != -1)
                    throw new UserException("Cannot create users.  User: " + username.toString() + " already exists.");
                UserRegistrationHandler.registerStudentUser(conn,username.toString(),password,classInfo);

            } finally {
                if (stmt != null)
                    stmt.close();
            }
        }
        return true;
    }




    public static void updateClassEmailSettings(Connection conn, int classId, int studentEmailInterval, int studentReportPeriod, int teacherEmailInterval, int teacherReportPeriod) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String q = "update classconfig set studentEmailIntervalDays=?, studentEmailPeriodDays=?, statusReportIntervalDays=?, statusReportPeriodDays=? where classid=?";
            stmt = conn.prepareStatement(q);
            stmt.setInt(1, studentEmailInterval);
            stmt.setInt(2, studentReportPeriod);
            stmt.setInt(3, teacherEmailInterval);
            stmt.setInt(4, teacherReportPeriod);
            stmt.setInt(5, classId);
            stmt.executeUpdate();
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }


    public static int getClassTopic(Connection conn, int classID, int n) throws SQLException {
        String q = "select probGroupId from ClassLessonPlan where classID=? and seqPos>0 order by seqPos";
        PreparedStatement ps = conn.prepareStatement(q);
        ps.setInt(1, classID);
        ResultSet rs = ps.executeQuery();
        for (int i = 1; rs.next(); i++) {
            if (i == n)
                return rs.getInt(1);
        }
        return -1;
    }

    public static int getDefaultClassId(Connection conn) throws SQLException {
        String q = "select classId from ClassLessonPlan where isDefault=1";
        PreparedStatement ps = conn.prepareStatement(q);
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            return rs.getInt(1);
        else return -1;
    }


    // get topics marked active and that have problems mapped to them
    public static List<Integer> getClassLessonTopics(Connection conn, int classID) throws SQLException {
        String q = "select p.seqPos, p.probGroupId from ClassLessonPlan p, problemGroup t where p.seqpos >= 0 " +
                "and p.classId=? and t.id=p.probGroupId and t.active=1 and t.id in" +
                " (select distinct pgroupid from probprobgroup) order by p.seqPos";
        PreparedStatement ps = conn.prepareStatement(q);
        ps.setInt(1, classID);
        ResultSet rs = ps.executeQuery();
        List<Integer> topics = new ArrayList<Integer>();
        while (rs.next()) {
            int topicId = rs.getInt(2);
            topics.add(topicId);
        }

        if (topics.size() == 0)   {
            topics = getClassLessonTopics(conn, getDefaultClassId(conn));
        }

        return topics;
    }

    // Gets the topics for the class in the order given by the classLessonPlan.  It will omit any topic that
    // does not have ready problems.
    public static List<Integer> getClassLessonPlayableTopics(Connection conn, int classID) throws SQLException {
        String q = "select p.seqPos, p.probGroupId from ClassLessonPlan p, problemGroup t where p.seqpos >= 0" +
                "                and p.classId=? and t.id=p.probGroupId and t.active=1 and exists" +
                "(select * from problem pp, probprobgroup tm where pp.id=tm.probId and tm.pgroupId=t.id and pp.status='ready') order by p.seqpos" ;
        PreparedStatement ps = conn.prepareStatement(q);
        ps.setInt(1, classID);
        ResultSet rs = ps.executeQuery();
        List<Integer> topics = new ArrayList<Integer>();
        while (rs.next()) {
            int topicId = rs.getInt(2);
            topics.add(topicId);
        }

        if (topics.size() == 0)   {
            topics = getClassLessonPlayableTopics(conn, getDefaultClassId(conn));
        }

        return topics;
    }


    public static List<Integer> getClassOmittedProblems(Connection conn, int classID, int topicId) throws SQLException {
        String q = "select probId from ClassOmittedProblems where classId=? and topicId=?";
        PreparedStatement ps = conn.prepareStatement(q);
        ps.setInt(1, classID);
        ps.setInt(2, topicId);

        List<Integer> results = new ArrayList<Integer>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int pid = rs.getInt(1);
            results.add(pid);
        }
        return results;
    }

    public static double getClassMastery(Connection conn, int classId) throws SQLException {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            String q = "select topicMastery from classConfig where classId=?";
            stmt = conn.prepareStatement(q);
            stmt.setInt(1, classId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                double c = rs.getDouble(1);
                return c;
            }
        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
        }
        return 0.85; // in case of a failure this default value will work.
    }


    public static void setSimpleConfig(Connection conn, int classId, String lc, String collab, String diffRate, String lowDiff, String highDiff) throws SQLException {
        PreparedStatement s = null;
        try {
            String q = "update classconfig set simplelc=?, simplecollab=?, simpleLowDiff=?, simpleHighDiff=?, simpleDiffRate=? " +
                    "where classid=?";
            s = conn.prepareStatement(q);
            s.setString(1, lc);
            s.setString(2, collab);
            s.setString(3, lowDiff);
            s.setString(4, highDiff);
            s.setString(5, diffRate);
            s.setInt(6, classId);
            s.executeUpdate();
        } finally {
            if (s != null)
                s.close();
        }
    }

    public static void main( String[] args )
    {


//        System.out.println("Maven + Hibernate + MySQL");
//        Session session = HibernateUtil.getSessionFactory().openSession();
//
//        session.beginTransaction();
////        Stock stock = new Stock();
////
////        stock.setStockCode("4715");
////        stock.setStockName("GENM");
////
////        session.save(stock);
//        session.getTransaction().commit();
    }


    public static void setClassConfigDiffRate(Connection conn, int classId, double diffRate, double masteryThresh, int contentFailureThresh) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String q = "update classconfig set difficultyRate=?, topicMastery=?, contentFailureThreshold=? where classid=?";
            stmt = conn.prepareStatement(q);
            stmt.setDouble(1, diffRate);
            stmt.setDouble(2, masteryThresh);
            stmt.setInt(3, contentFailureThresh);
            stmt.setInt(4, classId);
            stmt.executeUpdate();
        } finally {
            if (stmt != null)
                stmt.close();
        }

    }

    public static void setClassConfigShowPostSurvey(Connection conn, int classId, boolean showPostSurvey) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String q = "update classconfig set showPostSurvey=? where classid=?";
            stmt = conn.prepareStatement(q);
            stmt.setBoolean(1, showPostSurvey);
            stmt.setInt(2, classId);
            stmt.executeUpdate();
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    public static void deleteClasses(Connection conn, int[] classesToDelete) throws SQLException {
        for (int c : classesToDelete) {
            DbClass.deleteClass(conn,c);
        }
    }

    public static List<Integer> getTeacherClassIds(Connection conn, int teacherId) throws SQLException {
        ResultSet rs=null;
        PreparedStatement stmt=null;
        try {
            List<Integer> result = new ArrayList<Integer>();
            String q = "select id from class where teacherId=?";
            stmt = conn.prepareStatement(q);
            stmt.setInt(1,teacherId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int classId= rs.getInt(1);
                result.add(classId);
            }
            return result;
        }
        finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
        }

    }



    public static final List<ClassInfo> getTeacherClasses (Connection conn, int teacherId) throws SQLException {
        List<Integer> cids = getTeacherClassIds(conn,teacherId);
        List<ClassInfo> results = new ArrayList<ClassInfo>();
        for (int cid: cids) {
            ClassInfo ci = getClass(conn,cid);
            results.add(ci);
        }
        return results;
    }
}