package com.devmountain.bugFixApp.services;

import com.devmountain.bugFixApp.dtos.BugDto;
import com.devmountain.bugFixApp.entities.Bug;
import com.devmountain.bugFixApp.entities.User;
import com.devmountain.bugFixApp.repositories.BugRepository;
import com.devmountain.bugFixApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Service
public class BugServiceImpl implements BugService {
    @Autowired
    private BugRepository bugRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Bug createBug(BugDto bugDto, Long createdById) {
        List<String> response = new ArrayList<>();
        bugDto.setCreatedById(createdById);
        bugDto.setStatus("OPEN");
        Bug bug = new Bug(bugDto);
        return bugRepository.saveAndFlush(bug);
    }


    @Override
    @Transactional
    public BugDto updateBugById(BugDto bugDto, Long bugId, Long createdById) {
        Optional<Bug> bugOptional = bugRepository.findById(bugId);
        if(bugOptional.isPresent() && bugOptional.get().getCreatedById() == createdById) {
           Bug bug= bugOptional.get();
           bug.setTaggedUserId(bugDto.getTaggedUserId());
           return new BugDto(bugRepository.saveAndFlush(bug));
        }
        return null;
    }

    @Override
    @Transactional
    public void resolveBugById(Long bugId, Long taggedUserId) {
        Optional<Bug> bugOptional = bugRepository.findById(bugId);
        if (bugOptional.get().getTaggedUserId() == taggedUserId) {
            bugOptional.ifPresent(bug -> {
                bug.setStatus("CLOSED");
                bugRepository.saveAndFlush(bug);
            });
        }
    }

    @Override
    public Optional<BugDto> getBugById(Long bugId) {
        Optional<Bug> bugOptional = bugRepository.findById(bugId);
        if (bugOptional.isPresent()) {
            return Optional.of(new BugDto(bugOptional.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<BugDto> getAllCreatedBugsByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            List<Bug> bugList = bugRepository.findAll();
            List<BugDto> userCreatedBugs = new ArrayList<>();
            for (int i = 0; i < bugList.size(); i++) {
                if (bugList.get(i).getCreatedById() == userId) {
                    userCreatedBugs.add(new BugDto(bugList.get(i)));

                }

            }
            return userCreatedBugs;
        }

        return Collections.emptyList();
    }

    @Override
    public List<BugDto> getAllTaggedBugsByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            List<Bug> bugList = bugRepository.findAll();
            List<BugDto> userTaggedBugs = new ArrayList<>();
            for (int i = 0; i < bugList.size(); i++) {
                if (bugList.get(i).getTaggedUserId() == userId) {
                    userTaggedBugs.add(new BugDto(bugList.get(i)));

                }

            }
            return userTaggedBugs;
        }
        return Collections.emptyList();
    }
    @Override
    public List<BugDto> getAllBugsByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            List<Bug> bugList = bugRepository.findAll();
            List<BugDto> userBugs = new ArrayList<>();
            for (int i = 0; i < bugList.size(); i++) {
                if (bugList.get(i).getTaggedUserId() == userId) {
                    userBugs.add(new BugDto(bugList.get(i)));

                }
                else if (bugList.get(i).getCreatedById() == userId) {
                    userBugs.add(new BugDto(bugList.get(i)));

                }

            }
            return userBugs;
        }
        return Collections.emptyList();
    }
    @Override
    public List<BugDto> getAllBugs(){
        List<Bug> bugList = bugRepository.findAll();
        return bugList.stream().map(bug -> new BugDto(bug)).collect(Collectors.toList());
    }
}




