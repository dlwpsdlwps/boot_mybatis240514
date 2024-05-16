package com.ezen.www.service;

import com.ezen.www.domain.MemberVO;
import com.ezen.www.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberMapper memberMapper;

    @Override
    public int insert(MemberVO mvo) {
        int isOk = memberMapper.insert(mvo);
        return isOk > 0 ? memberMapper.insertAuth(mvo.getEmail()) : 0;
    }

    @Override
    public List<MemberVO> getList() {
        List<MemberVO> list = memberMapper.getList();
        for(MemberVO mvo : list){
            mvo.setAuthList(memberMapper.selectAuths(mvo.getEmail()));
        }
        return list;
    }

    @Override
    public void modify(MemberVO mvo) {
        memberMapper.modify(mvo);
    }

    @Override
    public void delete(String name) {
        memberMapper.deleteAuth(name);
        memberMapper.delete(name);
    }
}
