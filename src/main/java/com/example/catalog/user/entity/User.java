package com.example.catalog.user.entity;

import com.example.catalog.date.deserialization.LocalDateDeserializer;
import com.example.catalog.date.serialization.LocalDateSerializer;
import com.example.catalog.user.enums.UserRoleEnum;
import com.example.catalog.user.enums.UserStatusEnum;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a user in the system.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column
  private String login;

  @Column
  private String password;

  @Column
  @Enumerated(value = EnumType.STRING)
  private UserRoleEnum role;

  @Column
  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate createDate;

  @Column
  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate updateDate;

  @Column
  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate deleteDate;

  @Column
  @Enumerated(value = EnumType.STRING)
  private UserStatusEnum status;
}
