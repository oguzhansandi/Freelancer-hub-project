package com.freelance.repository;

import com.freelance.model.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByChatIdOrderByCreateDateAsc(Long chatId);

    @Query("SELECT u FROM Message u WHERE u.chat.id = :chatId AND u.isRead = false AND u.sender.id <> :userId")
    List<Message> findUnreadMessages(@Param("chatId") Long chatId, @Param("userId") Long userId);
}
