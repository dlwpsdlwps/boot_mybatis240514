package com.ezen.www.controller;

import com.ezen.www.domain.CommentVO;
import com.ezen.www.domain.PagingVO;
import com.ezen.www.handler.PagingHandler;
import com.ezen.www.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/comment/*")
@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService csv;

    @PostMapping("/post")
    @ResponseBody
    public String post(@RequestBody CommentVO cvo){
        int isOk = csv.post(cvo);
        return isOk>0 ? "1" : "0";
    }

    /* select * from comment order by cno desc limit 0,5 */
    @GetMapping("/list/{bno}/{page}")
    @ResponseBody
    public PagingHandler list(@PathVariable("bno")long bno, @PathVariable("page")int page){
        log.info("bno >> page >> {}/{}", bno, page);
        PagingVO pgvo = new PagingVO(page, 5);
        //Comment List
        //totalCount
        PagingHandler ph = csv.getList(bno, pgvo);
        return ph;
    }

    @PutMapping("/edit")
    @ResponseBody
    public String edit(@RequestBody CommentVO cvo) {
        int isOk = csv.edit(cvo);
        return isOk>0 ? "1" : "0";
    }

    @DeleteMapping("/{cno}")
    @ResponseBody
    public String remove(@PathVariable("cno")int cno) {
        log.info("cno >> {}", cno);
        int isOk = csv.remove(cno);
        return isOk>0 ? "1" : "0";
    }
}
