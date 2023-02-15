package com.example.todolist.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "todolist")
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @Column(name = "description")
    private String description;
}
