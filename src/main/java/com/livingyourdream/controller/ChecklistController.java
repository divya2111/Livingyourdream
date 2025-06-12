package com.livingyourdream.controller;

import com.livingyourdream.model.Checklist;
import com.livingyourdream.model.ChecklistItem;
import com.livingyourdream.service.Checklistservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checklist")
public class ChecklistController {

    @Autowired
    private Checklistservice checklistservice;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create/{bookingId}")
    public ResponseEntity<Checklist> createChecklist(@PathVariable Long bookingId) {
        Checklist checklist = checklistservice.createChecklist(bookingId);
        return ResponseEntity.ok(checklist);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<List<ChecklistItem>> getChecklistItems(@PathVariable Long bookingId) {
        return ResponseEntity.ok(checklistservice.getChecklistItems(bookingId));
    }

    @PutMapping("/update-status/{itemId}")
    public ResponseEntity<ChecklistItem> updateItemStatus(@PathVariable Long itemId, @RequestParam boolean completed) {
        return ResponseEntity.ok(checklistservice.updateItemStatus(itemId, completed));
    }
}
