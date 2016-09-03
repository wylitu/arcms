/**
 * 价格, 保留2位小数
 * */
//var REG_PRICE = /^(\d*.\d{1,2}|\d+)$/;
var REG_PRICE = /^(0|[1-9][0-9]{0,9})(\.[0-9]{1,2})?$/;
/**
 * 库存数量 正整数
 * */
var REG_QUANTITY = /^([1-9]\d*)$/;
/**
 * 积分数量, 0 或者正整数
 * */
var REG_POINT = /^([1-9]\d*|[0]{1,1})$/;

/**
 * 验证价格
 * @param objId 当前input元素的Id
 * @param showId 显示提示信息的html元素id
 * */
function checkPriceById(objId,showId){
    var price = document.getElementById(objId).value;
    var show = null;
    if(!isNullOrEmpty(showId)){
        show = document.getElementById(showId);
    }
    if(price.match(REG_PRICE)){
        if(!isNullOrEmpty(show)) {
            show.innerHTML = '';
        }
    }else{
        if(!isNullOrEmpty(show)) {
            show.innerHTML = '请输入价格,最多保留两位小数';
        }
        return false;
    }
    return true;
}

 /**
  * 商品列表dataTable
  * */
 var goodsList = $('#goodsList').dataTable({
     "language": {
         "paginate": {
             "first": "首页",
             "last": "末页",
             "next": "下一页",
             "previous": "上一页"
         },
         "lengthMenu": "每页 _MENU_ 条记录",
         "zeroRecords": "没有数据",
         "info": "共 _TOTAL_ 条记录， 当前第 _PAGE_ 页",
         "sInfoEmpty": "0条记录",
         "sProcessing":   "加载中,请稍后...",
         "infoFiltered": ""
     },
     "pagingType": "full_numbers",
     "processing": true,
     "searching": false,
     "serverSide": true,
     "bInfo":true,
     "bStateSave":true,
     "sInfoEmpty": "0条记录",
     "ajax": {
         "url":  baseUrl + '/mall/goods/list',
         "data": function(d){
             var title = $("#searchGoodsForm :input[name='title']").val();
             var status = $("#searchGoodsForm :input[name='status']").val();
             var cname = $("#searchGoodsForm :input[name='categoryName']").val();
             var startDate = $("#searchGoodsForm :input[name='startDate']").val();
             var endDate = $("#searchGoodsForm :input[name='endDate']").val();

             if (!isNullOrEmpty(title)) {
                 d.title = title;
             }

             if (!isNullOrEmpty(status)) {
                 d.status = status;
             }

             if (!isNullOrEmpty(cname)) {
                 d.categoryName = cname;
             }

             if (!isNullOrEmpty(startDate)) {
                 d.startDate = startDate;
             }

             if (!isNullOrEmpty(endDate)) {
                 d.endDate = endDate;
             }
         }
     },
     "columnDefs": [
         {
             orderable : false,
             targets : [0]
         },
         {
             "render": function (data, type, row) {
                 return '<td class="center"><label><input type="checkbox" class="ace" value="'+data+'"/><span class="lbl"></span></label></td>';
             },
             "targets": 0
         }, {
             "render": function (data, type, row) {
                 var name = row.title;
//                 var imgUrls = row.imgsUrl;
//                 var firstImg;
//                 if(!isNullOrEmpty(imgUrls)){
//                     firstImg = imgUrls.split(",")[0]
//                 }
                 var firstImg = row.thumbnail;
                 var html = '<td align="left">';
//                     html += '<a href="#">';
                     html += '<div style="position:relative;overflow:hidden;">';
                    html += '<img src="'+ baseUrl + firstImg + '" width="60px" height="60px" style="float:left" alt="速牛科技" title="' + name + '"/>';
                    html += '<div style="margin: 0 0 0 70px; height: 60px;padding: 0;position: relative; text-align: left">';
                    html += name;
                    html += '</div></div>';
//                    html += '</a>';
                    html += '</td>';
                 return html;
//                 return  '<td align="left"><a href="#"><img src="'+baseUrl + firstImg + '" width="60px" height="60px"/>'+ name + '</a></td>';
             },
             "targets":1
         }, {
             "render": function (data, type, row) {
                 var price = data/100;
                 return "￥"+ price.toFixed(2);
             },
             "targets":2
         },{
             "render": function (data, type, row) {

                 return data;
             },
             "targets":3
         },{
             "render": function (data, type, row) {
                 return data;
             },
             "targets":4
         },{
             "render": function (data, type, row) {
                 return data;

             },
             "targets":5
         },{
             "render": function (data, type, row) {
                 if(data==null){
                     return  '';
                 }else
                 return new Date(data).format('yyyy-MM-dd hh:mm:ss');
//                     return getLocalTime(data);
             },
             "targets":6
         },{
             "render": function (data, type, row) {
                 if(data == 'onsale'){
                     return '已上架';
                 }else if(data == 'instock'){
                     return '已下架';
                 }
                 return '未知';
             },
             "targets":7
         },{
             "render": function (data, type, row) {
                 var content = '';
                 var status = row.status;
                 if(row.status == 'instock'){
//                     content = '<a href="#" onclick=freshUrl("' + baseUrl + '/mall/goods/toEidt?id=' + row.id + '")>编辑</a>';
                     content = '<a href="#" onclick="freshUrl(\'' + baseUrl + '/mall/goods/toEidt?id=' + row.id + '\');return false;">编辑</a>';
                     content += '| <a href="#" onclick="deleteGoods(' + row.goodsId + ');return false;">删除</a> |';
                 }else if(row.status == 'onsale'){
                     content += '<a href="#" onclick="deleteGoods(' + row.goodsId + ');return false;">删除</a> |';
                 }
//                 var content = '<a href="#" onclick=freshUrl("' + baseUrl + '/mall/goods/toEidt?id=' + row.id + '")>编辑</a>';
//                 content += '| <a href="#" onclick="deleteGoods(' + row.goodsId + ')">删除</a> |';
                 if(status == 'onsale'){
                     content += '<a href="#" onclick="upOrDownGoods(' + row.goodsId + ',0);return false;">下架</a>';
                 }else if(status == 'instock'){
                     content += '<a href="#" onclick="upOrDownGoods(' + row.goodsId + ',1);return false;">上架</a>';
                 }
                 return content;
             },
             "targets":8
         }
     ],
     "sort": false,
     "columns": [
         {"data": "goodsId","sorting":false},
         {"data": null,"sorting":false},
         {"data": "price","sorting":false},
         {"data": "quantity","sorting":false},
         {"data": "exchangeAmount","sorting":false},
         {"data": "point","sorting":false},
         {"data": "listTime","sorting":false},
         {"data": "status","sorting":false},
         {"data": null,"sorting":false}
     ]
 });

    //查询
    $("#searchGoodsForm").submit(function () {
        $('#goodsList').DataTable().draw();
        return false; //阻止表单默认提交
    });

 /**
  * new row
  * */
 function newRow(tid){
     var tb = document.getElementById(tid);
     var row = tb.insertRow(tb.rows.length-1);
     var columnNum = tb.rows[0].cells.length;        //列数
     var i=0;
     for(;i < columnNum - 2; i++){
        var cell = row.insertCell(i);
        cell.innerHTML = tb.rows[1].cells[i].innerHTML;
     }
     var amountRow = row.insertCell(i);
     amountRow.innerHTML = '<input class="input-sm" type="text" placeholder="" name="skuAmount" onkeyup="fillGoodsAmount(this.value)" style="width: 80px" onblur="fillGoodsAmount();checkQuantity(this,&quot;show&quot;)"/>';
     var priceRow = row.insertCell(i+1);
     priceRow.innerHTML = '<input class="input-sm" type="text" placeholder="" name="skuPrice" style="width: 80px" onblur="fillGoodsPrice();checkPrice(this,&quot;show&quot;)"/>';
 }

 /**
  * delete row
  * */
 function removeRow(tid){
     var tb = document.getElementById(tid);
     var size = tb.rows.length-1;
     if(size > 2){
         tb.deleteRow(size-1);
     }
     fillGoodsAmount();
     fillGoodsPrice();
 }

 /**
  *   ajax请求后台返回html, 填充某块html文本
  * */
 function createPropertyHTML(id,cid){
     $.ajax({
         url: baseUrl +  'mall/getPropertyTable',
         type :'POST',
         data :{'cid':cid},
         dataType : 'html',
         success : function(content){
             $("#"+ id).html(content);
             var obj = $('input[name="goodsAmount"]').eq(0);

             if(content.indexOf('table') >= 0){
                 obj.attr("readonly",true);
                 $('#goodsPrice').attr("readonly",true);
             }else{
                 obj.attr("readonly",false);
                 obj.val(0);
                 $('#goodsPrice').attr("readonly",false);
             }
        }
     });
 }

 /***
  *     有sku的情况下， 填充商品数量
  * */
 function fillGoodsAmount(){
     var objs = $('input[name="skuAmount"]');
     var amount = 0;
     $.each(objs, function(index, obj){
         if(obj.value != ''){
             amount = amount + Number(obj.value);
         }
     });
     $('input[name="goodsAmount"]').eq(0).val(amount);
 }

/**
 *    有sku的情况下， 填充商品价格
 * */
function fillGoodsPrice(){
    var objs = $('input[name="skuPrice"]');
    var minPrice = Number.MAX_VALUE;
    $.each(objs, function(index, obj){
        if(obj.value != ''){
            var price = Number(obj.value);
            if(minPrice > price){
                minPrice = price;
            }
        }
    });
    $('input[name="goodsPrice"]').eq(0).val(minPrice);
}


 /**
  *  商品编辑时填充属性值
  * */
 function createPropertyHTMLForEdit(id,goodId,amount){
     $.ajax({
         url: baseUrl +  'mall/fillProperty',
         type :'POST',
         data :{'gid':goodId},
         dataType : 'html',
         success : function(content){
             $("#"+ id).html(content);
             var obj = $('input[name="goodsAmount"]').eq(0);
             if(content.indexOf('table') >= 0){
                 obj.attr("readonly",true);
             }else{
                 obj.attr("readonly",false);
             }
             obj.val(amount);
         }
     });
 }

 /**
  *  get 方式的ajax请求
  *  @param url     请求的url
  *  @param toUrl   请求成功后的跳转url
  *  @data          服务器返回json格式的数据
  * */
 function ajax_get(url, toUrl){
     $.get(url,function(obj){
         if(obj.errorCode == 0){
            alert(obj.errorMessage);
//            window.location.href = toUrl;
             freshUrl(toUrl);
//             console.dir(obj.errorMessage);
//             console.dir(goodsList);
 //            window.location.reload();
//             goodsList.fnDraw(false);
         }else{
             alert(obj.errorMessage);
         }
     });
 }

 /**
  * 删除一个商品
  * */
 function deleteGoods(value){
     var url = baseUrl + "/mall/goods/delete?goodsId=" + value;
     var toUrl = baseUrl + "mall/product/list";
    if(confirm("确定删除该商品吗?")){
        ajax_get(url,toUrl);
    }
 }

 /**
  * 上下架一个商品
  * @param status 0表示下架,1表示上架
  * */
 function upOrDownGoods(value,status){
     var url = baseUrl + "/mall/goods/upOrDownGoods?goodsId=" + value + "&status=" + status;
     var toUrl = baseUrl + "mall/product/list";
     var msg = '';
     if(status == 0){
         msg = "确定下架本商品吗?";
     }else if(status == 1){
         msg = "确定上架本商品吗?";
     }
     if(confirm(msg)){
         ajax_get(url,toUrl);
     }
 }

 /**
  *  订单查询
  * */

 var dataModal ;
 function orderDatas(url,tableId) {
      dataModal = $("#" + tableId).DataTable({
         "language": {
             "paginate": {
                 "first": "首页",
                 "last": "末页",
                 "next": "下一页",
                 "previous": "上一页"
             },
             "lengthMenu": "每页 _MENU_ 条记录",
             "zeroRecords": "没有数据",
             "info": "共 _TOTAL_ 条记录， 当前第 _PAGE_ 页",
             "sInfoEmpty": "0条记录",
             "sProcessing":   "加载中,请稍后...",
             "infoFiltered": ""
         },
         "pagingType": "full_numbers",
         "processing": true,
         "searching": false,
         "serverSide": true,
         "bInfo":true,
         "bAutoWidth" : false,
         "bDestroy" : true,
//         "bRetrieve": true,
//         "sInfoEmpty": "0条记录",
         "ajax": {
             "url": baseUrl + url
         },
         "columnDefs": [
             {
                 "render": function (data, type, row) {
                     return '<td class="center" style="width: "><label><input type="checkbox" class="ace" value="' + data + '"/><span class="lbl"></span></label></td>';
                 },
                 "targets": 0
             },
             {
                 "render": function (data, type, row) {
                     var name = row.title;
//                     var imgUrls = row.imgUrls;
//                     var firstImg;
//                     if (!isNullOrEmpty(imgUrls)) {
//                         firstImg = imgUrls.split(",")[0]
//                     }
                     var firstImg = row.thumbnail;
                     if(isNullOrEmpty(firstImg)){
                         firstImg = '';
                     }
                     var pvalue = '';
                     var propName = row.prop;
//                     console.dir(propName);
                     if(!isNullOrEmpty(propName)) {
                         var index = propName.lastIndexOf(';');
                         if (index == propName.length - 1) {
                             propName = propName.substring(0, propName.length - 1);
                         }
                         var parr = propName.split(';');
                         for (var i = 0; i < parr.length; i++) {
                             //alert(parr);
                             var arr = parr[i].split(':');
                             pvalue += arr[3] + ' ';
                         }
                     }
                     name += ('&nbsp;' + pvalue);

                     var html = '<td align="left">';
                     html += '<a href="#">';
                     html += '<div style="position:relative;overflow:hidden;">';
                     html += '<img src="'+ baseUrl + firstImg + '" width="60px" height="60px" style="float:left" alt="速牛科技" title="' + name + '"/>';
                     html += '<div style="margin: 0 0 0 70px; height: 60px;padding: 0;position: relative; text-align: left">';
                     html += name;
                     html += '</div></div>';
                     html += '</a>';
                     html += '</td>';
                     return html;
//                     return  '<td align="left"><a href="#"><img src="' + baseUrl + firstImg + '" width="60px" height="60px"/>' + name + '</a></td>';
                 },
                 "targets": 1
             },
             {
                 "render": function (data, type, row) {
                     return data;
                 },
                 "targets": 2
             },
             {
                 "render": function (data, type, row) {
                     var price = row.price / 100;
                     return "￥" + price.toFixed(2);
                 },
                 "targets": 3
             },
             {
                 "render": function (data, type, row) {
                     return row.userName;
                 },
                 "targets": 4
             },
             {
                 "render": function (data, type, row) {
                     if (row.tradeDate == null) {
                         return  '';
                     } else
//                         return getLocalTime(row.tradeDate);
                        return new Date(row.tradeDate).format('yyyy-MM-dd hh:mm:ss');
                 },
                 "targets": 5
             },
             {
                 "render": function (data, type, row) {
                     var status = row.status;
                     if (status == 'WAIT_BUYER_PAY') {
                         return '待付款';
                     } else if (status == 'WAIT_SELLER_SEND_GOODS') {
                         return '待发货';
                     } else if (status == 'WAIT_BUYER_CONFIRM_GOODS') {
                         return '已发货';
                     } else if (status == 'TRADE_FINISHED') {
                         return '交易完成';
                     } else if (status == 'TRADE_CLOSED') {
                         return '交易关闭';
                     } else if (status == 'TRADE_CLOSED_BY_SELLER') {
                         return '卖家关闭';
                     }
                     return '未知';
                 },
                 "targets": 6
             },
             {
                 "render": function (data, type, row) {
                     var point = row.point;
                     var num = row.quantity;
                     return point * num;
                 },
                 "targets": 7
             },
             {
                 "render": function (data, type, row) {
                     var content = '<a href="#" onclick="freshUrl(\'' + baseUrl + '/mall/order/detail?tid=' + row.tid + '\');return false">查看</a>';

                     var status = row.status;
                     if (status == 'WAIT_SELLER_SEND_GOODS') {
                         content += ' | <a href="javascript:void(0)" onclick="toReceiveTrade(' + row.tid + ');return false">发货</a>';
                     } else {

                     }
                     return content;
                 },
                 "targets": 8
             }
         ],
         "sort": false,
         "columns": [
             {"data": "tid"},
             {"data": null},
             {"data": "tid"},
             {"data": null},
             {"data": "userName"},
             {"data": null},
             {"data": null},
             {"data": null},
             {"data": null}
         ]
     });
 }

 /**
  * 跳转到单个订单发货页面
  * */
 function toReceiveTrade(param){
     $.ajax({
         type : "POST",
         url : baseUrl + 'mall/order/toSigleReceTrade',
         data: {tid:param},
         dataType : "json",
         success : function(d){
            if(d.errorCode == 0){
                $('#singleReceive input[name="tradeId"]').attr("value", d.data.id);
                $('#singleReceiveModal').modal("show");
            }else{
                alert(d.errorMessage);
            }
          }
     });
 }

 /**
  * 处理单个订单发货
  * */
  function singleRece(){
     var datas = $('#singleReceive').serialize();
     var tradeId = $('#singleReceive .form-group input[name="tradeId"]').val();
     var company = $('#singleReceive .form-group select[name="company"]').val();
     var deliverNum = $('#deliverNum').val();
//     console.dir(company);
//     console.dir(deliverNum);
     if(isNullOrEmpty(company) || isNullOrEmpty(deliverNum)){
         alert('请选择快递公司并且填写快递单号信息');
         return ;
     }
     $.ajax({
         type : "POST",
         url : baseUrl + 'mall/order/singleReceTrade',
         data: datas,
         dataType : "json",
         success : function(d){
             if(d.errorCode == 0){
                 alert('发货成功');
                 $("#singleReceiveModal").modal("hide");
                 console.dir(dataModal);
                 dataModal.draw(false);
             }else{
                 alert('发货失败');
             }
         }
     });
 }

 /**
  * 跳转到多个订单发货页面
  * */
 function toMultipReceiveTrade(){
     //取所选的订单tid
     var ids = '';
     var boxes = $('#wait_seller_send_orders input[type="checkbox"]');
     for(var i=1;i<boxes.length;i++){
         if(boxes[i].checked) {
             ids += boxes[i].value + ",";
         }
     }
     if(!isNullOrEmpty(ids)){
         var index = ids.lastIndexOf(',');
         if(index == ids.length - 1){
             ids = ids.substring(0, ids.length-1);
         }
     }else{
         alert('请选择未发货订单');
         return ;
     }
     $.ajax({
         type : "POST",
         url : baseUrl + 'mall/order/toMultiReceTrade',
         data: {tids:ids},
         dataType : "json",
         success : function(d){
             if(d.errorCode == 0){
                 var html = '';
                 var _tids = '';
                 $.each(d.data, function(index, obj){
                     var tNo = obj.tid;
                     var tid = obj.id;
                     _tids += tid;
                     if(index < d.data.length -1){
                         _tids += ',';
                     }

//                     var createTime = getLocalTime(obj.created);
                     var createTime = new Date(obj.created).format('yyyy-MM-dd hh:mm:ss');
                     html += '<table style="width:100%" border="0px" cellpadding="5px">';
                     html += '<tr style="border: 1px solid #CCCCCC; background-color: #EAF8FF; height: 40px">';
                     html += '<td colspan="4" style="text-align: left">' + createTime + '         订单号:   ' + tNo + '</td>';
                     html += '</tr>';
                     var size = obj.mallGoodsSkuDoList.length;
                     $.each(obj.mallGoodsSkuDoList, function(ind, sku){
                         html += '<tr>';
                         html += '<td style="text-align: left; width:40%">';
                         html += '<div style="position:relative;overflow:hidden;">';
                         html += '<img src="' + baseUrl + '/' + sku.goodsImgUrl + '" width="60px" height="60px" style="float:left">';
                         html += '<div style="margin: 0 0 0 70px; height: 60px;padding: 0;position: relative; text-align: left">';
                         html += sku.goodsName;
                         html += '</div></div>';
                         html += '</td>';
                         html += '<td style="width:20%">客户姓名:' + obj.name + '</td>';
                         html += '<td style="width:10%">数量:  ' + sku.buyAmount + '</td>';
                         if(ind == 0){
                             html += '<td style="width:30%" rowspan="' + size + '">快递单号:<input type="text" name="shipId_' + tid + '" value=""/></td>';
                         }
                         html += '</tr>';
                     });
                     html += '</table>';
                     console.log(index + ' :  ' + obj.tid);
                 });
//                 alert("_tids= " + _tids);
                 $('#multipleReceive input[name="mTradeIds"]').attr("value", _tids);
                 $('#trades').html(html);
//                 $('#multipleReceive').submit();
                 $('#multipleReceiveModal').modal("show");
             }else{
                 alert(d.errorMessage);
             }
         }
     });
 }

 /**
  * 处理多个订单发货
  * */
 function multiplRece(){
     var datas = $('#multipleReceive').serialize();
     $.ajax({
         type : "POST",
         url : baseUrl + 'mall/order/multipReceTrade',
         data: datas,
         dataType : "json",
         success : function(d){
             if(d.errorCode == 0){
                 alert('发货成功');
//                 setTimeout(function(){$('#multipleReceiveModal').modal("hide")},2000);
//                 $('#multipleReceiveModal').modal("hide");
                 setTimeout(function(){freshUrl(baseUrl + '/mall/product/order');},500);
//                 freshUrl(baseUrl + '/mall/product/order');
             }else{
                 alert(d.errorMessage);
             }
         }
     });

 }

/**
 * 全选
 * */
function selectAll(id){
    var checks = document.getElementById(id).getElementsByTagName('input');
    console.dir(checks[0].checked);
    for(var i=1;i<checks.length;i++){
        checks[i].checked = checks[0].checked;
    }
}



 function test(){
     var str = '2:4:材质:纯银;3:7:尺寸:5MM;4:10:颜色:蓝色;';
     alert(str);
     var index = str.lastIndexOf(';');
     str = str.substring(0,str.length-1);
     alert(str);
     alert(index + "    " + str.length);
 }

/**
 * 验证价格
 * @param obj 当前input元素
 * @param showId 显示提示信息的html元素
 * */
function checkPrice(obj,showId){
    var price = obj.value;
    var show = document.getElementById(showId);
    if(price.match(REG_PRICE)){
        show.innerHTML = '';
    }else{
        show.innerHTML = '请输入价格,最多保留两位小数';
        return false;
    }
    return true;
}

/**
 * 验证数量
 * */
function checkQuantity(obj, showId){
//    console.dir(showId);
    var quantity = obj.value;
    var show = document.getElementById(showId);
    if(quantity.match(REG_QUANTITY)){
        show.innerHTML = '';
    }else{
        show.innerHTML = '请输入数量,正整数';
        return false;
    }
    return true;
}

/**
 * 验证积分数
 * */
function checkPointById(objId, showId){
    var point = document.getElementById(objId).value;
    var show = null;
    if(!isNullOrEmpty(showId)){
        show = document.getElementById(showId);
    }
    if(point.match(REG_POINT)){
        if(!isNullOrEmpty(show)){
            show.innerHTML = '';
        }
    }else{
        if(!isNullOrEmpty(show)) {
            show.innerHTML = '请输入积分数,正整数或者0';
        }
        return false;
    }
    return true;
}

/**
 * 验证数量
 * */
function checkQuantityById(objId, showId){
    var quantity = document.getElementById(objId).value;
    var show = null;
    if(!isNullOrEmpty(showId)){
        show = document.getElementById(showId);
    }
    if(quantity.match(REG_QUANTITY)){
        if(!isNullOrEmpty(show)) {
            show.innerHTML = '';
        }
    }else{
        if(!isNullOrEmpty(show)) {
            show.innerHTML = '请输入数量,正整数';
        }
        return false;
    }
    return true;
}

/**
 * 批量验证商品设置里面的价格
 * */
function batchCheckSkuPrice(){
    var flag = true;
    $.each($('#p_table tr td input[name="skuPrice"]'),function(index, obj){
        var value = obj.value;
//        console.dir(value);
        if(!value.match(REG_PRICE)){
            flag = false;
            return flag;
        }
    });
    return flag;
}

/**
 * 批量验证商品设置里面的数量
 * */
function batchCheckSkuAmount(){
    var flag = true;
    $.each($('#p_table tr td input[name="skuAmount"]'),function(index, obj){
        var value = obj.value;
        if(!value.match(REG_QUANTITY)){
            flag = false;
            return flag;
        }
    });
    return flag;
}