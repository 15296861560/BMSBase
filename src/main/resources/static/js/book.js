
function apply(e) {
    var bookId=e.getAttribute("data-id");
    var bookStatus=e.getAttribute("data-status");
    //验证图书是否在库中
    if (bookStatus=="完好"){
        window.location.href="/book/"+"apply/"+bookId;
    }else {
        // alert("这本书已经被借出或已经损坏了，请换本试试吧！")
        Swal.fire({
            type: 'error',
            title: '这本书已经被借出或已经损坏了...',
            text: '请换本试试吧！',
        })
    }


}
function back() {
    window.location.href="/book";
}
