package com.freelance.model.message;

import com.freelance.model.common.BaseEntity;
import com.freelance.model.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chat extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "participant1", nullable = false)
    private User participant1;

    @ManyToOne
    @JoinColumn(name = "participant2", nullable = false)
    private User participant2;


}
