package com.camunda.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Whatmore
 * @created 23/11/2020 - 14:41
 * @project my-project
 */
@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Builder
@Table(name = "leave_monitoring")
public class LeaveMonitoring {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private String employee_number;
    private String fullname;
    private String leave_type;
    private String dept;
    private String status;
    private String approver;
    private String comments;
    private Date period_from;
    private Date period_to;
    private int number_of_days;
    @CreationTimestamp
    private LocalDateTime createDateTime;
    @UpdateTimestamp
    private LocalDateTime updateDateTime;

}
