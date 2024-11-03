package net.happykoo.entity;

import lombok.*;
import net.happykoo.constant.RoleType;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
//테이블 명 수정 시
@Table(name = "member", uniqueConstraints = {
    @UniqueConstraint(name = "NAME_AGE_UNIQUE", columnNames = { "NAME", "AGE" })
})
//수정된 부분만 update 문에 포함
//@DynamicUpdate
public class Member {
    @Id
    private String id;
    @Column(name = "NAME", nullable = false, length = 10)
    private String userName;
    private int age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;
}