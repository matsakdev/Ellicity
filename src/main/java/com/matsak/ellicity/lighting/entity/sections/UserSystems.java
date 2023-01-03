package com.matsak.ellicity.lighting.entity.sections;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class UserSystems {

    @Embeddable
    public static class Id implements Serializable {
        @Column(name = "USER_ID")
        private Long userId;

        @Column(name = "SYSTEM_ID")
        private Long systemId;

        public Id() {
        }

        public Id(Long userId, Long systemId) {
            this.userId = userId;
            this.systemId = systemId;
        }

        public Long getUserId() {
            return userId;
        }

        public Long getSystemId() {
            return systemId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public void setSystemId(Long systemId) {
            this.systemId = systemId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Id)) return false;
            Id id = (Id) o;
            return Objects.equals(userId, id.userId) && Objects.equals(systemId, id.systemId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, systemId);
        }
    }

    @EmbeddedId
    private Id id = new Id();
    @ManyToOne
    @JoinColumn(
            name = "SYSTEM_ID",
            insertable = false, updatable = false
    )
    private System system;

    public UserSystems() {
    }

    public UserSystems(Long userId, System system) {
        this.system = system;

        this.id = new Id(userId, system.getId());
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }
}
