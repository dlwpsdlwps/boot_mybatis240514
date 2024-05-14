package com.ezen.www.service;


import com.ezen.www.domain.BoardDTO;
import com.ezen.www.domain.BoardVO;
import com.ezen.www.domain.PagingVO;

import java.util.List;

public interface BoardService {

    void insert(BoardDTO bdto);

    List<BoardVO> getList(PagingVO pgvo);

    BoardDTO detail(long bno);

    void modify(BoardVO bvo);

    void remove(int bno);

    int getTotalCount(PagingVO pgvo);
}
