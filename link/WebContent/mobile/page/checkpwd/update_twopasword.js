/**
 * 
 */

var vm = avalon.define({
	
	$id:'updatepassword',
	oldpassword:'',
	password:'',
	cpassword:'',
	login:function(){
		if(vm.password == '' || vm.oldpassword == ''  || vm.cpassword == '' ){
			alert('密码不能为空！');
			return;
		}
		
		if(vm.cpassword != vm.password){
			alert('新密码两次输入不一致！');
			return;
		}
			
		$.ajax({
	    url: "../../../user/modifyTwoPwd",    //请求的url地址
	    dataType: "json",   //返回格式为json
	    data: {newPwd:vm.password, oldPwd:vm.oldpassword},    //参数值
	    type: "post",   //请求方式
	    success: function(res) {
	    	if(res.status == 100){
	    		alert("修改成功");
	    		
	    	}else if(res.status == -112){
            	alert("原始密码输入错误");
            }else{
	    		alert(res.data);
	    	}
	        console.log('sucess');
	    },
	    error: function() {
	    	console.log('error');
	    }
	});
	},
	removeInput:function(type){
		
		if(type == 2){
			vm.password = '';
		}
	}
	
})