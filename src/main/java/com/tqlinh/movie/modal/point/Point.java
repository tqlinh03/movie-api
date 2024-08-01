package com.tqlinh.movie.modal.point;

import com.tqlinh.movie.modal.exchange.Exchange;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "point")
@EntityListeners(AuditingEntityListener.class)
public class Point {
    @Id
    @GeneratedValue
    private Integer id;

    private long point;

    @OneToMany(mappedBy = "point")
    List<Exchange> exchanges;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;
}
