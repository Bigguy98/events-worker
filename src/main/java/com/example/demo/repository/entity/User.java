package com.example.demo.repository.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



import jakarta.persistence.*;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user_account")
public class User extends BaseEntity {

    @Column(name = "wallet_id")
    private String walletId;

    @Column(name = "name")
    private String name;

    @Column(name = "birthdate")
    private Date birthDate;

    @Column(name = "gender")
    private String gender;

    @Column(name = "email")
    private String email;
}
