async function get1(bno){
    const result = await axios.get(`/replies/list/${bno}`)
    console.log(result)
    return result.data
}

async function getList({bno, page, size, goLast}){
    const result = await axios.get(`/replies/list/${bno}`, {params:{page,size}})

    if (goLast){
        // 총 데이터 갯수를 변수에 저장
        const total = result.data.total
        // 총 데이터 수 / 한 페이지의 사이즈의 정수 변환값 저장
        const lastPage = parseInt(Math.ceil(total/size))
        // getList 자기자신을 다시 한번 실행
        return getList({bno:bno, page:lastPage, size:size})
    }

    return result.data
}

// 댓글 추가
async function addReply(replyObj){
    const response = await axios.post(`/replies/`,replyObj)
    return response.data
}

// 댓글 조회
async function getReply(rno){
    const response = await axios.get(`/replies/${rno}`)
    return response.data
}

// 댓글 수정
async function modifyReply(replyObj){
    const response = await axios.put(`/replies/${replyObj.rno}`, replyObj)
    return response.data
}

// 댓글 삭제
async function removeReply(rno){
    const response = await axios.delete(`/replies/${rno}`)
    return response.data
}