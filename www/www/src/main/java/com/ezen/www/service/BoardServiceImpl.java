package com.ezen.www.service;

import com.ezen.www.domain.BoardVO;
import com.ezen.www.repository.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    @Override
    public void insert(BoardVO bvo) {
        boardMapper.insert(bvo);
    }

    @Override
    public List<BoardVO> getList() {
        return boardMapper.getList();
    }

    @Override
    public BoardVO detail(int bno) {
        return boardMapper.detail(bno);
    }

    @Override
    public void modify(BoardVO bvo) {
        boardMapper.modify(bvo);
    }

    @Override
    public void remove(int bno) {
        boardMapper.remove(bno);
    }
}
