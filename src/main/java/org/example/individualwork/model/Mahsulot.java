package org.example.individualwork.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Table
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mahsulot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mahsulot_seq")
    @SequenceGenerator(name = "mahsulot_seq", sequenceName = "mahsulot_sequence", initialValue = 100000, allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sotuvchi_id", nullable = false)
    private Sotuvchi sotuvchi;

    @Column(nullable = false, length = 25)
    private String title;
    private String body;
    private String image;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Double discount;

    @Column(nullable = false)
    private LocalDateTime fromDate;

    @Column(nullable = false)
    private LocalDateTime toDate;

    @CreationTimestamp
    private LocalDateTime created_at;
}
