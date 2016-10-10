package com.demo.limits.domain.core;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lnv.
 */
@Entity
@Table(name="PROJECT_ITEM")
public class ProjectItem {

    @EmbeddedId
    PK id;

    @Embeddable
    public static class PK implements Serializable {

        @Column(name = "COLLECTION_ID", nullable = false)
        private Long collectionId;

        @Column(name = "ITEM_TYPE", nullable = false)
        private String itemType;

        @Column(name = "ITEM_REF_ID", nullable = false)
        private Long itemRefId;

        public PK() {
        }

        public PK(Long collectionId, String itemType, Long itemRefId) {
            this.collectionId = collectionId;
            this.itemType = itemType;
            this.itemRefId = itemRefId;
        }

        public Long getCollectionId() {
            return collectionId;
        }

        public void setCollectionId(Long collectionId) {
            this.collectionId = collectionId;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public Long getItemRefId() {
            return itemRefId;
        }

        public void setItemRefId(Long itemRefId) {
            this.itemRefId = itemRefId;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            PK pk = (PK) obj;

            if (!collectionId.equals(pk.collectionId)) return false;
            if (!itemRefId.equals(pk.itemRefId)) return false;
            if (!itemType.equals(pk.itemType)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = collectionId.hashCode();
            result = 31 * result + itemType.hashCode();
            result = 31 * result + itemRefId.hashCode();
            return result;
        }
    }

    public ProjectItem() { }

    public ProjectItem(PK id) {
        this.id = id;
    }

    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }

}
