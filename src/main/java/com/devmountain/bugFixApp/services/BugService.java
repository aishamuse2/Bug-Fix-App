package com.devmountain.bugFixApp.services;

import com.devmountain.bugFixApp.dtos.BugDto;
import com.devmountain.bugFixApp.entities.Bug;

import java.util.List;
import java.util.Optional;

public interface BugService {
    Bug createBug(BugDto bugDto , Long createdById);
    BugDto updateBugById(BugDto bugDto ,Long bugId,Long createdById);
    void resolveBugById (Long bugId,Long createdById);
    Optional<BugDto> getBugById(Long bugId);
    List<BugDto> getAllTaggedBugsByUserId(Long userId);
    List<BugDto> getAllCreatedBugsByUserId(Long userId);
    List<BugDto> getAllBugs();
    List<BugDto> getAllBugsByUserId(Long userId);

}
