//var permissions = [{route: '/user/list', paths: "user/user_list.html", name: "用户管理", active: false},
//                   {route: '/consumer/list', paths: "consumer/consumer_list.html", name: "客户管理", active: false},
//                   {route: '/category/list', paths: "goods/goods_category.html", name: "商品分类管理", active: false},
//                   {route: '/goods/list', paths: "goods/goods_list.html", name: "商品管理", active: false}];
//
//var menus = [{route: '/user/list', paths: "user/user_list.html", name: "用户管理", active: false},
//             {route: '/user/au', paths: "user/user_au.html", name: "用户增改", active: false},
//             {route: '/consumer/list', paths: "consumer/consumer_list.html", name: "客户管理", active: false},
//             {route: '/consumer/au', paths: "consumer/consumer_au.html", name: "客户增改", active: false},
//             {route: '/category/list', paths: "goods/goods_category.html", name: "商品分类管理", active: false},
//             {route: '/goods/list', paths: "goods/goods_list.html", name: "商品管理", active: false},
//             {route: '/goods/au', paths: "goods/goods_au.html", name: "商品管理", active: false}];
var menus = [];
var currentPM;

var btns = [{type:'add', has:false}, {type:'delete', has:false}, {type:'update', has:false}];
var vm = avalon.define({
	$id:'index',
	permissions:[],
    //currentPM:{},     
	click:function(obj){
	   //alert('hello');  	
		currentPM.active = false;
		obj.active = true;
		currentPM = obj;
		for(var i=0; i<btns.length; i++){
			btns[i].has = false;
		}
		if(obj.btnList.length > 0){
			for(var i=0; i<btns.length; i++){
				for(var j=0; j<obj.btnList.length; j++){
					if(btns[i].type == obj.btnList[j].btnType){
						btns[i].has = true;
					}
				}
			}
		}
		window.location.href = '#' + obj.route;
	},
	index:function()
	{
		//vm.permissions = permissions;
		$.ajax({
		    url: "../user/searchUserAuthority",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if(res.status == 100){
		    		
		    		for(var i=0; i<res.data.length; i++){
		    			for(var j=0; j<res.data[i].subList.length; j++){
		    				res.data[i].subList[j].active = false;
		    				var temp = {route: '', paths: "", name: "", active: false};
		    				temp.route = res.data[i].subList[j].route;
		    				temp.paths = res.data[i].subList[j].paths;
		    				temp.name = res.data[i].subList[j].name;
		    				menus.push(temp);
		    			}
		    		}
		    		$.ajax({
		    		    url: "../user/searchLeafMenu",    //请求的url地址
		    		    dataType: "json",   //返回格式为json
		    		    data: {},    //参数值
		    		    type: "post",   //请求方式
		    		    success: function(res) {
		    		    	if(res.status == 100){
		    		    		for(var i=0; i<res.data.length; i++){
		    		    			var temp = {route: '', paths: "", name: "", active: false};
				    				temp.route = res.data[i].route;
				    				temp.paths = res.data[i].paths;
				    				temp.name = res.data[i].name;
				    				menus.push(temp);
		    		    		}
		    		    		for(var i=0; i<menus.length; i++){
		    		    			pipei(menus[i]);
		    		    		}
		    		    		router.init();
		    		    	}
		    		    }
		    		});
		    		
		    		vm.permissions = res.data;
		    		currentPM = vm.permissions[0].subList[0];
		    		currentPM.active = true;
		    		window.location.href = '#' + currentPM.route;
		    	}else{
		    		window.location.href = "login/login.html";
		    	}
		        console.log('sucess');
		    },
		    error: function() {
		    	console.log('error');
		    }
		});
	},
	showDialog:function(){
		window.location.href = "#" + "/user/pwdmodify";
	}
	
});

/**
 * director使用
 */
var routes = {};
var router = new Router(routes);
function pipei(hot) {
    router.on(hot.route, function () {
        console.log(hot.paths);
        $("#body").load(hot.paths);
        for (var i = 0; i < menus.length; i++) {
        	menus[i].active = false;
            if (hot.route == menus[i].route) {
            	menus[i].active = true;
            }
        }
    });
}
//for(var i=0; i<menus.length; i++){
//	pipei(menus[i]);
//}
//router.init();

vm.index();
avalon.scan();