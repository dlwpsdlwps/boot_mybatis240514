package com.ezen.www.repository;

import com.ezen.www.domain.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    void insert(BoardVO bvo);

    List<BoardVO> getList();

    BoardVO detail(int bno);

    void modify(BoardVO bvo);

    void remove(int bno);
}
