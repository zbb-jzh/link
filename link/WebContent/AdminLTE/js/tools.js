/**
 *用于ajax对象参数解析为param1=v1&&paramSon.param=v2的格式
 *@param  obj:需要解析成字符串的对象
 */
var param = function (obj) {
    var query = '';
    var name, value, fullSubName, subName, subValue, innerObj, i;
    for (name in obj) {
        value = obj[name];
        if (value instanceof Array) {
            for (i = 0; i < value.length; ++i) {
                subValue = value[i];
                fullSubName = name + '[' + i + ']';
                innerObj = {};
                innerObj[fullSubName] = subValue;
                query += param(innerObj) + '&';
            }
        } else if (value instanceof Object) {
            for (subName in value) {
                subValue = value[subName];
                if (subValue == null || subValue === "") {
                    continue;
                }
                fullSubName = name + '.' + subName + '';
                innerObj = {};
                innerObj[fullSubName] = subValue;
                query += param(innerObj) + '&';
            }
        } else if (value !== undefined && value !== null) {
            query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
        }
    }
    return query.length ? query.substr(0, query.length - 1) : query;
};
var getUrlData = function (key) {

    var url = window.location.hash; //获取url中"?"符后的字串
    var urlData = new Object();
    var index = url.indexOf("?")
    if (index != -1) {
        var str = url.substr(index + 1);
        tempString = str.split("&");
        for (var i = 0; i < tempString.length; i++) {
            urlData[tempString[i].split("=")[0]] = (tempString[i].split("=")[1]);
        }
    }
    if ('' == urlData || null == urlData) {
        return ''
    }
    return eval("urlData." + key)
};

var isEmpty = function (val) {
    if (null == val || undefined == val || '' == val) {
        return true;
    }
    return false
};

var checkIsLogin = function () {
    var success = function (data) {
        currentUser = data.data.account
    };
    var error = function (url, data) {

        window.location = 'login/login.html'
        currentUser = null;
        //设置当前焦点为今日特惠
    };
    post('/act_admin/doCheckLogin', {}, success, error)
};


