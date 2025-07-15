package com.lms.usermanagementservice.Model;

import com.LMS.Constants.ResourcesName;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ResourcesName resourceName;

    @CreationTimestamp
    private ZonedDateTime createdDate;
    @UpdateTimestamp

    private ZonedDateTime lastModifiedDate;

    public String getResorceNameString(){
        return resourceName.toString();
    }
}
