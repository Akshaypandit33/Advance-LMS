package com.lms.usermanagementservice.Model;

import com.LMS.Constants.ACTIONS;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Builder
public class Actions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ACTIONS action;

    private String descriptions;

    @PrePersist
    @PreUpdate
    public void prePersistAndPreUpdate() {
        action = ACTIONS.valueOf(action.toString().toUpperCase());
    }

    public String getActionString(){
        return action.toString();
    }

}
