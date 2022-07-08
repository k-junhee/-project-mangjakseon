package com.mangjakseon.service;

import com.mangjakseon.dto.BoardDTO;
import com.mangjakseon.dto.PageRequestDTO;
import com.mangjakseon.dto.PageResultDTO;
import com.mangjakseon.entity.Board;
import java.util.List;


public interface BoardService {


    Integer register(BoardDTO boardDTO); // 글 등록

    PageResultDTO<BoardDTO, Board> getList(PageRequestDTO requestDTO); // 페이징 처리

    List<Board> boardList();

    BoardDTO read(Integer bno); // 상세보기


    int updateView(Integer bno); // 조회수

    void remove(Integer bno); // 글 삭제

    void modify(BoardDTO dto); // 글 수정


    default Board dtoToEntity(BoardDTO boardDTO){
        Board entity = Board.builder()
                .bno(boardDTO.getBno())
                .b_title(boardDTO.getB_title())
                .b_content(boardDTO.getB_content())
                .b_count(boardDTO.getB_count())
                .writer(boardDTO.getWriter())
                .iframe(boardDTO.getIframe())
                .s_img(boardDTO.getS_img())
                .build();
        return entity;
    }

    default BoardDTO entityToDto (Board entity){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(entity.getBno())
                .b_title(entity.getB_title())
                .b_content(entity.getB_content())
                .b_count(entity.getB_count())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .iframe(entity.getIframe())
                .s_img(entity.getS_img())
                .build();
        return boardDTO;
    }
}
