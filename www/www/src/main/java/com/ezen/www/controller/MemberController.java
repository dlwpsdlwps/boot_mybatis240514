package com.ezen.www.controller;

import com.ezen.www.domain.MemberVO;
import com.ezen.www.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/member/*")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService msv;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public void join(){}

    @PostMapping("/register")
    public String register(MemberVO mvo){
        mvo.setPwd(passwordEncoder.encode(mvo.getPwd()));
        int isOk = msv.insert(mvo);
        return "index";
    }

    @GetMapping("/login")
    public void login(){}

    @GetMapping("/list")
    public void list(Model m){
        m.addAttribute("list", msv.getList());
    }

    @GetMapping("/modify")
    public void modify(){}

    @PostMapping("/modify")
    public String modify(MemberVO mvo){
        mvo.setPwd(passwordEncoder.encode(mvo.getPwd()));
        msv.modify(mvo);
        return "redirect:/member/logout";
    }

    @GetMapping("/delete")
    public String delete(Principal principal){
        msv.delete(principal.getName());
        return "redirect:/member/logout";
    }

}
