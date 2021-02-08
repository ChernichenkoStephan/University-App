package org.university.Controller;

import org.university.Model.*;
import org.university.DBLayer.Exeptions.*;
import org.university.DBLayer.Repositories.*;
import org.university.DBLayer.Repositories.Interfaces.*;

import java.util.Collection;
import java.util.Optional;
import java.util.Vector;

public class DatabaseService {

    private static final TeacherDatabase teacherRepository = new TeacherRepository();
    private static final DepartmentDatabase departmentRepository = new DepartmentRepository();
    private static final GroupDatabase groupRepository = new GroupRepository();
    private static final ClassroomDatabase classroomRepository = new ClassroomRepository();
    private static final SubjectDatabase subjectRepository = new SubjectRepository();
    private static final ClassDatabase classRepository = new ClassRepository(teacherRepository,
            departmentRepository,
            groupRepository,
            classroomRepository,
            subjectRepository);
    private static final RequestsRepository repository = new RequestsRepository();

    public static Teacher getTeacher(int id) throws NonExistentEntityException {
        Optional<Teacher> teacher = teacherRepository.get(id);
        return teacher.orElseThrow(NonExistentTeacherException::new);
    }

    public static Collection<Teacher> getAllTeachers() {
        return teacherRepository.getAll();
    }

    public static void updateTeacher(Teacher teacher) {
        teacherRepository.update(teacher);
    }

    public static Optional<Integer> addTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public static void deleteTeacher(Teacher teacher) {
        teacherRepository.delete(teacher);
    }

    public static Collection<Teacher> mostExperienced() { return teacherRepository.getMostExperienced(); }

    public static Integer teachersAmount() { return teacherRepository.getAmount(); }

    public static Optional<Teacher> maxExperienced() {
        return teacherRepository.getMaxExperienced();
    }

    public static Boolean professorize() {
        return teacherRepository.professorize();
    }

    // ---------------------- Department ----------------------

    public static Department getDepartment(int id) throws NonExistentEntityException {
        Optional<Department> department = departmentRepository.get(id);
        return department.orElseThrow(NonExistentDepartmentException::new);
    }

    public static Collection<Department> getAllDepartments() {
        return departmentRepository.getAll();
    }

    public static void updateDepartment(Department department) {
        departmentRepository.update(department);
    }

    public static Optional<Integer> addDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public static void deleteDepartment(Department department) {
        departmentRepository.delete(department);
    }

    // ---------------------- Group ----------------------

    public static Group getGroup(int id) throws NonExistentEntityException {
        Optional<Group> group = groupRepository.get(id);
        return group.orElseThrow(NonExistentGroupException::new);
    }

    public static Collection<Group> getAllGroups() {
        return groupRepository.getAll();
    }

    public static void updateGroup(Group group) {
        groupRepository.update(group);
    }

    public static Optional<Integer> addGroup(Group group) {
        return groupRepository.save(group);
    }

    public static void deleteGroup(Group group) {
        groupRepository.delete(group);
    }

    // ---------------------- Classroom ----------------------

    public static Classroom getClassroom(int id) throws NonExistentEntityException {
        Optional<Classroom> classroom = classroomRepository.get(id);
        return classroom.orElseThrow(NonExistentClassroomException::new);
    }

    public static Collection<Classroom> getAllClassrooms() {
        return classroomRepository.getAll();
    }

    public static void updateClassroom(Classroom classroom) {
        classroomRepository.update(classroom);
    }

    public static Optional<Integer> addClassroom(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    public static void deleteClassroom(Classroom classroom) {
        classroomRepository.delete(classroom);
    }

    public static Collection<Classroom> getLectures() {
        return classroomRepository.getLectures();
    }

    public static Collection<Classroom> getPractices() {
        return classroomRepository.getPractices();
    }

    public static Collection<Classroom> getLaboratories() { return classroomRepository.getLaboratories(); }

    public static Collection<Classroom> getComputers() {
        return classroomRepository.getComputers();
    }

    public static Collection<Classroom> getSmall() {
        return classroomRepository.getSmall();
    }

    // ---------------------- Subject ----------------------

    public static Subject getSubject(int id) throws NonExistentEntityException {
        Optional<Subject> subject = subjectRepository.get(id);
        return subject.orElseThrow(NonExistentSubjectException::new);
    }

    public static Collection<Subject> getAllSubjects() {
        return subjectRepository.getAll();
    }

    public static void updateSubject(Subject subject) {
        subjectRepository.update(subject);
    }

    public static Optional<Integer> addSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public static void deleteSubject(Subject subject) {
        subjectRepository.delete(subject);
    }

    public static Integer fullCourse() {
        return subjectRepository.getFullCourse();
    }

    // ---------------------- Event ----------------------

    public static Event getClass(int id) throws NonExistentEntityException {
        Optional<Event> subject = classRepository.get(id);
        return subject.orElseThrow(NonExistentEventException::new);
    }

    public static Collection<Event> getAllClasses() {
        return classRepository.getAll();
    }

    public static void updateClass(Event event) {
        classRepository.update(event);
    }

    public static Optional<Integer> addClass(Event event) {
        return classRepository.save(event);
    }

    public static void deleteClass(Event event) {
        classRepository.delete(event);
    }

    public static Collection<Event> getClassesByTime() {
        return classRepository.classesByTime();
    }

    // ---------------------- Requests ----------------------

    public static CustomData allPlaces() {
        return repository.allPlaces();
    }

    public static CustomData universityUnits() {
        return repository.universityUnits();
    }

    public static CustomData universityObjects() {
        return repository.universityObjects();
    }

    public static CustomData mostClasses() {
        return repository.mostClasses();
    }

    public static CustomData aboveAvgTeachers() {
        return repository.aboveAvgTeachers();
    }

    public static CustomData enoughClass() {
        return repository.enoughClass();
    }

    public static class CustomData {
        private final Vector<String> columnNames;
        private final Vector<Vector<Object>> body;

        public CustomData(Vector<String> columnNames, Vector<Vector<Object>> body) {
            this.columnNames = columnNames;
            this.body = body;
        }

        public Vector<String> getColumnNames() {
            return columnNames;
        }

        public Vector<Vector<Object>> getBody() {
            return body;
        }

        @Override
        public String toString() {
            return "CustomData{" +
                    "columnNames=" + columnNames +
                    ",\n body=" + body +
                    '}';
        }
    }

}
