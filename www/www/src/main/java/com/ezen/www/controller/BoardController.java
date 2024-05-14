package com.ezen.www.controller;

import com.ezen.www.domain.BoardVO;
import com.ezen.www.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/board/*")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService bsv;

    @GetMapping("/register")
    public void register(){}

    @PostMapping("/register")
    public String insert(BoardVO bvo){
        bsv.insert(bvo);
        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public String list(Model m){
        List<BoardVO> list = bsv.getList();
        m.addAttribute("list", list);
        return "/board/list";
    }

    @GetMapping("/detail")
    public String detail(Model m, @RequestParam("bno")int bno){
        BoardVO bvo = bsv.detail(bno);
        m.addAttribute("bvo", bvo);
        return "/board/detail";
    }

    @PostMapping("/modify")
    public String modify(BoardVO bvo){
        bsv.modify(bvo);
        return "redirect:/board/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("bno")int bno){
        bsv.remove(bno);
        return "redirect:/board/list";
    }

}
