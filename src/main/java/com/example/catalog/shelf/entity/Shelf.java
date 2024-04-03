package com.example.catalog.shelf.entity;

import com.example.catalog.room.entity.Room;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing shelf in the catalog.
 */
@Entity
@Table(name = "shelf")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Shelf {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String letter;

  @Column
  private Integer number;

  @ManyToOne(cascade = {
      CascadeType.MERGE,
      CascadeType.DETACH,
      CascadeType.PERSIST,
      CascadeType.REFRESH
      })
  @JoinColumn(name = "id_room")
  private Room room;
}