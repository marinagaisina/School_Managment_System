package jpa.entitymodels;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
                "Id=" + cId +
                ", Name='" + cName + '\'' +
                ", InstructorName='" + cInstructorName + '\'' +
                //", Students: " + Arrays.toString(students.toArray()) + '\''+
                '}';
    }

    public Course(Integer cId, String cName, String cInstructorName) {
        this.cId = cId;
        this.cName = cName;
        this.cInstructorName = cInstructorName;
        this.students = new ArrayList<>();
    }

    public Course() {
        this.cId = 0;
        this.cName = "";
        this.cInstructorName = "";
        this.students = new ArrayList<>();
    }

    public String getcInstructorName() {
        return cInstructorName;
    }

    public void setcInstructorName(String cInstructorName) {
        this.cInstructorName = cInstructorName;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
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

    public void setcName(String cName) {
        this.cName = cName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return getcName().equals(course.getcName())
                && getcInstructorName().equals(course.getcInstructorName())
                && getcId() == course.getcId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getcId(), getcName(), getcInstructorName());
    }
}
