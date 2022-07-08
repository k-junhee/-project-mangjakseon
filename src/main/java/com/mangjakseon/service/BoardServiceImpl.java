package com.mangjakseon.service;

import com.mangjakseon.dto.BoardDTO;
import com.mangjakseon.dto.PageRequestDTO;
import com.mangjakseon.dto.PageResultDTO;
import com.mangjakseon.entity.Board;
import com.mangjakseon.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    // 등록
    @Override
    public Integer register(BoardDTO boardDTO) {

        Board entity = dtoToEntity(boardDTO); // dto를 entity로 변환

        boardRepository.save(entity); // entity로 변환된 값을 repository에 저장

        return entity.getBno(); // 저장된 후에는 해당 entity가 가지는 bno 값을 반환
    }

    // 페이징 처리
    @Override
    public PageResultDTO<BoardDTO, Board> getList(PageRequestDTO requestDTO) { // PageRequestDTO는 파라미터, PageResultDTO를 리턴 타입으로 사용하는 getList()메서드
        Pageable pageable = requestDTO.getPageable(Sort.by("bno").descending()); // bno 역순으로
        Page<Board> result = boardRepository.findAll(pageable);
        Function<Board, BoardDTO> fn = (entity -> entityToDto(entity));
        return new PageResultDTO<>(result, fn);
    }

    // 목록
    @Override
    public List<Board> boardList() {
        return boardRepository.findAll();
    }

    // 상세보기
    @Override
    public BoardDTO read(Integer bno) {

        Optional<Board> result = boardRepository.findById(bno);

        return result.isPresent()? entityToDto(result.get()): null;
    }


    // 조회수
    @Transactional
    public int updateView(Integer bno) {
        return boardRepository.updateView(bno);
    }


    // 삭제
    @Override
    public void remove(Integer bno) {
        boardRepository.deleteById(bno);
    }

    // 수정
    @Override
    public void modify(BoardDTO dto) {
        Optional<Board> result = boardRepository.findById(dto.getBno());

        if(result.isPresent()){
            Board entity = result.get();

            entity.changeTitle(dto.getB_title());
            entity.changeContent(dto.getB_content());

            boardRepository.save(entity);
        }
    }

}
