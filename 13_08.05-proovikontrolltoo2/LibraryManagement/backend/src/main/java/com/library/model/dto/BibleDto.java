package com.library.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BibleDto {
    private Long bible_id;
    private String language;
    private String version;
}
