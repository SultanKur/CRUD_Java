package com.example.demo.students;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }


    public void addNewStudent(Student student)
    {
        if (student.getFirst_name().isEmpty() || !student.getFirst_name().matches("\\D*")) {
            throw new IllegalStateException("Имя должно содержать только буквы");
        }
        if (student.getLast_name().isEmpty() || !student.getLast_name().matches("\\D*")) {
            throw new IllegalStateException("Фамилия должна содержать только буквы");
        }

        if (!student.getEmail().endsWith("@narxoz.kz")) {
            throw new IllegalStateException("Электронная почта должна быть @narxoz.kz");
        }

        Optional<Student> studentOptionalEmail = studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptionalEmail.isPresent()) {
            throw new IllegalStateException("Этот email уже используется ");
        }

        if (student.getDob().getYear() >= LocalDate.now().getYear()) {
            throw new IllegalStateException("Проверьте дату рождения, вы неверно ввели данные");
        }

        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new IllegalStateException(
                    "студент с id = " + studentId + " не найден");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String first_name, String last_name, LocalDate dob, String speciality, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "студент с id = " + studentId + " не найден"));

        if (first_name != null) {
            if (first_name.matches("\\D*") && first_name.length() > 0) {
                student.setFirst_name(first_name);
            } else {
                throw new IllegalStateException("Вы неверно ввели Имя");
            }
        }

        if (last_name != null) {
            if (last_name.matches("\\D*") && last_name.length() > 0) {
                student.setLast_name(last_name);
            } else {
                throw new IllegalStateException("Вы неверно ввели Фамилию");
            }
        }

        if (dob != null) {
            if (dob.getYear() < LocalDate.now().getYear()) {
                student.setDob(dob);
            } else {
                throw new IllegalStateException("Проверьте дату рождения, вы неверно ввели данные");
            }
        }

        if (speciality != null) {
            if (speciality.length() > 0) {
                student.setSpeciality(speciality);
            } else {
                throw new IllegalStateException("Вы не указали специальность, проверьте данные");
            }
        }

        if (email != null) {
            Optional<Student> studentOptionalEmail = studentRepository.
                    findStudentByEmail(email);
            if (studentOptionalEmail.isPresent()) {
                throw new IllegalStateException("email taken");
            }
            if (email.endsWith("@narxoz.kz")) {
                student.setEmail(email);
            } else {
                throw new IllegalStateException("Проверьте правильно ли вы указали почту, она должна быть @narxoz.kz");
            }
        }
    }
}

