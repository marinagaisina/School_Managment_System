package jpa.service;

import jpa.dao.CourseDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;

import javax.persistence.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.zip.CheckedInputStream;

import static java.lang.System.out;


public class CourseService implements CourseDAO {
    @Override
    public List<Course> getAllCourses() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SMSPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Course> executedList = null;

        try {
            entityManager.getTransaction().begin();
            executedList = entityManager.createQuery("select c from Course c", Course.class).getResultList();
            entityManager.getTransaction().commit();
        } catch (NoResultException e) {
            out.println("No courses were found");
            e.printStackTrace();
        } catch (Exception e) {
            if (entityManager.getTransaction() !=null) {
                entityManager.getTransaction().rollback();
                e.printStackTrace();
            }
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return executedList;
    }
    public Course getCourseById(int id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SMSPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Course foundCourse = null;
        try {
            Query namedQuery = entityManager.createNamedQuery("Course.findByCourseId", Course.class).setParameter("cId", id);
            foundCourse = (Course) namedQuery.getSingleResult();
            //out.println(foundCourse.getcName());
            //entityManager.getTransaction().commit();
        } catch (NoResultException e) {
            out.println("No course with "+id+" was found");
            e.printStackTrace();
        } catch (Exception e) {
            if (entityManager.getTransaction() !=null) {
                entityManager.getTransaction().rollback();
                e.printStackTrace();
            }
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return foundCourse;
    }
}
