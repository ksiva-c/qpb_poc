package com.demo.limits.domain.core;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="PROJECT")
@PrimaryKeyJoinColumn(name = "COLLECTION_ID")
public class EProject extends ECollection{

    public EProject(){
        super();
    }

    public EProject(
            String uuid,
            String name,
            String description,
            String parentId,
            Integer version
            ) {
        super(uuid, name, description, parentId, version);
    }

}
