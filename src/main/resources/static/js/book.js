
function appply(e) {
    var bookId=e.getAttribute("data-id");
    window.location.href="/book/"+"apply/"+bookId;
}
function back() {
    window.location.href="/book";
}
