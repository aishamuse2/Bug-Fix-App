package com.devmountain.bugFixApp.controllers;

import com.devmountain.bugFixApp.dtos.BugDto;
import com.devmountain.bugFixApp.services.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/bugs")
public class BugController{
    @Autowired
    private BugService bugService;

    @GetMapping("/bug/{userId}/all")
    public List<BugDto> getBugsByUser(@PathVariable Long userId){
        return bugService.getAllBugsByUserId(userId);
    }

    @GetMapping("/bug/{userId}/tagged")
    public List<BugDto> getTaggedBugsByUser(@PathVariable Long userId){
        return bugService.getAllTaggedBugsByUserId(userId);
    }

    @GetMapping("/bug/{userId}/created")
    public List<BugDto> getCreatedBugsByUser(@PathVariable Long userId){
        return bugService.getAllCreatedBugsByUserId(userId);
    }

    @GetMapping("/bug")
    public List<BugDto> getAllBugs(){
        return bugService.getAllBugs();
    }

    @GetMapping("/bug/{bugId}")
    public Optional<BugDto> getBugById(@PathVariable Long bugId){
        return bugService.getBugById(bugId);

    }


    @PostMapping("/create/{createdById}")
    public void createBug(@RequestBody BugDto bugDto,@PathVariable Long createdById){
        bugService.createBug(bugDto, createdById);
    }


    @PutMapping("/{bugId}/update/{createdById}")
    public void updateBug(@RequestBody BugDto bugDto, @PathVariable Long bugId, @PathVariable Long createdById){
       bugService.updateBugById(bugDto, bugId, createdById);
    }
    @PutMapping("/{bugId}/resolve/{taggedUserId}")
    public void resolveBug(@PathVariable Long bugId, @PathVariable Long taggedUserId){
        bugService.resolveBugById(bugId, taggedUserId);
    }
}

