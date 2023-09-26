package org.exercise.java.springlamiapizzeriacrud.repository;

import org.exercise.java.springlamiapizzeriacrud.model.SpecialOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<SpecialOffer, Integer> {
}
