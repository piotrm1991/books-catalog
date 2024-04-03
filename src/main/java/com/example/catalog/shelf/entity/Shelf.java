package com.example.catalog.shelf.entity;

import com.example.catalog.room.entity.Room;
import lombok.*;

import javax.persistence.*;

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

  @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(name = "id_room")
  private Room room;
}