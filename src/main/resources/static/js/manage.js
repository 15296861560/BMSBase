function sendback(notificatinId,status) {


    $.ajax({
        type: "POST",
        url: "/manage/handle",
        contentType:'application/json',
        data: JSON.stringify({//将json对象转换成字符串
            "notificatinId":notificatinId,
            "type":type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            }else {
                if(response.code==2003){//登陆错误则弹窗提示是否登陆
                    var isAccepted = confirm(response.message);
                    if (isAccepted){//跳转到登陆地址
                        window.open("/login/user");
                        window.localStorage.setItem("closable",true);
                    }

                }else {
                    alert(response.message);

                }
            }

        },
        dataType: "json"
    });
}

function agree(e) {
    var notificatinId=e.getAttribute("data-id");
    window.location.href="/sendback/"+"agree/"+notificatinId;
}
function reject(e) {
    var notificatinId=e.getAttribute("data-id");
    window.location.href="/sendback/"+"reject/"+notificatinId;
}
