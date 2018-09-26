/**
 * 
 */


var setting = {
        treeNodeKey: "id",
        treeNodeParentKey: "parentId",

//        view: {
//            dblClickExpand: true
//        },
        check: {
            enable: true,
            chkStyle: "radio",  //单选框
            radioType: "all"   //对所有节点设置单选
//            chkStyle: "checkbox",
//            chkboxType: {
//                "Y": "ps", "N": "ps"
//            }
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId"

            },
            key: {
                children: "nodes"
            }, keep: {
                parent: true,
                open: true
            }
        },
        callback: {
            beforeCheck: zTreeBeforeCheck,
            onClick: function (e, treeId, treeNode, clickFlag) { 
            	zTreeObj.checkNode(treeNode, !treeNode.checked, true); 
        }
        }
        
    };

var referrersetting = {
        treeNodeKey: "id",
        treeNodeParentKey: "parentId",

//        view: {
//            dblClickExpand: true
//        },
        check: {
            enable: true,
            chkStyle: "radio",  //单选框
            radioType: "all"   //对所有节点设置单选
//            chkStyle: "checkbox",
//            chkboxType: {
//                "Y": "ps", "N": "ps"
//            }
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId"

            },
            key: {
                children: "nodes"
            }, keep: {
                parent: true,
                open: true
            }
        },
        callback: {
            //beforeCheck: zTreeBeforeCheck,
            onClick: function (e, treeId, treeNode, clickFlag) { 
            	referrerztree.checkNode(treeNode, !treeNode.checked, true); 
        }
        }
        
    };

var zTreeObj = null;

var referrerztree = null;

function zTreeBeforeCheck(treeId, treeNode){
	console.log(treeNode);
	if(treeNode.nodes.length >= 2){
		alert("每个客户下只能有两个接点");
		return false;
	}
	return true;
    	
}

var vm = avalon.define({
	$id:'consumerau',
	consumerId:getUrlData('id'),
	consumer:{id:'',name:'', type:'1',area:'1', description:'',contactPerson:'',phone:'',contact:'',address:'',referrerId:'',parentId:'',bankAccountName:'',bankName:'',bankAddress:'',bankCard:'',zipCode:'',userName:'',userPwd:'',twoPassword:''},
	submited:false,
	isUpdate:false,
	getConsumer:function()
	{
		if(vm.consumerId)
		{
			vm.isUpdate = true;
			$.ajax({
			    url: "../../../consumer/doGetById",    //请求的url地址
			    dataType: "json",   //返回格式为json
			    data: {id:vm.consumerId},    //参数值
			    type: "post",   //请求方式
			    success: function(res) {
			    	if (res.status == 1) {
			    		vm.consumer = res.data;
	                }else if(res.status == -114){
	                	window.location.href = "../checkpwd/check_pwd.html";
	                }
			    },
			    error: function() {
			    	console.log('error');
			    }
			});
		}
	},
	add:function()
	{
		//vm.submited = true;
		if(vm.consumer.name == ''){
			alert("姓名不能为空！");
			return false;
		}
		if(vm.consumer.phone == ''){
			alert("手机号不能为空！");
			return false;
		}
		if(vm.consumer.bankAccountName == ''){
			alert(" 银行持卡人姓名不能为空！");
			return false;
		}
		if(vm.consumer.bankName == ''){
			alert("开户银行不能为空！");
			return false;
		}
		if(vm.consumer.bankAddress == ''){
			alert("开户银行所在地不能为空！");
			return false;
		}
		if(vm.consumer.bankCard == ''){
			alert("卡号不能为空！");
			return false;
		}
		if(vm.consumer.userName == ''){
			alert("登录用户名不能为空！");
			return false;
		}
		if(vm.consumer.userPwd == ''){
			alert("登录密码不能为空！");
			return false;
		}
		if(vm.consumer.twoPassword == ''){
			alert("二级密码不能为空！");
			return false;
		}
		var nodes = zTreeObj.getCheckedNodes();
		if (null != nodes && nodes.length > 0) {
            for (var i = 0; i < nodes.length; i++) {
                
            	vm.consumer.parentId = nodes[i].id;
            }
        }else if(nodes.length == 0){
        	alert("客户接点不能为空！");
			return false;
        }
        
        var referrernodes = referrerztree.getCheckedNodes();
		if (null != referrernodes && referrernodes.length > 0) {
            for (var i = 0; i < referrernodes.length; i++) {
                
            	vm.consumer.referrerId = referrernodes[i].id;
            }
        }else if(referrernodes.length == 0){
        	alert("推荐人不能为空！");
			return false;
        }
		
		if(vm.consumerId)
		{
			if(vm.consumerId == vm.consumer.parentId){
				alert('自己不能选择自己');
				return false;
			}
			$.ajax({
			    url: "../../../consumer/doUpdate",    //请求的url地址
			    dataType: "json",   //返回格式为json
			    data: param({consumer: vm.consumer}),    //参数值
			    type: "post",   //请求方式
			    success: function(res) {
			    	if (res.status == 1) {
			    		console.log('sucess');
			    		alert("注册成功");
			    		//vm.goback();
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
			    url: "../../../consumer/doAdd",    //请求的url地址
			    dataType: "json",   //返回格式为json
			    data: param({consumer: vm.consumer}),    //参数值
			    type: "post",   //请求方式
			    success: function(res) {
			    	if (res.status == 1) {
			    		console.log('sucess');
			    		alert("注册成功");
			    		window.location.href = "consumer_node.html";
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
	actionIcon:function(nodes){
		//node[i].icon="../../common/images/zTreeStandard.png";
		for(var i=0; i<nodes.length; i++){
			
			nodes[i].icon="../../common/images/zTreeStandard.png";
			vm.actionIcon(nodes[i].nodes);
		}
	},
	getConsumerTreeList: function () {
    	$.ajax({
		    url: "/link/consumer/doTree",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if (res.status == 100) {
		    		//vm.shopCategoryList = res.data;
		    		res.data.icon="../../common/images/zTreeStandard.png";
		    		vm.actionIcon(res.data.nodes);
		    		/*for(var i=0; i<res.data.nodes.length; i++){
		    			
		    			//res.data.nodes[i].icon="../../common/images/zTreeStandard.png";
		    			vm.actionIcon(res.data.nodes[i]);
		    		
		    		}*/
		    		console.log(res.data)
		            zTreeObj = $.fn.zTree.init($("#goodsCateory"), setting, res.data);
		    		var nodes = zTreeObj.getNodes();
		            zTreeObj.expandAll(true);
		            
		            referrerztree = $.fn.zTree.init($("#referrerztree"), referrersetting, res.data);
		            referrerztree.expandAll(true);
		            
		            if(vm.consumer.id){
		            	zTreeObj.checkNode(zTreeObj.getNodeByParam("id", vm.consumer.parentId, null), true, true);
		            }
		            
                }else if(res.status == -114){
                	window.location.href = "../checkpwd/check_pwd.html";
                }else if(res.status == -110){
                	window.location.href = "../login/login.html";
                }else{
                	alert(res.data);
                }
		    },
		    error: function() {
		    	console.log('error');
		    }
		});
    },
    zTreeBeforeCheck:function(treeId, treeNode){
    	console.log(treeNode);
    },
    removeInput:function(name){
    	vm.consumer[name] = '';
    },
	goback:function()
	{
		window.location.href = '#/consumer/list';
	}
});

vm.getConsumer();
vm.getConsumerTreeList();
avalon.scan();