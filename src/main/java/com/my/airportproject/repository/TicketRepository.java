package com.my.airportproject.repository;


import com.my.airportproject.model.entity.Ticket;
import com.my.airportproject.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByUser_Email(String email);

    List<Ticket> findAllByFlight_Id(Long id);

}
