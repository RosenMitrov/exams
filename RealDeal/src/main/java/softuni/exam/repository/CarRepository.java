package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Car;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("select c from Car as c " +
            " order by c.pictures.size desc, c.make asc ")
    Optional<List<Car>> findAllByOrderPicturesCountDescMakeAsc();
}
