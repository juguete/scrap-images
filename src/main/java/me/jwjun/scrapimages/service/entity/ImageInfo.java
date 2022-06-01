package me.jwjun.scrapimages.service.entity;

import lombok.*;

import javax.persistence.*;
import java.net.URL;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "t_image")
public class ImageInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long imageId;

    @Column
    String url;

    @Column
    String localPath;

    @Column
    Boolean isDownloaded;

    @Column
    Integer downloadTryCnt;

    @Column
    Integer width;

    @Column
    Integer height;

    @Column
    String mimeType;

    @PrePersist
    public void prePersist(){
        this.isDownloaded = false;
        this.downloadTryCnt = 0;
    }
}
