package com.gmail.podkutin.dmitry.voting_system.repository;

import com.gmail.podkutin.dmitry.voting_system.model.restaurant.Dish;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface DishRepository  extends JpaRepository<Dish, Integer>  {

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restaurantId")
    @CacheEvict(value="queryCacheServices",allEntries = true)
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);

    @Query("SELECT  DISTINCT d FROM Dish d WHERE d.restaurant.id=:restaurantId ORDER BY d.date")
    List<Dish> getAllForRestaurant(@Param("restaurantId") int restaurantId);

    @Override
    @Transactional
    @CacheEvict(value="queryCacheServices",allEntries = true)
    <S extends Dish> S save(S entity);
}
