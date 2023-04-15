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


    @Override
    Optional<Ticket> findById(Long id);


    List<Ticket> findAllByUser(User user);
    List<Ticket> findAllByFlight_FirmOwner(String company);

    List<Ticket> findAllByFlight_Id(Long id);

    Optional<Ticket> findByFlight_Id(Long flightId);
    List<Ticket> findAllByFlight_TimeOfFlight(LocalDateTime time);


}
