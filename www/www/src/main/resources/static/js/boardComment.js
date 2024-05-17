console.log("board comment in");
console.log(bnoVal);

document.getElementById('cmtAddBtn').addEventListener('click', ()=>{
    let cmtText = document.getElementById('cmtText').value;
    const cmtWriter = document.getElementById('cmtWriter').innerText;

    if(cmtText == null || cmtText == ''){
        alert('댓글을 입력하세요');
        document.getElementById('cmtText').focus();
        return false;
    }else{
        let cmtData={
            bno:bnoVal,
            writer:cmtWriter,
            content:cmtText
        }
        //댓글 등록
        postCommentToServer(cmtData).then(result => {
            console.log(cmtData);
            if(result == '1'){
                alert('댓글 등록 성공');
                document.getElementById('cmtText').value = '';
                //댓글 뿌리기
                spreadCommentList(bnoVal);
            }
        })
    }
})
//댓글 등록
async function postCommentToServer(cmtData){
    try {
        const url = "/comment/post";
        const config={
            method:"post",
            headers:{
                "content-type":"application/json; charset=utf-8"
            },
            body: JSON.stringify(cmtData)
        }
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

//댓글 리스트 가져오기
async function getCommentListFromServer(bno, page){
    try {
        const resp = await fetch("/comment/list/"+bno+"/"+page);
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log(error);
    }
}

//페이징 처리를 하여, 한 페이지(더보기)에 5개 댓글을 출력
//전체 게시글 수에 따른 페이지 수
function spreadCommentList(bno, page=1){
    getCommentListFromServer(bno, page).then(result => {
        console.log(result); // ph cmtList
        const ul = document.getElementById('cmtListArea');
        if(result.cmtList.length > 0){
            if(page==1){ //1 page에서만 댓글 내용 지우기
                ul.innerHTML = ''; //ul에 원래 있던 html 값 지우기
            }
            for(let cvo of result.cmtList){
                let add = `<li class="list-group-item" data-cno="${cvo.cno}">`;
                add += `<div class="input-group mb-3"> no. ${cvo.cno} | `;
                add += `<div class="fw-bold">　${cvo.writer}　</div>`;
                add += `${cvo.content}`;
                add += `</div>`;
                add += `<span class="badge rounded-pill text-bg-warning">${cvo.regAt}</span>`;
                //수정 삭제 버튼
                add += `<button type="button" class="btn btn-outline-warning btn-sm mod" data-bs-toggle="modal" data-bs-target="#myModal">%</button>`;
                add += `<button type="button" class="btn btn-outline-danger btn-sm del" >X</button>`;
                add += `</li>`;
                ul.innerHTML += add;
            }
            //더보기 버튼 코드
            let moreBtn = document.getElementById('moreBtn');
            console.log(moreBtn);
            //moreBtn 표시되는 조건
            //pgvo.pageNo = 1 / realEndPage = 3
            //realEndPage보다 현재 내 페이지가 작으면 표시
            if(result.pgvo.pageNo < result.realEndPage){
                //style="visibility:hidden" => 숨김, "visibility:visible" => 표시
                moreBtn.style.visibility = 'visible';   //버튼 표시
                moreBtn.dataset.page = page + 1;    //1페이지 늘림
            }else{
                moreBtn.style.visibility = 'hidden';    //숨김
            }

        }else{
            ul.innerHTML = `<div class="fw-bold"> Comment List is Empty </div>`;
        }
    })
}

//더보기 버튼 작업
document.addEventListener('click', (e)=>{
    if(e.target.id == 'moreBtn'){
        let page = parseInt(e.target.dataset.page);
        spreadCommentList(bnoVal, page);
    }
    else if(e.target.classList.contains('mod')){
        //내가 수정버튼을 누른 댓글의 li
        let li = e.target.closest('li');

        //writer를 찾아서 id="modWriter"에 넣기
        let modWriter = li.querySelector('.fw-bold').innerText;
        document.getElementById('modWriter').innerText = modWriter;

        //nextSibling : 한 부모 안에서 다음 형제를 찾기
        let cmtText = li.querySelector('.fw-bold').nextSibling;
        console.log(cmtText);
        document.getElementById('cmtTextMod').value = cmtText.nodeValue; //nodeValue를 이용해 변환

        //수정 => cno dataset으로 달기 cno, content
        document.getElementById('cmtModBtn').setAttribute("data-cno", li.dataset.cno);
    }
    else if(e.target.id == 'cmtModBtn'){
        let cmtModData = {
            cno: e.target.dataset.cno,
            content: document.getElementById('cmtTextMod').value
        }
        console.log(cmtModData);
        //비동기로 보내기
        updateCommentToServer(cmtModData).then(result => {
            if(result == '1'){
                alert('댓글 수정 성공');
                //모달창 닫기
                document.querySelector(".btn-close").click();
            }else{
                alert('수정실패');
                document.querySelector(".btn-close").click();
            }
            //댓글 새로 뿌리기
            spreadCommentList(bnoVal);
        })
    }
    else if(e.target.classList.contains('del')){
        //cno
        // let cnoVal = e.target.dataset.cno;
        let li = e.target.closest('li');
        let cnoVal = li.dataset.cno;
        //비동기로 삭제 요청
        removeCommentToServer(cnoVal).then(result=>{
            if(result == '1'){
                alert('댓삭 성공');
                spreadCommentList(bnoVal);
            }
        })
    }
})

//댓글 수정
async function updateCommentToServer(cmtModData){
    try {
        const url = "/comment/edit";
        const config = {
            method: "put",
            headers: {
                'content-type':'application/json; charset=utf-8'
            },
            body: JSON.stringify(cmtModData)
        }
        const resp = await fetch(url, config);
        const result = resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

//댓글 삭제
async function removeCommentToServer(cnoVal){
    try {
        const url = "/comment/"+cnoVal;
        const config = {
            method: "delete"
        }
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}