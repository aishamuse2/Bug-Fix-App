package com.devmountain.bugFixApp.dtos;

import com.devmountain.bugFixApp.entities.Bug;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class BugDto implements Serializable {
    private Long id;
    private Long createdById;
    private Long taggedUserId;
    private String name;
    private String description;
    private String resolution;
    private String status;

    public BugDto(Bug bug) {
        if (bug.getCreatedById() != null) {
            this.createdById = bug.getCreatedById();
        }
        if (bug.getId() != null) {
            this.id = bug.getId();
        }
        if (bug.getTaggedUserId() != null) {
            this.taggedUserId = bug.getTaggedUserId();
        }

        if (bug.getName() != null) {
            this.name = bug.getName();
        }

        if (bug.getDescription() != null) {
            this.description = bug.getDescription();
        }
        if (bug.getResolution() != null) {
            this.resolution = bug.getResolution();
        }
        if (bug.getStatus() != null) {
            this.status = bug.getStatus();
        }


    }
}

