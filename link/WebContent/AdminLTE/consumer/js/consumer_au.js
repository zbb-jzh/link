
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
        }
    };
var zTreeObj = null;

var vm = avalon.define({
	$id:'consumerau',
	consumerId:getUrlData('id'),
	consumer:{id:'',name:'', type:'1', description:'',contactPerson:'',phone:'',contact:'',address:'',parentId:''},
	submited:false,
	getConsumer:function()
	{
		if(vm.consumerId)
		{
			$.ajax({
			    url: "../consumer/doGetById",    //请求的url地址
			    dataType: "json",   //返回格式为json
			    data: {id:vm.consumerId},    //参数值
			    type: "post",   //请求方式
			    success: function(res) {
			    	if (res.status == 1) {
			    		vm.consumer = res.data;
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
		vm.submited = true;
		if(vm.consumer.name == '' && vm.consumer.phone == ''){
			return;
		}
		var nodes = zTreeObj.getCheckedNodes();
		if (null != nodes) {
            for (var i = 0; i < nodes.length; i++) {
                
            	vm.consumer.parentId = nodes[i].id;
            }
        }
		if(vm.consumerId)
		{
			if(vm.consumerId == vm.consumer.parentId){
				alert('自己不能选择自己');
				return;
			}
			$.ajax({
			    url: "../consumer/doUpdate",    //请求的url地址
			    dataType: "json",   //返回格式为json
			    data: param({consumer: vm.consumer}),    //参数值
			    type: "post",   //请求方式
			    success: function(res) {
			    	if (res.status == 1) {
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
			    url: "../consumer/doAdd",    //请求的url地址
			    dataType: "json",   //返回格式为json
			    data: param({consumer: vm.consumer}),    //参数值
			    type: "post",   //请求方式
			    success: function(res) {
			    	if (res.status == 1) {
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
	getConsumerTreeList: function () {
    	$.ajax({
		    url: "/link/consumer/doTree",    //请求的url地址
		    dataType: "json",   //返回格式为json
		    data: {},    //参数值
		    type: "post",   //请求方式
		    success: function(res) {
		    	if (res.status == 100) {
		    		//vm.shopCategoryList = res.data;
		    		console.log(res.data)
		            zTreeObj = $.fn.zTree.init($("#goodsCateory"), setting, res.data);
		            zTreeObj.expandAll(true);
		            
		            if(vm.consumer.id){
		            	zTreeObj.checkNode(zTreeObj.getNodeByParam("id", vm.consumer.parentId, null), true, true);
		            }
		            
                }else{
                	alert(res.data);
                }
		    },
		    error: function() {
		    	console.log('error');
		    }
		});
    },
	goback:function()
	{
		window.location.href = '#/consumer/list';
	}
});

vm.getConsumer();
vm.getConsumerTreeList();
avalon.scan();