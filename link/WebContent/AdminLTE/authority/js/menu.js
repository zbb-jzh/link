

var vm = avalon.define({
	$id:'authority',
	menuList: [],
    showAdd: false,
    showSub: false,
    showbtnAdd:false,
    showBtn:false,
    currentAuthority: {},
    currentMenuBtn:{},
    isParent:1,
    model:{id:"",name: "",description: "",fontIcon: "",orderNum: 0,route: "",paths: "",parentId: "",isLeaf:0,subList:[],btnList:[]},
    menuBtn:{id:"", btnName:"",btnType:""},
    menuBtnTypes:[{name:'新增', value:'add'}, {name:'删除', value:'delete'}, {name:'修改', value:'update'}, {name:'上下架', value:'onof'}],
    hasAdd:false,
	hasDelete:false,
	hasUpdate:false,
    initList:function(all){

        vm.menuList=[];
        vm.menuList = all;
//        for (var i = 0; i < all.length; i++) {
//            if (all[i].isParent == 1) {
//                all[i].subList = [];
//                vm.menuList.push(all[i]);
//            }
//        }
//        for (var i = 0; i < all.length; i++) {
//            for (var j = 0; j < vm.menuList.length; j++) {
//                if (all[i].parentId == vm.menuList[j].id) {
//                    vm.menuList[j].subList.push(all[i]);
//                }
//            }
//        }
//        vm.menuList.sort(
//            function(x,y){
//                return x.index-y.index;
//            }
//        );
    },
    //初始化表单数据
    initObj:function(){
        vm.model.id = "";
        vm.model.name = "";
        vm.model.description = "";
        //vm.model.fontIcon = "";
        vm.model.orderNum = 0;
        vm.model.route = "";
        vm.model.paths = "";
        vm.model.parentId = "";
    },
    //查询列表
    searchAllAuthority: function ( fun) {
    	
    	$.ajax({
		    url: "/link/menu/queryList",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if (res.status == 100) {
		    		var all = res.data;
		            vm.initList(all);
		            if(fun){
                        fun();
                    }
                }
		    },
		    error: function() {
		    	console.log('error');
		    }
		});
    },
    //显示添加对话框
    toAdd: function (isp, pId) {
        vm.initObj();
        if (isp) {
            vm.isParent = 1;
        } else {
        	vm.isParent = 2;
        }
        vm.model.parentId = pId;
        vm.showAdd = !this.showAdd;
    },
    //刷新二级列表
    refreshList:function(){
        if(vm.currentAuthority.id){
            for (var j = 0; j < vm.menuList.length; j++) {
                if (vm.currentAuthority.id== vm.menuList[j].id) {
                    vm.currentAuthority=vm.menuList[j];
                }
            }
        }
        if(vm.currentMenuBtn.id){
        	for (var j = 0; j < vm.menuList.length; j++) {
                if (vm.menuList[j].subList.length > 0) {
                	for(var i=0; i< vm.menuList[j].subList.length; i++){
                		if (vm.currentMenuBtn.id== vm.menuList[j].subList[i].id) {
                            vm.currentMenuBtn=vm.menuList[j].subList[i];
                        }
                	}
                }
            }
        }
    },
    //更新或添加
    addOrUpdate: function () {
    	if(vm.model.id){
    		$.ajax({
    		    url: "/link/menu/doUpdate",    //请求的url地址
    		    dataType: "json",   //返回格式为json
    		    data: param({menu: vm.model}),    //参数值
    		    type: "post",   //请求方式
    		    success: function(res) {
    		    	if (res.status == 100) {
    		    		console.log('sucess');
    		    		//vm.goback();
    		    		
    		    		vm.searchAllAuthority(vm.refreshList);
    		            vm.initObj();
    		            vm.showAdd = false;
    		            
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
    		    url: "/link/menu/doAdd",    //请求的url地址
    		    dataType: "json",   //返回格式为json
    		    data: param({menu: vm.model}),    //参数值
    		    type: "post",   //请求方式
    		    success: function(res) {
    		    	if (res.status == 100) {
    		    		console.log('sucess');
    		    		
    		    		vm.searchAllAuthority(vm.refreshList);
    		            vm.initObj();
    		            vm.showAdd = false;
    		    		//vm.goback();
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
    toShowSub: function (authority) {
        vm.currentAuthority = authority;
        vm.showSub = true;
    },
    toHideSub: function () {
    	vm.showSub = false;
    },
    hideAdd:function(){
    	vm.showAdd = false;
    },
    toshowBtn:function(menu){
    	vm.currentMenuBtn = menu;
    	vm.showBtn = true;
    },
    toHideBtn:function(){
    	vm.showBtn = false;
    },
    hideAddBtn:function(){
    	vm.showbtnAdd = false;
    },
    addBtn:function(){
    	vm.showbtnAdd = true;
    },
    toAddBtn:function(menu){
    	vm.menuBtn.menuId = menu.id;
    	vm.showbtnAdd = true;
    },
    initMenuBtn:function(){
    	vm.menuBtn.id = "";
    	vm.menuBtn.menuId = "";
    	vm.menuBtn.btnName = "";
    	vm.menuBtn.btnType = "";
    },
    addOrUpdateBtn:function(){
    	if(vm.menuBtn.id){
    		$.ajax({
    		    url: "/link/menu/doUpdateBtn",    //请求的url地址
    		    dataType: "json",   //返回格式为json
    		    data: param({menuBtn: vm.menuBtn}),    //参数值
    		    type: "post",   //请求方式
    		    success: function(res) {
    		    	if (res.status == 100) {
    		    		console.log('sucess');
    		    		//vm.goback();
    		    		
    		    		vm.searchAllAuthority(vm.refreshList);
    		            vm.initMenuBtn();
    		            vm.showbtnAdd = false;
    		            
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
    		    url: "/link/menu/doAddBtn",    //请求的url地址
    		    dataType: "json",   //返回格式为json
    		    data: param({menuBtn: vm.menuBtn}),    //参数值
    		    type: "post",   //请求方式
    		    success: function(res) {
    		    	if (res.status == 100) {
    		    		console.log('sucess');
    		    		
    		    		vm.searchAllAuthority(vm.refreshList);
    		            vm.initMenuBtn();
    		            vm.showbtnAdd = false;
    		    		//vm.goback();
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
    editBtn:function(menuBtn){
    	vm.showbtnAdd = true;
    	vm.menuBtn.id = menuBtn.id;
    	vm.menuBtn.menuId = menuBtn.menuId;
    	vm.menuBtn.btnName = menuBtn.btnName;
    	vm.menuBtn.btnType = menuBtn.btnType;
    },
    deleteBtn:function(id){
    	$.ajax({
		    url: "/link/menu/doDeleteBtn",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {id: id},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if (res.status == 100) {
		    		console.log('sucess');
		    		vm.searchAllAuthority(vm.refreshList);
                }else{
                	alert(res.data);
                }
		    },
		    error: function() {
		    	console.log('error');
		    }
		});
    },
    edit: function(authority){
        //vm.initObj();
        vm.showAdd = true;
        if (authority.parentId=="" || authority.parentId == null) {
        	vm.isParent = 1;
        } else {
        	vm.isParent = 2;
        }
        //vm.model = authority;
        vm.model.id = authority.id;
        vm.model.name = authority.name;
        vm.model.description = authority.description;
        //vm.model.fontIcon = authority.fontIcon;
        vm.model.orderNum = authority.orderNum;
        vm.model.route = authority.route;
        vm.model.paths = authority.paths;
        vm.model.parentId = authority.parentId;
        vm.model.isLeaf = authority.isLeaf;
    },
    deleteP:function(id){
    	$.ajax({
		    url: "/link/menu/doDelete",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {id: id},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if (res.status == 100) {
		    		console.log('sucess');
		    		vm.searchAllAuthority(vm.refreshList);
                }else{
                	alert(res.data);
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
vm.searchAllAuthority();
avalon.scan();