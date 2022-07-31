package com.bigshare.repository;

import com.bigshare.model.visitor.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    Visitor findByAddressAndDate(String address, LocalDate date);
}
