package com.mangjakseon.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Getter
@Setter
public class BoardDTO {
    private int bno;
    private String b_title;
    private String b_content;
    private int b_count;
    private String writer;
    private LocalDateTime regDate,modDate;
    private String iframe;
    private String s_img;

}
