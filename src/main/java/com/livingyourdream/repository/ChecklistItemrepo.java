package com.livingyourdream.repository;

import com.livingyourdream.model.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChecklistItemrepo extends JpaRepository<ChecklistItem,Long> {
    List<ChecklistItem> findByChecklist_ChecklistId(Long ChecklistId);
}
