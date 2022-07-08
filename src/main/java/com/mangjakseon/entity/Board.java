package com.mangjakseon.entity;

import com.mangjakseon.utils.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "p_board")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bno;

    private String b_title;

    @Column(length = 1500)
    private String b_content;

    @Column
    private int b_count;

    private String writer;

    @Column(length = 1000)
    private String iframe;

    @Column(length = 300)
    private String s_img;


    public void changeTitle(String b_title){
        this.b_title = b_title;
    }

    public void changeContent(String b_content){
        this.b_content = b_content;
    }

    public void increaseViewCount(){
        this.b_count++;
    }

}
