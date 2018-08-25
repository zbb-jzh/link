

var vm = avalon.define({
	$id:"role",
	showAdd:false,
	roleList:[],
	authorityList:[],
	authorityAll: [],
	currentRole: {},
	id: "", 
	name: "", 
	description: "",
	hasAdd:false,
	hasDelete:false,
	hasUpdate:false,
	
	//初始化表单数据
    initObj: function () {
        vm.id = "";
        vm.name = "";
        vm.description = "";
        for (var i = 0; i < vm.authorityAll.length; i++) {
            vm.authorityAll[i].checked = false;
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
		    		vm.roleList = res.data;
                }
		    },
		    error: function() {
		    	console.log('error');
		    }
		});
    },
    searchAllAuthority: function () {
    	
    	$.ajax({
		    url: "/link/menu/searchAllMenu",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if (res.status == 100) {
		            //vm.authorityList = res.data;
		            
		            var all = res.data;
                    vm.authorityList = [];
                    for (var i = 0; i < all.length; i++) {
                        all[i].checked = false;
                        if (all[i].parentId == "" || all[i].parentId == null) {
                            all[i].subList = [];
                            vm.authorityList.push(all[i]);
                        }
                        if(all[i].btnList != null && all[i].btnList.length > 0){
                        	for(var j=0; j<all[i].btnList.length; j++){
                        		all[i].btnList[j].checked = false;
                        	}
                        }
                    }
                    for (var i = 0; i < all.length; i++) {
                        for (var j = 0; j < vm.authorityList.length; j++) {
                            if (all[i].parentId == vm.authorityList[j].id) {
                                vm.authorityList[j].subList.push(all[i]);
                            }
                        }
                    }
                    vm.authorityAll = all;
		    	}
		    },
		    error: function() {
		    	console.log('error');
		    }
		});
    },
    //显示添加对话框
    toAdd: function () {
        vm.searchAllRoles();
        vm.initObj();
        vm.showAdd = !vm.showAdd;
    },
    hideDialog:function(){
    	vm.showAdd = false;
    },

    //更新或添加
    addOrUpdate: function () {
    	
        var permissionIds = "";
        for (var i = 0; i < vm.authorityAll.length; i++) {
//            if (vm.authorityAll[i].checked) {
//                permissionIds += vm.authorityAll[i].id + ",";
//            }
        	if(vm.authorityAll[i].btnList != null && vm.authorityAll[i].btnList.length > 0){
            	for(var j=0; j<vm.authorityAll[i].btnList.length; j++){
            		if (vm.authorityAll[i].btnList[j].checked) {
                      permissionIds += vm.authorityAll[i].btnList[j].id + ",";
                  }
            	}
            }
        }
        if (vm.id != "") {
        	
        	$.ajax({
    		    url: "/link/role/doUpdate",    //请求的url地址
    		    dataType: "json",   //返回格式为json
    		    data: {id: vm.id,name: vm.name, permissionIds: permissionIds,description: vm.description},    //参数值
    		    type: "post",   //请求方式
    		    success: function(res) {
    		    	if (res.status == 100) {
    		    		vm.initObj();
    		            vm.searchAllRoles();
    		            vm.showAdd = false;
                    }
    		    },
    		    error: function() {
    		    	console.log('error');
    		    }
    		});
        } else {
        	
        	$.ajax({
    		    url: "/link/role/doAdd",    //请求的url地址
    		    dataType: "json",   //返回格式为json
    		    data: {name: vm.name, permissionIds: permissionIds,description: vm.description},    //参数值
    		    type: "post",   //请求方式
    		    success: function(res) {
    		    	if (res.status == 100) {
    		    		vm.initObj();
    		            vm.searchAllRoles();
    		            vm.showAdd = false;
                    }
    		    },
    		    error: function() {
    		    	console.log('error');
    		    }
    		});
        }
    },
    getRolePermissions: function (id) {
        vm.initObj();
        
        $.ajax({
		    url: "/link/role/searchRoleMenu",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {id:id},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if (res.status == 100) {
		    		for (var i = 0; i < vm.authorityAll.length; i++) {
		    			if(vm.authorityAll[i].btnList != null && vm.authorityAll[i].btnList.length > 0){
		    				for(var j=0; j<vm.authorityAll[i].btnList.length; j++){
		    					for (var k = 0; k < res.data.length; k++) {
		    						if( vm.authorityAll[i].btnList[j].id==res.data[k].id){
		 		                       vm.authorityAll[i].checked=true;
		 		                      vm.authorityAll[i].btnList[j].checked=true;
		 		                   }
		    					}
//		                		if (vm.authorityAll[i].btnList[j].checked) {
//		                          permissionIds += vm.authorityAll[i].btnList[j].id + ",";
//		                      }
		                	}
		    			}
//		                for (var j = 0; j < res.data.length; j++) {
//		                   if( vm.authorityAll[i].id==res.data[j].id){
//		                       vm.authorityAll[i].checked=true;
//		                   }
//		                }
		            }
                }
		    },
		    error: function() {
		    	console.log('error');
		    }
		});
    },
    edit: function (role) {
        vm.getRolePermissions(role.id);
        vm.showAdd = true;
        vm.id = role.id;
        vm.name = role.name;
        vm.description = role.description;
    },
    deleteR: function (id) {
    	
    	$.ajax({
		    url: "/link/role/doDelete",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {id:id},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if (res.status == 100) {
		    		 vm.searchAllRoles();
                }
		    },
		    error: function() {
		    	console.log('error');
		    }
		});
    }
});
if(btns[0].has){
	vm.hasAdd = true;
}
if(btns[1].has){
	vm.hasDelete = true;
}
console.log(vm.hasDelete);
if(btns[2].has){
	vm.hasUpdate = true;
}
vm.searchAllRoles();
vm.searchAllAuthority();
avalon.scan();