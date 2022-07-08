package com.mangjakseon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WriterDTO {
    private String memberId;
    private String nickname;
    private String profileImage;
}