

var vm = avalon.define({
	$id:'userau',
	userId:getUrlData('id'),
	user:{id:'',name:'', sex:'1', password:'', phone:'', email:'', address:'', roleIds:'', post:'', salary:''},
	submited:false,
	roleList:[],
	getUser:function()
	{
		if(vm.userId)
		{
			$.ajax({
			    url: "../user/doGetById",    //请求的url地址
			    dataType: "json",   //返回格式为json
			    data: {id:vm.userId},    //参数值
			    type: "post",   //请求方式
			    success: function(res) {
			    	if (res.status == 100) {
			    		vm.user = res.data;
	                }
			    },
			    error: function() {
			    	console.log('error');
			    }
			});
		}else{
			//vm.user = {name:'', typeId:'1000', sex:'1', password:'', phone:'', email:'', address:''};
			vm.user.name = "";
			vm.user.password = "";
			vm.user.phone = "";
			vm.user.email = "";
			vm.user.address = "";
		}
	},
	add:function()
	{
		vm.submited = true;
		if(vm.user.name == '' || vm.user.password == '' || vm.user.phone == ''){
			return;
		}
		var roleIds = "";
		for (var i = 0; i < vm.roleList.length; i++) {
			if(vm.roleList[i].checked){
				roleIds += vm.roleList[i].id + ',';
			}
		}
		//vm.user.roleIds = roleIds;
		
		if(vm.userId)
		{
			console.log(vm.userId);
			$.ajax({
			    url: "../user/doUpdate",    //请求的url地址
			    dataType: "json",   //返回格式为json
			    data: {id:vm.userId, name: vm.user.name, password:vm.user.password, phone: vm.user.phone, sex: vm.user.sex, address: vm.user.address, email: vm.user.email, roleIds:roleIds,salary:vm.user.salary, post:vm.user.post},    //参数值
			    type: "post",   //请求方式
			    success: function(res) {
			    	if (res.status == 100) {
			    		console.log('sucess');
			    		vm.goback();
	                }else{
	                	alert(res.data);
	                }
			    },
			    error: function() {
			    	console.log('error');
			    }
			});
		}else{
			$.ajax({
			    url: "../user/doAdd",    //请求的url地址
			    dataType: "json",   //返回格式为json
			    data: {name: vm.user.name, password:vm.user.password, phone: vm.user.phone, sex: vm.user.sex, address: vm.user.address, email: vm.user.email, roleIds:roleIds,salary:vm.user.salary, post:vm.user.post},    //参数值
			    type: "post",   //请求方式
			    success: function(res) {
			    	if (res.status == 100) {
			    		console.log('sucess');
			    		vm.goback();
	                }else{
	                	alert(res.data);
	                }
			    },
			    error: function() {
			    	console.log('error');
			    }
			});
		}
	},
	//查询列表
    searchAllRoles: function () {
    	$.ajax({
		    url: "/link/role/queryList",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if (res.status == 100) {
		    		var all = res.data;
		    		for (var i = 0; i < all.length; i++) {
		    			all[i].checked = false;
		    		}
		    		vm.roleList = all;
		    		if(vm.userId){
		    			$.ajax({
						    url: "../user/searchUserRole",    //请求的url地址
						    dataType: "json",   //返回格式为json
						    data: {id:vm.userId},    //参数值
						    type: "post",   //请求方式
						    success: function(res) {
						    	if (res.status == 100) {
						    		var roles = res.data;
						    		for(var i = 0; i < roles.length; i++){
						    			for(var j = 0; j < vm.roleList.length; j++){
						    				if(roles[i].id == vm.roleList[j].id){
						    					vm.roleList[j].checked = true;
						    				}
						    			}
						    		}
				                }
						    },
						    error: function() {
						    	console.log('error');
						    }
						});
		    		}
                }
		    },
		    error: function() {
		    	console.log('error');
		    }
		});
    },
	goback:function()
	{
		window.location.href = '#/user/list';
	}
	
});
vm.searchAllRoles();
vm.getUser();
avalon.scan();