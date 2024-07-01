package com.example.cellphones.model.parking;


import com.example.cellphones.model.base.BaseModel;
import com.example.cellphones.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.CascadeType.ALL;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "tbl_employee")
public class Employee extends BaseModel {

    @OneToOne(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    private String fullName;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    private String position;
}
