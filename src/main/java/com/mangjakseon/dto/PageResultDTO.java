package com.mangjakseon.dto;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// Generic Class 생성. DTO, EN은 PageResultDTO의 인스턴스를 생성 할 때 지정
@Data
@Getter
public class PageResultDTO<DTO, EN> { // DTO = BoardDTO, EN = BoardEntity
    private List<DTO> dtoList; // 글 목록을 저장하는 List
    private int totalPage; // 총 페이지 번호
    private int page; // 현재 페이지 번호
    private int size; // 목록 사이즈
    private int start, end; // 시작 페이지 번호, 끝 페이지 번호
    private boolean prev, next; // 이전, 다음
    private List<Integer> pageList; // 페이지 번호 목록
    // 생성자
    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        // 목록생성. dtoList에 저장
        // map()함수에서 사용할 함수를 fn으로 지정
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        // map함수 사용법
        // 배열 or 컬렉션or페이지.stream().map(화살표함수).collect(Collectors.toList())
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }
    // paging에 관련된 필드들의 값을 구해서 저장
    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1; // 페이지 번호
        this.size = pageable.getPageSize(); // 페이지당 글수

        // temp end page
        int tempEnd = (int)(Math.ceil(page/10.0)) * 10; // 1...10에서 10. 실제페이지수가 tempEnd보다 작으면 실제페이지수가 end가 됨
        start = tempEnd - 9; // 끝번호(10)에서 9를 빼면 마지막번호(1)
        prev = start > 1; // 시작번호가 1보다 크면 최소 두번째 구간이므로 이전구간 존재
        end = totalPage > tempEnd ? tempEnd: totalPage; // 홈페이지수가 현주개군의 tempEnd보다 작으면 홈페이지수가 end가 됨
        next = totalPage > tempEnd; // 총페이지수가 현재구간의 tempEnd보다 크면 다음 구간 존재
        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList()); // 1....10까지의 수를 List에 저장
    }
}
