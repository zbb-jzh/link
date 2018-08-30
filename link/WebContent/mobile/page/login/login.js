/**
 * 
 */

var vm = avalon.define({
	
	$id:'login',
	name:'',
	password:'',
	
	login:function(){
		if(vm.name == '' || vm.password == ''){
			alert('用户名或密码不能为空！');
			return;
		}
			
		$.ajax({
	    url: "../../../user/login",    //请求的url地址
	    dataType: "json",   //返回格式为json
	    data: { name: vm.name, password:vm.password },    //参数值
	    type: "post",   //请求方式
	    success: function(res) {
	    	if(res.status == 100){
	    		 localStorage.setItem("user", JSON.stringify(res.data));
	    		window.location.href = "../consumer/person_info.html";
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
		if(type == 1){
			vm.name = '';
		}
		if(type == 2){
			vm.password = '';
		}
	}
	
})