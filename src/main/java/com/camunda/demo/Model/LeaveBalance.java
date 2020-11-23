package com.camunda.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Whatmore
 * @created 23/11/2020 - 14:40
 * @project my-project
 */
@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Builder
@Table(name = "leave_balance")
public class LeaveBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private String employee_number;
    private double opening_leave_balance;
    private double closing_leave_balance;
    private double days_taken;
}
