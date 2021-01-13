package cn.edu.pku.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageInfo {

    private int id;

    private String name;

    private String tag;

    private String imageId;

    private String createTime;
}
