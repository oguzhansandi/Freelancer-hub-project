package com.freelance.model.message;

import com.freelance.model.common.BaseEntity;
import com.freelance.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    private boolean isRead = false;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String attachmentOriginalName;

    private String attachmentType;

    private String attachmentUrl;

    private boolean isDeleted = false;

    private boolean isEdited = false;

    private Date editedAt;
}
