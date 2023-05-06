package br.com.sw2you.realmeet.domain.entity;

import java.time.OffsetDateTime;
import java.util.Objects;

import br.com.sw2you.realmeet.domain.model.Employee;

public class Allocation {

    private Long id;
    private Room room;
    private Employee employee;
    private String subject;
    private OffsetDateTime startAt;
    private OffsetDateTime endAt;
    private OffsetDateTime updateAt;

    public Allocation(Builder builder) {
        id = builder.id;
        room = builder.room;
        employee = builder.employee;
        subject = builder.subject;
        startAt = builder.startAt;
        endAt = builder.endAt;
        updateAt = builder.updateAt;
    }

    public Allocation() {}

    public Long getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getSubject() {
        return subject;
    }

    public OffsetDateTime getStartAt() {
        return startAt;
    }

    public OffsetDateTime getEndAt() {
        return endAt;
    }

    public OffsetDateTime getUpdateAt() {
        return updateAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Allocation that = (Allocation) o;
        return id.equals(that.id) && room.equals(that.room) && employee.equals(that.employee) && subject.equals(that.subject) && startAt.equals(that.startAt) && endAt.equals(that.endAt) && updateAt.equals(that.updateAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, room, employee, subject, startAt, endAt, updateAt);
    }

    @Override
    public String toString() {
        return "Allocation{" +
                "id=" + id +
                ", room=" + room +
                ", employee=" + employee +
                ", subject='" + subject + '\'' +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                ", updateAt=" + updateAt +
                '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }
    public static final class Builder {
        private Long id;
        private Room room;
        private Employee employee;
        private String subject;
        private OffsetDateTime startAt;
        private OffsetDateTime endAt;
        private OffsetDateTime updateAt;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder room(Room room) {
            this.room = room;
            return this;
        }

        public Builder employee(Employee employee) {
            this.employee = employee;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder startAt(OffsetDateTime startAt) {
            this.startAt = startAt;
            return this;
        }

        public Builder endAt(OffsetDateTime endAt) {
            this.endAt = endAt;
            return this;
        }

        public Builder updateAt(OffsetDateTime updateAt) {
            this.updateAt = updateAt;
            return this;
        }

        public Allocation build() {
            return new Allocation(this);
        }
    }
}
