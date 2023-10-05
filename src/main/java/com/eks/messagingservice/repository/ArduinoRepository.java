package com.eks.messagingservice.repository;

import com.eks.messagingservice.models.Arduino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ArduinoRepository extends JpaRepository<Arduino, String> {
    @Modifying
    @Query("UPDATE Arduino a SET a.status = :newStatus WHERE a.arduinoId = :arduinoId")
    int updateStatusByArduinoId(@Param("arduinoId") String arduinoId, @Param("newStatus") String newStatus);

    @Modifying
    @Query("UPDATE Arduino a SET a.lastSeen = :newLastSeen WHERE a.arduinoId = :arduinoId")
    int updateLastSeenByArduinoId(@Param("arduinoId") String arduinoId, @Param("newLastSeen") Date newLastSeen);

}
