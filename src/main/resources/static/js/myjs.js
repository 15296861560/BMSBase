
// 校验两次输入的密码是否一致
function checkout() {
    var id = $("#id").val();
    var password = $("#password").val();
    var re_password = $("#re_password").val();
    debugger;
    if (id.value == "")
    {
        alert("学号不能为空，请输入学号！");
        $("#id").focus();
        return false;
    }
    if (password.value == "")
    {
        alert("您的密码不能为空，请输入密码！");
        $("#password").focus();
        return false;
    }
    if (re_password.value == "")
    {
        alert("用户确认密码不能为空，请输入密码！");
        $("#re_password").focus();
        return false;
    }
    if (password!= re_password)
    {
        alert("密码与确认密码不同");
        $("#re_password").focus();
        return false;
    }
    return true;

}

