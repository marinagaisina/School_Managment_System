package jpa.service;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import org.junit.Test;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

public class StudentService implements StudentDAO {
    @Override
    public List<Student> getAllStudents() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SMSPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Student> executedList = null;
        try {
            executedList = entityManager.createQuery("select s from Student s", Student.class).getResultList();
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

    @Override
    public Student getStudentByEmail(String sEmail) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SMSPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Student expectedStudent = null;
        try {
            expectedStudent = entityManager.find(Student.class, sEmail);
        } catch (NoResultException e) {
            System.out.println("No student with email: "+sEmail+" found.");
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
        return expectedStudent;
    }

    @Override
    public boolean validateStudent(String sEmail, String sPassword) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SMSPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        boolean isValid = false;
        Student student = null;
        try {
            Query query = entityManager.createQuery("select s from Student s where s.sEmail=:email and s.sPass=:pass").setParameter("email", sEmail).setParameter("pass", sPassword);
            student = (Student) query.getSingleResult();
            if (student != null) {
                isValid = true;
            }
        } catch (NoResultException e) {
            e.printStackTrace();
        } catch (EntityNotFoundException e) {
            System.out.println("wrong " + sEmail + " and/or " + sPassword);
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
        return isValid;
    }
    @Test
    @Override
    public void registerStudentToCourse(String sEmail, int cId) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SMSPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Student student = null;
        Course course = null;
        try {
            entityManager.getTransaction().begin();
            student = entityManager.find(Student.class, sEmail);
            course = entityManager.find(Course.class, cId);
            Query query = entityManager.createNativeQuery("select count(sc.student_email) from student_course sc where sc.student_email=:sEmail and sc.course_id=:id")
                    .setParameter("sEmail", sEmail).setParameter("id", cId);
            Object rows = (Object) query.getSingleResult();
            if (String.valueOf(rows) == "0") {
                student.addCourse(course);
                entityManager.getTransaction().commit();
                System.out.println("You have successfully registered to " + course.getcName() + " course.");
            } else {
                System.out.println(student.getsName() + " is already registered for "+course.getcName()+" course");
            }

            } catch (NoResultException e) {
                e.printStackTrace();
            } catch (EntityNotFoundException e) {
                System.out.println("wrong " + sEmail + " and/or course id");
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
            return;
    }

    @Override
    public List<Course> getStudentCourses(String sEmail) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SMSPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Course> studentsCourses = null;
        try {
            Student student= entityManager.find(Student.class, sEmail);
            studentsCourses = student.getsCourses();
            if (studentsCourses.size() == 0) {
                System.out.println("No courses were assigned yet.");
                return Collections.EMPTY_LIST;
            }
        } catch (Exception e) {
            if (entityManager.getTransaction() !=null) {
                entityManager.getTransaction().rollback();
                e.printStackTrace();
            }
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return studentsCourses;
    }

}
