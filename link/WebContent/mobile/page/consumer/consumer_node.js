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
        }
        
    };

var zTreeObj = null;


var vm = avalon.define({
	$id:'consumernode',
	
	
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
		    		var nodes = zTreeObj.getNodes();
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

vm.getConsumerTreeList();
avalon.scan();
