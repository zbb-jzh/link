/**
 * 
 */

var vmm = avalon.define({
	
	$id:'footercontent',
	isOk:false,
	isOk1:false,
	isOk2:false,
	setshow:function(type){
		if(type == 1){
			vmm.isOk = true;
		}
		if(type == 0){
			vmm.isOk = false;
		}
		
	},
	toLocation:function(type){
		
		if(type == 1){
			window.location.href = "../consumer/person_info.html";
		}
		if(type == 2){
			window.location.href = "../checkpwd/update_password.html";
		}
		if(type == 3){
			window.location.href = "../checkpwd/update_twopassword.html";
		}
		if(type == 4){
			$.ajax({
			    url: "../../../user/loginout",    //请求的url地址
			    dataType: "json",   //返回格式为json
			    data: {},    //参数值
			    type: "post",   //请求方式
			    success: function(res) {
			    	if(res.status == 100){
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
			
		}
	},
	setshow1:function(type){
		if(type == 1){
			vmm.isOk1 = true;
		}
		if(type == 0){
			vmm.isOk1 = false;
		}
		
	},
	toLocation1:function(type){
		
		if(type == 1){
			window.location.href = "../consumer/referrer_consumers.html";
		}
		if(type == 2){
			window.location.href = "../consumer/consumer_node.html";
		}
		if(type == 3){
			window.location.href = "../consumer/consumer_au.html";
		}
		if(type == 4){
			window.location.href = "../consumer/person_info.html";
		}
	},
	setshow2:function(type){
		if(type == 1){
			vmm.isOk2 = true;
		}
		if(type == 0){
			vmm.isOk2 = false;
		}
		
	},
	toLocation2:function(type){
		
		if(type == 1){
			window.location.href = "../consumer/consumer_salary.html";
		}
		if(type == 2){
			window.location.href = "../consumer/consumer_withdraw.html";
		}
		if(type == 3){
			window.location.href = "../consumer/withdraw_detail.html";
		}
		if(type == 4){
			window.location.href = "../consumer/person_info.html";
		}
	},
	removeInput:function(type){
		
	}
	
})
avalon.scan();