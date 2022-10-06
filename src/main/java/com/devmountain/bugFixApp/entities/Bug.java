package com.devmountain.bugFixApp.entities;

import com.devmountain.bugFixApp.dtos.BugDto;
import com.devmountain.bugFixApp.dtos.UserDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Bug")
public class Bug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long createdById;
    @Column
    private Long taggedUserId;
    @Column
    private String name;
    @Column(columnDefinition = "text")
    private String description;
    @Column(columnDefinition = "text")
    private String resolution ;
    @Column
    private String status;

    public Bug(BugDto bugDto) {
        if(bugDto.getName() != null){
            this.name = bugDto.getName();
        }
        if(bugDto.getDescription() != null){
            this.description = bugDto.getDescription();
        }
        if(bugDto.getStatus() != null){
            this.status = bugDto.getStatus();
        }
        if(bugDto.getCreatedById() != null){
            this.createdById = bugDto.getCreatedById();
        }
        if(bugDto.getTaggedUserId() != null){
            this.taggedUserId = bugDto.getTaggedUserId();
        }
    }

}
