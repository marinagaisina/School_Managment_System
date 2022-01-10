package jpa.entitymodels;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="course")
@NamedQuery(name = "Course.findByCourseId", query = "select c FROM Course c WHERE c.id=:cId")
public class Course {
    @Id
    @Column(name="id")
    private int cId;

    @Column(name="name")
    private String cName;

    @Column(name = "instructor")
    private String cInstructorName;

    @ManyToMany(cascade={CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_email", referencedColumnName = "email")
    )
    private List<Student> students;

    @Override
    public String toString() {
        return "Course {" +
                "cId=" + cId +
                ", cName='" + cName + '\'' +
                ", cInstructorName='" + cInstructorName + '\'' +
                //", students=" + students +//movies.stream().map(Movie::getTitle).collect(Collectors.toList()) + '\''
                '}';
    }

    public Course(String cName, String cInstructorName) {
        this.cName = cName;
        this.cInstructorName = cInstructorName;
    }

    public Course() {
    }

    public void addStudent(Student student) {
        if (students == null) {
            students = new ArrayList<>();
        }
        students.add(student);
    }

    public void setCourses(List<Student> students) {
        this.students = students;
    }

    public List<Student> getCourses() {
        return students;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public int getcId() {
        return cId;
    }

    public String getcName() {
        return cName;
    }

    public String getcInstructorName() {
        return cInstructorName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public void setcInstructorName(String cInstructorName) {
        this.cInstructorName = cInstructorName;
    }
}
