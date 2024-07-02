async function get1(bno){
    const result = await axios.get(`/replies/list/${bno}`)
    return result.data
}

async function getList({bno,page,size,goLast}){
    const result = await axios.get(`/replies/list/${bno}`,{params:{page,size}})
    if(goLast){
        //총 데이터 갯수를 변수에 저장
        const total = result.data.total
        // 총데이터수 / 한페이지의 사이즈의 정수 변환값 저장
        const lastPage = parseInt(Math.ceil(total/size))
        // getList 자기자신을 다시 한번 실행
        return getList({bno:bno, page:lastPage,size:size})
    }
    return result.data
}
//댓글 추가 함수(댓글객체)
async function addReply(replyObj){
    const response = await axios.post(`/replies/`,replyObj)
    return response.data
}

async function getReply(rno){
    const response = await axios.get(`/replies/${rno}`)
    return response.data
}

async function modifyReply(replyObj){
    const response = await axios.put(`/replies/${replyObj.rno}`,replyObj)
    return response.data
}

async function removeReply(rno){
    const response = await axios.delete(`/replies/${rno}`)
    return response.data
}












