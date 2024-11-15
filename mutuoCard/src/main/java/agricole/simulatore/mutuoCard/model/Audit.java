package agricole.simulatore.mutuoCard.model;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import lombok.Setter;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Audit implements Auditable<String, Long, LocalDateTime>, Serializable {

    @CreatedBy
    @Column(updatable = false, name = "CREATED_BY")
    protected String createdBy;

    @CreatedDate
    @Column(updatable = false, name="CREATED_DATE")
    protected LocalDateTime createdDate;

    @LastModifiedBy
    @Column(name="LAST_MODIFIED_BY")
    protected String lastModifiedBy;

    @LastModifiedDate
    @Column(name="LAST_MODIFIED_DATE")
    protected LocalDateTime lastModifiedDate;

    public Optional<String> getCreatedBy() {
        return Optional.ofNullable(createdBy);
    }

    public Optional<LocalDateTime> getCreatedDate() {
        return Optional.ofNullable(createdDate);
    }

    public Optional<String> getLastModifiedBy() {
        return Optional.ofNullable(lastModifiedBy);
    }

    public Optional<LocalDateTime> getLastModifiedDate() {
        return Optional.ofNullable(lastModifiedDate);
    }

    @Override
    public boolean isNew() {
        return Objects.isNull(getId());
    }
}