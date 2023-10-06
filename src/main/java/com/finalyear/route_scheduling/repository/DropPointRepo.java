package com.finalyear.route_scheduling.repository;

import com.finalyear.route_scheduling.entity.DropPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface DropPointRepo extends JpaRepository<DropPoint,Integer> {
    @Query(nativeQuery = true,value = "SELECT * FROM ktm")
    Set<DropPoint> allLocations();


    @Query(nativeQuery = true,value = "SELECT * FROM ktm WHERE id IN (:selectedIds)")
    Set<DropPoint> selectedLocations(@Param("selectedIds") Set<Integer> selectedIds);

    @Query(nativeQuery = true,value = "SELECT * FROM ktm WHERE isWarehouse = true")
    Set<DropPoint> warehouses();

}
