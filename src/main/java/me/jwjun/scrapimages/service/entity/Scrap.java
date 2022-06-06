package me.jwjun.scrapimages.service.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "t_scrap")
public class Scrap extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long scrapId;

    @Column
    Long userId;

    @Column
    String title;

    @Column
    String description;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "imageId")
    ImageInfo imageInfo;

    @Column
    Long viewCnt;

    @PrePersist
    public void init(){
        this.viewCnt = 0L;
    }
}
