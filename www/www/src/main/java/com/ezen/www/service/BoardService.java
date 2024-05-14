package com.ezen.www.service;


import com.ezen.www.domain.BoardVO;

import java.util.List;

public interface BoardService {

    void insert(BoardVO bvo);

    List<BoardVO> getList();

    BoardVO detail(int bno);

    void modify(BoardVO bvo);

    void remove(int bno);
}
