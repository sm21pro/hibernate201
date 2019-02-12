package com.srikanth.hibernate.dao;

import com.srikanth.hibernate.entity.Employee;
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

public class ManageEmployeeDao {

    private static final Logger logger = Logger.getLogger(ManageEmployeeDao.class);

    /**
     * Persist employee to database
     *
     * @param employee
     */
    public void saveOrUpdateEmployee(
            final Employee employee, final PersistType typeOfPers)
            throws EventRegistrationFailureException {

        logger.info("Persisting employee to database");
        Session session = null;
        Transaction transaction = null;
        try {
            SessionFactory sessionFactory = HibernateSessionUtil.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            if (PersistType.SAVE.equals(typeOfPers)) {
                session.persist(employee);
            } else {
                session.saveOrUpdate(employee);
            }
            transaction.commit();
        } catch (HibernateException ex) {
            logger.error("Failed saving employee. Rolling back the transaction");
            transaction.rollback();
            throw new EventRegistrationFailureException("Exception during database transaction" + ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    /**
     * Retrieves one employee from database.
     *
     * @param employeeId
     * @return
     */
    public Employee fetchEmployee(final String employeeId) throws EventRegistrationFailureException {
        logger.info("Fetching Employee using employee id " + employeeId);
        Employee employee = null;
        Session session = null;
        try {
            SessionFactory sessionFactory = HibernateSessionUtil.getSessionFactory();
            session = sessionFactory.openSession();
            employee = session.get(Employee.class, employeeId);
        } catch (HibernateException ex) {
            logger.error("Failed fetching employee\n" + ex);
            throw new EventRegistrationFailureException("Exception during database transaction" + ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return employee;
    }

    /**
     * Retrieves all employees list from database.
     *
     * @return
     */
    public List<Employee> getEmployees() throws EventRegistrationFailureException {
        logger.info("Fetching all employees");
        List<Employee> employeeList = new ArrayList<>();
        Session session = null;
        try {
            SessionFactory sessionFactory = HibernateSessionUtil.getSessionFactory();
            session = sessionFactory.openSession();
            employeeList = session.createQuery("from Employee").list();
        } catch (HibernateException ex) {
            logger.error("Failed fetching employees list\n" + ex);
            throw new EventRegistrationFailureException("Exception during database transaction" + ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return employeeList;
    }
}
