package com.livingyourdream.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class ChecklistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String item;
    private boolean completed;
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "checklistId")
    @JsonIgnore
    private Checklist checklist;
}
