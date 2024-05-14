package com.ezen.www.controller;

import com.ezen.www.domain.BoardDTO;
import com.ezen.www.domain.BoardVO;
import com.ezen.www.domain.FileVO;
import com.ezen.www.domain.PagingVO;
import com.ezen.www.handler.FileHandler;
import com.ezen.www.handler.PagingHandler;
import com.ezen.www.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/board/*")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService bsv;
    private final FileHandler fh;

    @GetMapping("/register")
    public void register(){}

    @PostMapping("/register")
    public String insert(BoardVO bvo, @RequestParam(name = "files", required = false)MultipartFile[] files){
        List<FileVO> flist = null;
        if(files[0].getSize() > 0 || files != null){
            flist = fh.uploadFiles(files);
        }
        bsv.insert(new BoardDTO(bvo, flist));
        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public String list(Model m, PagingVO pgvo){
        int totalCount = bsv.getTotalCount(pgvo);
        PagingHandler ph = new PagingHandler(pgvo, totalCount);
        List<BoardVO> list = bsv.getList(pgvo);
        m.addAttribute("list", list);
        m.addAttribute("ph", ph);
        return "/board/list";
    }

    @GetMapping("/detail")
    public void detail(Model m, @RequestParam("bno")long bno){
        m.addAttribute("bdto", bsv.detail(bno));
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
