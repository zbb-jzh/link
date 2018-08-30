/**
 * 
 */

var vm = avalon.define({
	
	$id:'checkpwd',
	name:'',
	password:'',
	
	login:function(){
		if(vm.password == ''){
			alert('用户名或密码不能为空！');
			return;
		}
			
		$.ajax({
	    url: "../../../user/doCheckTWoPwd",    //请求的url地址
	    dataType: "json",   //返回格式为json
	    data: {password:vm.password },    //参数值
	    type: "post",   //请求方式
	    success: function(res) {
	    	if(res.status == 100){
	    		 
	    		 if(document.referrer == ""){
	    			 window.location.href = "../consumer/person_info.html";
	    		 }else{
	    			 window.location.href = document.referrer;
	    		 }
	    		
	    	}else if(res.status == -110){
            	window.location.href = "../login/login.html";
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