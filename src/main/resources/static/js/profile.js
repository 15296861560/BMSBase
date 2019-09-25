
function apply(e) {
    var notificationId=e.getAttribute("data-id");
    window.location.href="/profile/apply/"+notificationId;
}

function verify() {
    var phone=document.getElementById("phone").value;
    var send=document.getElementById("send");
    $.ajax({
        type: "POST",
        url: "/profile/phone",
        contentType: 'application/json',
        data: JSON.stringify({//将json对象转换成字符串
            "phone": phone,
            "action": "verify"
        }),
        success: function (response) {
            if (response.code == 200) {//发送验证码成功
                send.innerText = "验证码发送成功请进行验证";
            } else {
                send.innerText = "验证码发送失败请重新尝试";
            }
        }
    });
}

function check() {
    var phone=document.getElementById("phone").value;
    var verifyCode=document.getElementById("verifyCode").value;
    $.ajax({
        type: "POST",
        url: "/profile/phone/verify",
        contentType: 'application/json',
        data: JSON.stringify({//将json对象转换成字符串
            "phone": phone,
            "verifyCode": verifyCode
        }),
        success: function (response) {
            if (response.code == 200) {//验证成功，跳转到个人资料页面
                window.open("/profile");
            } else {
                // alert("验证失败,请重新尝试");
                Swal.fire({
                    type: 'error',
                    title: '验证失败,请重新尝试！',
                })
            }
        }
    });
}