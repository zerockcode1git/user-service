package org.zerock.user_service.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "tbl_user")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userId")
@ToString
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

 @Id
 @Column(name = "user_id", length = 50)
 private String userId;

 @Column(name = "userpw", nullable = false)
 private String userPw;

 @Column(name = "username", nullable = false, length = 100)
 private String username;

 @Column(name = "rolename", nullable = false, length = 50)
 private String rolename;

 @CreatedDate
 @Column(name = "reg_date", updatable = false)
 private LocalDateTime regDate;

 @LastModifiedDate
 @Column(name = "update_date")
 private LocalDateTime updateDate;
}

