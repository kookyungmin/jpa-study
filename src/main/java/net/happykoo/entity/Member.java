package net.happykoo.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MEMBER")
//수정된 부분만 update 문에 포함
//@DynamicUpdate
public class Member {
    @Id
    private String id;
    @Column(name = "NAME")
    private String userName;
    private int age;
}