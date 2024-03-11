package com.ismail.school;

import com.ismail.school.client.StudentClient;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
//@NoArgsConstructor    // to check
//@AllArgsConstructor
public class SchoolService {

//    @Autowired
    private final SchoolRepository repository;
    private final StudentClient client;

    public School saveSchool(School school) {
        //create object after logg then save the entity
        return repository.save(school);
    }//cerate school name add logger  param

    public List<School> findAllSchools() {
        return repository.findAll();
    }  //pagination  use cache filter  name of the service

    public FullSchoolResponse findSchoolsWithStudents(Integer schoolId) {
        var school = repository.findById(schoolId)
                .orElse(
                        School.builder()
                                .name("NOT_FOUND")
                                .email("NOT_FOUND")
                                .build()
                );
        var students = client.findAllStudentsBySchool(schoolId);
        return FullSchoolResponse.builder()
                .name(school.getName())
                .email(school.getEmail())
                .students(students)
                .build();
    }
}
