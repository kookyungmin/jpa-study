package net.happykoo.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    AUTO 로 하는 경우 DBMS 에 따라 IDENTITY / SEQUENCE 맞게 설정 됨
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
