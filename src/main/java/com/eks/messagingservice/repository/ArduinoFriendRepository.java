package com.eks.messagingservice.repository;

import com.eks.messagingservice.models.ArduinoFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArduinoFriendRepository extends JpaRepository<ArduinoFriend, Long> {

    @Query("SELECT " +
            "CASE " +
            "   WHEN af.arduino1.arduinoId = :arduinoId THEN af.arduino2.arduinoId " +
            "   ELSE af.arduino1.arduinoId " +
            "END " +
            "FROM ArduinoFriend af " +
            "JOIN af.arduino1 a1 " +
            "JOIN af.arduino2 a2 " +
            "WHERE (a1.arduinoId = :arduinoId OR a2.arduinoId = :arduinoId) " +
            "AND a1.status = 'online'")
    List<String> findOnlineFriendIdsByArduinoId(@Param("arduinoId") String arduinoId);

}
