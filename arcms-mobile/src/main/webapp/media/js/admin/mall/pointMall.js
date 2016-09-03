 /**
     * editor
     * */
     $('#goodsDetail').ace_wysiwyg({
        toolbar:
            [
                {name:'bold', className:'btn-info'},
                {name:'italic', className:'btn-info'},
                {name:'strikethrough', className:'btn-info'},
                {name:'underline', className:'btn-info'},
                null,
                {name:'insertunorderedlist', className:'btn-success'},
                {name:'insertorderedlist', className:'btn-success'},
                {name:'outdent', className:'btn-purple'},
                {name:'indent', className:'btn-purple'},
                null,
                {name:'justifyleft', className:'btn-primary'},
                {name:'justifycenter', className:'btn-primary'},
                {name:'justifyright', className:'btn-primary'},
                {name:'justifyfull', className:'btn-inverse'},
                null,
                {name:'createLink', className:'btn-pink'},
                {name:'unlink', className:'btn-pink'},
                null,
                {name:'insertImage', className:'btn-success'},
                null,
                'foreColor',
                null,
                {name:'undo', className:'btn-grey'},
                {name:'redo', className:'btn-grey'}
            ],
        'wysiwyg': {
            fileUploadError: showErrorAlert
        }
    }).prev().addClass('wysiwyg-style2');

    function showErrorAlert(reason, detail) {
        var msg = '';
        if (reason === 'unsupported-file-type') {
            msg = "Unsupported format " + detail;
        }
        else {
            console.log("error uploading file", reason, detail);
        }
        $('<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>' +
            '<strong>File upload error</strong> ' + msg + ' </div>').prependTo('#alerts');
    }


    /**
     * 多文件上传
     */

    $("#demo").zyUpload({
        width            :   "780px",                        // 宽度
        height           :   "230px",                        // 宽度
        itemWidth        :   "100px",                        // 文件项的宽度
        itemHeight       :   "100px",                        // 文件项的高度
        url              :   baseUrl + "mall/goods/uploadGoodsImgs",  // 上传文件的路径
        multiple         :   true,                          // 是否可以多个文件上传
        dragDrop         :   false,                         // 是否可以拖动上传文件
        del              :   true,                          // 是否可以删除文件
        finishDel        :   true,  				        // 是否在上传文件完成后删除预览
        /* 外部获得的回调接口 */
        onSelect: function(files, allFiles){                    // 选择文件的回调方法
//            alert(files.length);
        },
        onDelete: function(file, surplusFiles){                     // 删除一个文件的回调方法
            console.info("当前删除了此文件：");
            console.info(file);
            console.info("当前剩余的文件：");
            console.info(surplusFiles);
        },
        onSuccess: function(file){                    // 文件上传成功的回调方法
            console.info("此文件上传成功：");
            console.info(file);
        },
        onFailure: function(file){                    //文件上传失败的回调方法
            console.info("此文件上传失败：");
            console.info(file);
        },
        onComplete: function(responseInfo){           // 上传完成的回调方法
            console.info("文件上传完成");
            console.info(responseInfo);
        }
    });

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
     "sInfoEmpty": "0条记录",
     "ajax": {
         "url":  baseUrl + '/mall/goods/list'
     },
     "columnDefs": [
         {
             "render": function (data, type, row) {
                 return '<td class="center"><label><input type="checkbox" class="ace" value="'+data+'"/><span class="lbl"></span></label></td>';
             },
             "targets": 0
         }, {
             "render": function (data, type, row) {
                 var name = row.title;
                 var imgUrls = row.imgsUrl;
                 var firstImg;
                 if(!isNullOrEmpty(imgUrls)){
                     firstImg = imgUrls.split(",")[0]
                 }
                 return  '<td align="left"><a href="#"><img src="'+baseUrl + firstImg + '" width="60px" height="60px"/>'+ name + '</a></td>';
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
                     return getLocalTime(data);
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
                 var content = '<a href="#" onclick=freshUrl("' + baseUrl + '/mall/goods/toEidt?id=' + row.id + '")>编辑</a>';
                 content += '| <a href="#" onclick="deleteGoods(' + row.goodsId + ')">删除</a> |';
                 var status = row.status;
                 if(status == 'onsale'){
                     content += '<a href="#" onclick="upOrDownGoods(' + row.goodsId + ',0)">下架</a>';
                 }else if(status == 'instock'){
                     content += '<a href="#" onclick="upOrDownGoods(' + row.goodsId + ',1)">上架</a>';
                 }
                 return content;
             },
             "targets":8
         }
     ],
     "sort":false,
     "columns": [
         {"data": "id"},
         {"data": null},
         {"data": "price"},
         {"data": "quantity"},
         {"data": "exchangeAmount"},
         {"data": "point"},
         {"data": "listTime"},
         {"data": "status"},
         {"data": null}
     ]
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
     amountRow.innerHTML = '<input class="input-sm" type="text" placeholder="" name="skuAmount" onkeyup="fillGoodsAmount(this.value)" style="width: 80px" onblur="fillGoodsAmount()"/>';
     var priceRow = row.insertCell(i+1);
     priceRow.innerHTML = '<input class="input-sm" type="text" placeholder="" name="skuPrice" style="width: 80px"/>';
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
             }else{
                 obj.attr("readonly",false);
                 obj.val(0);
             }
        }
     });
 }

 /***
  *
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
  *  商品编辑时填充属性值
  * */
 function createPropertyHTMLForEdit(id,goodId){
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
                 obj.val(0);
             }
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
  function orderDatas(url,tableId) {
     $("#"+tableId).dataTable({
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
         "sInfoEmpty": "0条记录",
         "ajax": {
             "url": baseUrl + url
         },
         "columnDefs": [
             {
                 "render": function (data, type, row) {
                     return '<td class="center"><label><input type="checkbox" class="ace" value="' + data + '"/><span class="lbl"></span></label></td>';
                 },
                 "targets": 0
             },
             {
                 "render": function (data, type, row) {
                     var name = row.title;
                     var imgUrls = row.imgUrls;
                     var firstImg;
                     if (!isNullOrEmpty(imgUrls)) {
                         firstImg = imgUrls.split(",")[0]
                     }
                     var propName = row.prop;
                     var index = propName.lastIndexOf(';');
                     if (index == propName.length-1) {
                         propName = propName.substring(0, propName.length-1);
                     }
                     var pvalue = '';
                     var parr = propName.split(';');
                     for (var i = 0; i < parr.length; i++) {
                         //alert(parr);
                         var arr = parr[i].split(':');
                         pvalue += arr[3] + ' ';
                     }
                     name += ('&nbsp;' + pvalue);
                     return  '<td align="left"><a href="#"><img src="' + baseUrl + firstImg + '" width="60px" height="60px"/>' + name + '</a></td>';
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
                         return getLocalTime(row.tradeDate);
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
                     ;
                 },
                 "targets": 7
             },
             {
                 "render": function (data, type, row) {
                     var content = '<a href="#" onclick=freshUrl("' + baseUrl + '/mall/order/detail?tid=' + row.tid + '")>查看</a>';

                     var status = row.status;
                     if (status == 'WAIT_SELLER_SEND_GOODS') {
                         content += ' | <a href="javascript:void(0)" onclick="toReceiveTrade(' + row.tid+ ')" >发货</a>';
                     } else {
                         // content += ' | <a href="#" onclick="">已发货</a>';
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
     if(datas.id == null || datas.company == null){
         alert('请填写信息');
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
                     var createTime = getLocalTime(obj.created);
                     html += '<table style="width:100%" border="0px" cellpadding="5px">';
                     html += '<tr style="border: 1px solid #CCCCCC; background-color: #EAF8FF; height: 40px">';
                     html += '<td colspan="4" style="text-align: left">' + createTime + '  订单号:' + tNo + '</td>';
                     html += '</tr>';
                     var size = obj.mallGoodsSkuDoList;
                     $.each(obj.mallGoodsSkuDoList, function(ind, sku){
                         html += '<tr>';
                         html += '<td style="text-align: left"><img src="' + baseUrl + '/' + sku.goodsImgUrl + '" width="60px" height="60px">' + sku.goodsName +  '</td>';
                         html += '<td>' + obj.name + '</td>';
                         html += '<td>' + sku.buyAmount + '</td>';
                         if(ind == 0){
                             html += '<td rowspan="' + size + '"><input type="text" name="shipId_' + tid + '" value=""/></td>';
                         }
                         html += '</tr>';
                     });
                     html += '</table>';
                     console.log(index + ' :  ' + obj.tid);
                 })
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

 function test(){
     var str = '2:4:材质:纯银;3:7:尺寸:5MM;4:10:颜色:蓝色;';
     alert(str);
     var index = str.lastIndexOf(';');
     str = str.substring(0,str.length-1);
     alert(str);
     alert(index + "    " + str.length);
 }







