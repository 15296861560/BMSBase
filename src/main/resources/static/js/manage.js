function delete_user(e) {
    var userId=e.getAttribute("data-id");
    window.location.href="/manageUser/delete/"+userId;
}

function agree(e) {
    var notificationId=e.getAttribute("data-id");
    window.location.href="/sendback/"+"agree/"+notificationId;
}
function reject(e) {
    var notificationId=e.getAttribute("data-id");
    window.location.href="/sendback/"+"reject/"+notificationId;
}

function agreeBorrow(e) {
    var notificationId=e.getAttribute("data-id");
    window.location.href="/manage/"+"agree/"+notificationId;
}
function rejectBorrow(e) {
    var notificationId=e.getAttribute("data-id");
    window.location.href="/manage/"+"reject/"+notificationId;
}
