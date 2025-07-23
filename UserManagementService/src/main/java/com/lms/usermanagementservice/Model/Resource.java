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
@Table(
        name = "resource",
        indexes = {
                @Index(name = "idx_resource_name", columnList = "resourceName")
        }
)
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resource_name", nullable = false,unique = true)
    @Enumerated(EnumType.STRING)
    private ResourcesName resourceName;

    @Column(name = "created_date")
    @CreationTimestamp
    private ZonedDateTime createdDate;

    @Column(name = "last_modified_date")
    @UpdateTimestamp
    private ZonedDateTime lastModifiedDate;

    public String getResorceNameString(){
        return resourceName.toString();
    }
}
