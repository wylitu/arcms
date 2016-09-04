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
        "url":  baseUrl + '/goods/list',
        "data": function(d){
            var title = $("#searchGoodsForm :input[name='title']").val();
            var isVir = $("#searchGoodsForm :input[name='isVir']").val();

            if (!isNullOrEmpty(title)) {
                d.title = title;
            }

            if (!isNullOrEmpty(isVir)) {
                d.isVirtual = isVir;
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
        },{
            "render": function (data, type, row) {
                return data;
            },
            "targets": 1
        }, {
            "render": function (data, type, row) {
                var html = '<td align="left">';
                html += '<div style="position:relative;overflow:hidden;">';
                html += '<img src="'+ baseUrl + data + '" width="60px" height="60px" style="float:left" alt="速牛科技" title="' + name + '"/>';
                html += '</div>';
                html += '</td>';
                return html;
            },
            "targets":2
        },{
            "render": function (data, type, row) {
                return shopName;
            },
            "targets":3
        },{
            "render": function (data, type, row) {
                if(data == 1){
                    return '虚拟商品';
                }else if(data == 0){
                    return '实物商品';
                }
                return '未知';
            },
            "targets":4
        },{
            "render": function (data, type, row) {
                if(data == true){
                    return '已审核';
                }else if(data == false){
                    var reason = '';
                    if(row.auditReason){
                        reason = row.auditReason;
                    }
                    return '未通过审核 ' + '<a color="red">原因：（' + reason + ')</a>';
                }
                return '未知';
            },
            "targets":5
        },{
            "render": function (data, type, row) {
                if(data == 'checked'){
                    return '已审核';
                }else if(data == 'unchecked'){
                    return '未审核';
                }
                return '未知';
            },
            "targets":6
        }, {
            "render": function (data, type, row) {
                var price = data;
                return "￥"+ price.toFixed(2);
            },
            "targets":7
        },{
            "render": function (data, type, row) {
                //var currentPrice = (row.price * row.discount) / 10;
                return '￥' + data;
            },
            "targets":8
        },{
            "render": function (data, type, row) {
                return data;

            },
            "targets":9
        },{
            "render": function (data, type, row) {
                var content = '';
                var status = row.status;
                if(row.status == 'instock'){
//                     content = '<a href="#" onclick=freshUrl("' + baseUrl + '/mall/goods/toEidt?id=' + row.id + '")>编辑</a>';
                    content = '<a href="#" onclick="freshUrl(\'' + baseUrl + 'goods/edit?goodsId=' + row.goodsId + '\');return false;">编辑</a>';
                    content += '| <a href="#" onclick="deleteGoods(' + row.goodsId + ');return false;">删除</a> |';
                }else if(row.status == 'onsale'){
                    content += '<a href="#" onclick="deleteGoods(' + row.goodsId + ');return false;">删除</a> |';
                }
//                 var content = '<a href="#" onclick=freshUrl("' + baseUrl + '/mall/goods/toEidt?id=' + row.id + '")>编辑</a>';
//                 content += '| <a href="#" onclick="deleteGoods(' + row.goodsId + ')">删除</a> |';
                if(row.isAudit == true){
                    if(status == 'onsale'){
                        content += '<a href="#" onclick="upOrDownGoods(' + row.goodsId + ',0);return false;">下架</a>';
                    }else if(status == 'instock'){
                        content += '<a href="#" onclick="upOrDownGoods(' + row.goodsId + ',1);return false;">上架</a>';
                    }
                }else{
                    content += '<a href="#" onclick="auditGoods(' + row.goodsId + ');return false;">审核</a>';
                }
                return content;
            },
            "targets":10
        }
    ],
    "sort": false,
    "columns": [
        {"data": "goodsId","sorting":false},
        {"data": "title","sorting":false},
        {"data": "thumbnail","sorting":false},
        {"data": null,"sorting":false},
        {"data": "isVirtual", "sorting" : false},
        {"data": "isAudit", "sorting" : false},
        {"data": "status","sorting":false},
        {"data": "price","sorting":false},
        {"data": "nowPrice","sorting":false},
        {"data": "expiredEndTime","sorting":false},
        {"data": null,"sorting":false}
    ]
});

function deleteGoods(id){
    if(window.confirm("确认删除商品")){
        $.post(baseUrl + '/goods/delete', {id: id}, function(data){
            $('#goodsList').DataTable().draw();
            alert(data.errorMessage)
        })
    }
}

function upOrDownGoods(id){
    $.post(baseUrl + '/goods/updown', {id: id}, function(data){
        $('#goodsList').DataTable().draw();
        alert(data.errorMessage)
    })
}

function auditGoods(id){
    $('#auditModal').modal()
    $('#auditGoodsId').val(id)
    $('#auditReason').val('')
}

$('#auditGoodsBtn').click(function(event){
    event.preventDefault()
    var id = $('#auditGoodsId').val()
    var isAudit = $('#isAudit').val()
    var auditReason, data
    if(isAudit == 0){
        auditReason = $('#auditReason').val()
        data = {id: id, isAudit: isAudit, auditReason : auditReason}
    }else{
        data = {id: id, isAudit: isAudit}
    }

    $.post(baseUrl + '/goods/audit', data, function(data){
        $('#goodsList').DataTable().draw();
        alert(data.errorMessage)
    })
})

$('#isAudit').change(function(event){
    event.preventDefault()
    var isAudit = $(this).val()
    if(isAudit == 1){
        $('#auditReasonDiv').hide()
    }else{
        $('#auditReasonDiv').show()

    }

})
//查询
$("#searchGoodsForm").submit(function () {
    $('#goodsList').DataTable().draw();
    return false; //阻止表单默认提交
});

/**
 * 添加商品的提交
 * */
function submitForm(id) {
    var imgId = [];
    //商品名
    var goodsName = $('input[name="goodsName"]').val();
    if (isNullOrEmpty(goodsName)) {
        alert('商品名不能为空');
        return;
    }
    //商品简介
    var goodsSummary = $('input[name="goodsSummary"]').val();
    //商品分类
    var category = $('select[name="category"]').val();
    if (isNullOrEmpty(category)) {
        alert('商品分类不能为空');
        return;
    }
    //商品类型
    var goodsType = $('input[name="goodsType"]:checked').val();

    //商品总数量
    var goodsAmount = $('input[name="goodsAmount"]').val();
    if (isNullOrEmpty(goodsAmount)) {
        alert('商品总数量必填');
        return;
    }
    if (!checkQuantityById('goodsAmount')) {
        alert('请输入数量,正整数');
        return;
    }
    //商品价格
    var goodsPrice = $('input[name="goodsPrice"]').val();
    if (isNullOrEmpty(goodsPrice)) {
        alert('商品价格必填');
        return;
    }
    if (!checkPriceById('goodsPrice')) {
        alert('请输入价格,最多保留两位小数');
        return;
    }
    //商品折扣
    var goodsNowPrice = $('input[name="goodsNowPrice"]').val();

    //有效期开始时间
    var startDate = $('input[name="startDate"]').val();
    //有效期结束时间
    var endDate = $('input[name="endDate"]').val();
    //商品详情
    var goodsDetail = goodsDetailEditor.getContent();
    if (isNullOrEmpty(goodsDetail)) {
        alert('商品详情必填');
        return;
    }
    //套餐详情
    var comboIds = $("#comboIds").val();

    //购买须知
    var purchaseNotes = purchaseNotesEditor.getContent();
    if (isNullOrEmpty(purchaseNotes)) {
        alert('购买须知必填');
        return;
    }

    //图片
    var fileNames = '';
    $.each($('input[type="file"]'), function (index, obj) {
        var fn = $('input[type="file"]').eq(index).attr("name");
        fileNames += fn;
        if (index < $('input[type="file"]').length - 1) {
            fileNames += '|';
        }
        imgId.push($('input[type="file"]').eq(index).attr("id"));
    });
//    var token = $('#token').val();
    var data = {"goodsName": goodsName,"goodsSummary":goodsSummary, "category": category, "goodsType": goodsType, "goodsAmount": goodsAmount, "goodsPrice": goodsPrice, "goodsNowPrice": goodsNowPrice, "startDate": startDate, "endDate":endDate, "goodsDetail": escape(goodsDetail), "comboIds": comboIds, "purchaseNotes": escape(purchaseNotes), "fileNames": fileNames};
    var postData = JSON.stringify(data).replace(/\"/g, "'");
    $.ajaxFileUpload({
        url: baseUrl + 'goods/ajaxCreateGoods',
        secureuri: false,
        fileElementId: imgId,  //这里不在是以前的id了，要写成数组的形式哦！
        dataType: 'json',
        data: {goodsData: postData},
        success: function (reponseData) {
            if (reponseData.errorCode == 0) {
                alert(reponseData.errorMessage);
                freshUrl(baseUrl + "goods/index");
            } else {
                alert(reponseData.errorMessage);
            }
        },
        error: function (reponseData) {
            alert("保存失败!");
        }
    });
}

/**
 * 更新商品的提交
 * */
function submitEditForm(id) {
    var imgId = [];
    //商品名
    var goodsId = $('input[name="goodsId"]').val();
    if (isNullOrEmpty(goodsId)) {
        alert('请刷新页面');
        return;
    }
    var goodsName = $('input[name="goodsName"]').val();
    if (isNullOrEmpty(goodsName)) {
        alert('商品名不能为空');
        return;
    }
    //商品简介
    var goodsSummary = $('input[name="goodsSummary"]').val();
    //商品分类
    var category = $('select[name="category"]').val();
    if (isNullOrEmpty(category)) {
        alert('商品分类不能为空');
        return;
    }
    //商品类型
    var goodsType = $('input[name="goodsType"]:checked').val();

    //商品总数量
    var goodsAmount = $('input[name="goodsAmount"]').val();
    if (isNullOrEmpty(goodsAmount)) {
        alert('商品总数量必填');
        return;
    }
    if (!checkQuantityById('goodsAmount')) {
        alert('请输入数量,正整数');
        return;
    }
    //商品价格
    var goodsPrice = $('input[name="goodsPrice"]').val();
    if (isNullOrEmpty(goodsPrice)) {
        alert('商品价格必填');
        return;
    }
    if (!checkPriceById('goodsPrice')) {
        alert('请输入价格,最多保留两位小数');
        return;
    }
    //商品折扣
    var goodsNowPrice = $('input[name="goodsNowPrice"]').val();

    //有效期开始时间
    var startDate = $('input[name="startDate"]').val();
    //有效期结束时间
    var endDate = $('input[name="endDate"]').val();
    //商品详情
    var goodsDetail = goodsDetailEditor.getContent();
    if (isNullOrEmpty(goodsDetail)) {
        alert('商品详情必填');
        return;
    }
    //套餐详情
    var comboIds = $("#comboIds").val();

    //购买须知
    var purchaseNotes = purchaseNotesEditor.getContent();
    if (isNullOrEmpty(purchaseNotes)) {
        alert('购买须知必填');
        return;
    }

    //图片
    var fileNames = '';
    $.each($('input[type="file"]'), function (index, obj) {
        var fn = $('input[type="file"]').eq(index).attr("name");
        fileNames += fn;
        if (index < $('input[type="file"]').length - 1) {
            fileNames += '|';
        }
        imgId.push($('input[type="file"]').eq(index).attr("id"));
    });
//    var token = $('#token').val();
    var data = {"goodsId": goodsId, "goodsName": goodsName,"goodsSummary":goodsSummary, "category": category, "goodsType": goodsType, "goodsAmount": goodsAmount, "goodsPrice": goodsPrice, "goodsNowPrice": goodsNowPrice, "startDate": startDate, "endDate":endDate, "goodsDetail": escape(goodsDetail), "comboIds": comboIds, "purchaseNotes": escape(purchaseNotes), "fileNames": fileNames};
    var postData = JSON.stringify(data).replace(/\"/g, "'");
    $.ajaxFileUpload({
        url: baseUrl + 'goods/doEdit',
        secureuri: false,
        fileElementId: imgId,  //这里不在是以前的id了，要写成数组的形式哦！
        dataType: 'json',
        data: {goodsData: postData},
        success: function (reponseData) {
            if (reponseData.errorCode == 0) {
                alert(reponseData.errorMessage);
                freshUrl(baseUrl + "goods/index");
            } else {
                alert(reponseData.errorMessage);
            }
        },
        error: function (reponseData) {
            alert("保存失败!");
        }
    });
}

/**
 * 选择套餐
 */
var TableModal
function selectCombo() {
    //弹出悬浮层，展现套餐列表
    var poolId = $('#comboIds').val();
    TableModal = $('#HBTable').DataTable({
        "language": {
            "paginate": {
                "first": "首页",
                "last": "末页",
                "next": "下一页",
                "previous": "上一页"
            },
            "lengthMenu": "每页 _MENU_ 条记录",
            "zeroRecords": "没有数据",
            "info": "共 _TOTAL_ 记录， 当前第 _PAGE_ 页",
            "infoFiltered": ""
        },
        "pagingType": "full_numbers",
        "bDestroy":true,
        "processing": true,
        "searching": false,
        "serverSide": true,
        "ajax": {
            "url": baseUrl + 'goods/comboList',
            "data": function (d) {
                var name = $("#searchForm :input[name='title']").val();
//                var type = $("#searchForm :input[name='type']").val();

                if (!isNullOrEmpty(name)) {
                    d.title = name;
                }

            }
        },
        "columnDefs": [
            {
                "render": function (data, type, row) {
                    if(poolId.indexOf(data) >= 0){
                        return '<td class="center"><label><input name="cbox" type="checkbox" class="ace" value="'+data+'" checked/><span class="lbl"></span></label></td>';
                    }
                    return '<td class="center"><label><input name="cbox" type="checkbox" class="ace" value="'+data+'"/><span class="lbl"></span></label></td>';
                },
                "targets": 0
            }, {
                "render": function (data, type, row) {
                    return data;
                },
                "targets":1
            },{
                "render": function (data, type, row) {
                    return data;
                },
                "targets":2
            },{
                "render": function (data, type, row) {
                    return data;
                },
                "targets":3
            }, {
                "render" : function(data, type, row) {
                    return row.price * row.quantity;
                },
                "targets":4
            }

        ],
        "sort":false,
        "columns": [
            {"data": "comboId"},
            {"data": "title"},
            {"data": "price"},
            {"data": "quantity"},
            {"data": null}
        ]
    });
    $('#comboModal').modal('show');
    $('#searchBut').click(function(){
        TableModal.draw();
    });
}

$('#combosubmitBut').click(function(){
    var comboIds = ''
    $('#combo_tb tr').first().siblings().remove()
    $("input[name='cbox']").each(function(){
        if($(this).prop('checked')) {
            var poolId = $(this).val();
            comboIds += poolId + ';'
            var tr = $(this).parent().parent().parent().clone()
            tr.find('td').first().remove()
            console.log(tr.parent())
            $('#combo_tb tbody').append('<tr>' + tr.html() + '</tr>')

        }
    })
    $('#comboIds').val(comboIds)
})

$('#comboAdd').click(function(){
    var imgId = [];
    //商品名
    var title = $('#addForm input[name="title"]').val();
    if (isNullOrEmpty(title)) {
        alert('名称不能为空');
        return;
    }
    var price = $('#addForm input[name="price"]').val();
    if (isNullOrEmpty(price)) {
        alert('价格不能为空');
        return;
    }
    if (!checkPriceById('comboPrice')) {
        alert('请输入数量');
        return;
    }

    var quantity = $('#addForm input[name="quantity"]').val();
    if (isNullOrEmpty(quantity)) {
        alert('价格不能为空');
        return;
    }
    if (!checkQuantityById('comboQuantity')) {
        alert('请输入数量');
        return;
    }
    var data = {title : title, price : price, quantity: quantity}
    $.ajaxFileUpload({
        url: baseUrl + 'goods/combo/add',
        secureuri: false,
        fileElementId: imgId,  //这里不在是以前的id了，要写成数组的形式哦！
        dataType: 'json',
        data: data,
        success: function (reponseData) {
            if (reponseData.errorCode == 0) {
                alert(reponseData.errorMessage);
                //freshUrl(baseUrl + "goods/index");
                TableModal.draw()
            } else {
                alert(reponseData.errorMessage);
            }
        },
        error: function (reponseData) {
            alert("保存失败!");
        }
    });

})