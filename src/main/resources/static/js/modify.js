var submitPossible = true;

function passChk(){
    var pass = document.getElementById('password');
    var basic = document.getElementById('password_basic');
    var valid = document.getElementById('password_valid');
    var possible = document.getElementById('password_possible');

    if(pass.value.length>0) {
        if(!(pass.value.length>7 && pass.value.length<21)) {
            basic.style.display = 'none';
            valid.style.display = 'block';
            possible.style.display = 'none';
            submitPossible = false;
        }else{
            basic.style.display = 'none';
            possible.style.display = 'block';
            valid.style.display = 'none';
            submitPossible = true;
        }
    }else{
        basic.style.display = 'block';
        valid.style.display = 'none';
        possible.style.display = 'none';
        submitPossible = true;
    }
}

function nickChk(){
    var nick = document.getElementById('nickname');
    var basic = document.getElementById('nickname_basic');
    var valid = document.getElementById('nickname_valid');
    var possible = document.getElementById('nickname_possible');
    var impossible = document.getElementById('nickname_impossible');

    if(nick.value.length>0) {
        if(!/^[가-힣a-z0-9-_]{2,8}$/.test(nick.value)) {
            basic.style.display = 'none';
            valid.style.display = 'block';
            possible.style.display = 'none';
            impossible.style.display = 'none';
            submitPossible = false;
        }else{
            $.ajax({
                type : "post",
                url : "/nickChk",
                data : {"nickname":nickname.value},
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                success : function(result){
                    if(result == "0"){
                        basic.style.display = 'none';
                        valid.style.display = 'none';
                        possible.style.display = 'block';
                        impossible.style.display = 'none';
                        submitPossible = true;
                    }else{
                        basic.style.display = 'none';
                        valid.style.display = 'none';
                        possible.style.display = 'none';
                        impossible.style.display = 'block';
                        submitPossible = false;
                    }
                },
                error : function(){
                    alert("ajax 실행 실패");
                }
            });
        }
    }else{
        basic.style.display = 'block';
        valid.style.display = 'none';
        possible.style.display = 'none';
        impossible.style.display = 'none';
        submitPossible = true;
    }
}

function checkSubmit(){
    if(submitPossible==false){
        alert("회원정보 수정에 실패하였습니다");
        return;
    }else{
        var modForm = document.getElementById('modAccount');
        modForm.submit();
    }
}