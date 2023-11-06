package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Curriculum;

public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {

}