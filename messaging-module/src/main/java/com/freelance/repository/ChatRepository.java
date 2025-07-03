package com.freelance.repository;

import com.freelance.model.message.Chat;
import com.freelance.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByParticipant1AndParticipant2(User participant1, User participant2);

    Optional<Chat> findByParticipant2AndParticipant1(User participant2, User participant1);

    @Query("SELECT c FROM Chat c WHERE " +
            "(c.participant1 = :user1 AND c.participant2 = :user2) OR " +
            "(c.participant1 = :user2 AND c.participant2 = :user1)")
    Optional<Chat> findByParticipants(@Param("user1") User user1, @Param("user2") User user2);

    @Query("SELECT c FROM Chat c WHERE c.participant1.id = :userId OR c.participant2.id = :userId")
    List<Chat> findAllByUserId(@Param("userId") Long userId);
}
