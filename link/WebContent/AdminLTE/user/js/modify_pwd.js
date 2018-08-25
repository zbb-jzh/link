

var vm = avalon.define({
	$id:'pwdmodify',
	submited:false,
	oldPwd:'',
	newPwd:'',
	
	changePwd:function(){
		
		vm.submited = true;
		if(vm.oldPwd == '' || vm.newPwd == ''){
			return;
		}
		if(vm.oldPwd == vm.newPwd){
			alert("新密码不能和原始密码一样");
			return;
		}
		$.ajax({
		    url: "../user/modifyPwd",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {newPwd:vm.newPwd, oldPwd:vm.oldPwd},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if(res.status == 100){
		    		alert("修改成功");
		    		vm.oldPwd = '';
		    		vm.newPwd = '';
		    		window.location.href = "login/login.html";
		    	}else{
		    		alert(res.data);
		    	}
		    }
		});
	}
});
avalon.scan();