package com.ezen.www.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PagingVO {

    private int pageNo;
    private int qty;

    private String type;
    private String keyword;

    public PagingVO(){
        this.pageNo = 1;
        this.qty = 10;
    }

    public PagingVO(int pageNo, int qty){
        this.pageNo = pageNo;
        this.qty = qty;
    }

    public int getPageStart(){
        return (pageNo - 1)*qty; //db의 limit 앞의 값 설정 #{pageStart}
    }

    public String[] getTypeToArray(){
        return this.type == null ? new String[]{} : this.type.split("");
    }
}
