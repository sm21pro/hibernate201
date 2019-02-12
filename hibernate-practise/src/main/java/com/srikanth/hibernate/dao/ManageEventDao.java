package com.srikanth.hibernate.dao;

import com.srikanth.hibernate.entity.Employee;
import com.srikanth.hibernate.entity.Event;
import com.srikanth.hibernate.exception.EventRegistrationFailureException;
import com.srikanth.hibernate.util.HibernateSessionUtil;
import com.srikanth.hibernate.util.PersistType;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ManageEventDao {

    private static final Logger logger = Logger.getLogger(ManageEmployeeDao.class);

    /**
     * Persist event to database
     *
     * @param event
     */
    public void saveOrUpdateEvent(
            final Event event, final PersistType typeOfPers)
            throws EventRegistrationFailureException {

        logger.info("Persisting employee to database");
        Session session = null;
        Transaction transaction = null;
        try {
            SessionFactory sessionFactory = HibernateSessionUtil.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            if (PersistType.SAVE.equals(typeOfPers)) {
                session.persist(event);
            } else {
                session.saveOrUpdate(event);
            }
            transaction.commit();
        } catch (HibernateException ex) {
            logger.error("Failed saving event. Rolling back the transaction");
            transaction.rollback();
            throw new EventRegistrationFailureException("Exception during database transaction" + ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Retrieves one event from database.
     *
     * @param eventNum
     * @return
     */
    public Event fetchEvent(final long eventNum) throws EventRegistrationFailureException {

        logger.info("Fetching Event using event num " + eventNum);
        Event event = null;
        Session session = null;
        try {
            SessionFactory sessionFactory = HibernateSessionUtil.getSessionFactory();
            session = sessionFactory.openSession();
            event = session.get(Event.class, eventNum);
        } catch (HibernateException ex) {
            logger.error("Failed fetching event\n" + ex);
            throw new EventRegistrationFailureException("Exception during database transaction" + ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return event;
    }

    /**
     * Retrieve all events list from database.
     *
     * @return
     */
    public List<Event> getEvents() throws EventRegistrationFailureException {
        logger.info("Fetching all events");
        List<Event> eventsList = new ArrayList<>();
        Session session = null;
        try {
            SessionFactory sessionFactory = HibernateSessionUtil.getSessionFactory();
            session = sessionFactory.openSession();
            eventsList = session.createQuery("from Event").list();
        } catch (HibernateException ex) {
            logger.error("Failed fetching events list\n" + ex);
            throw new EventRegistrationFailureException("Exception during database transaction" + ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return eventsList;
    }
}
