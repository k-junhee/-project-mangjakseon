package com.mangjakseon.service;

import com.mangjakseon.dto.WriterDTO;
import com.mangjakseon.entity.Member;
import com.mangjakseon.repository.WriterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class WriterServiceImpl implements WriterService {

    private final WriterRepository repository;

    @Override
    public WriterDTO getWriter(String email) {
        Optional<Member> writerInfo = repository.findByWriterInfo(email);
        log.info(writerInfo + "writerInfo");
        return writerInfo.isPresent() ? writerEntityToDto(writerInfo.get()) : null;
    }
}
