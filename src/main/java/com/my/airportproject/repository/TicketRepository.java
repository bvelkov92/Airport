package com.my.airportproject.repository;


import com.my.airportproject.model.entity.Flight;
import com.my.airportproject.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Override
    Optional<Ticket> findById(Long id);

    Optional<Ticket> findFirstByFlight_Id(Long id);

}
