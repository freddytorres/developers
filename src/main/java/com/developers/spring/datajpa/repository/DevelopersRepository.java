package com.developers.spring.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.developers.spring.datajpa.model.Developers;

@Repository
public interface DevelopersRepository extends JpaRepository<Developers, Long> {
}