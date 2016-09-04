var statusDict = {
    WAIT_BUYER_PAY : '未付款',
    WAIT_BUYER_CONSUME : '未消费',
    WAIT_BUYER_JUDGE : '未评价',
    COMPLETED : '已完成',
    REFUND : '已退款',
    CANCELED : '已取消'}

var payTypeDict ={
    ICARDPAY : '爱卡支付',
    WXPAY : '微信支付',
    ALIPAY : '支付宝支付',
    UNIPAY : '银联支付'
}
 /**
  * 订单列表dataTable
  * */
 var tradeList = $('#tradeList').dataTable({
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
         "url":  baseUrl + '/trade/list',
         "data": function(d){
             var tid = $("#searchTradeForm :input[name='tid']").val();
             var status = $("#searchTradeForm :input[name='status']").val();

             if (!isNullOrEmpty(tid)) {
                 d.tid = tid;
             }

             if (!isNullOrEmpty(status)) {
                 d.status = status;
             }
         }
     },
     "columnDefs": [
         {
             "render": function (data, type, row) {

                 return statusDict[data] ? statusDict[data] : '未知状态';
             },
             "targets": 1
         }, {
             "render": function (data, type, row) {
                 return data + '元'
             },
             "targets":2
         }, {
             "render": function (data, type, row) {
                //return data;
                 return payTypeDict[data] == null ? '' : payTypeDict[data];
             },
             "targets":3
         }, {
             "render": function (data, type, row) {
                 return data;
             },
             "targets":4
         }, {
             "render": function (data, type, row) {
                 var str = '<a target="_blank" class="edit" tid="' + row.tid + '">修改备注</a>'
                 /*if(row.status == 'WAIT_BUYER_CONSUME'){
                    str += '| <a target="_blank" class="cardConsume" tid="' + row.tid + '">确认消费</a>'
                 }*/
                 return str;
             },
             "targets":10
         }
     ],
     "sort": false,
     "columns": [
         {"data":  "tid"},
         {"data": "status","sorting":false},
         {"data": "fee","sorting":false},
         {"data": "payType","sorting":false},
         {"data": "payTime","sorting":false},
         {"data": "source","sorting":false},
         {"data": "userName","sorting":false},
         {"data": "gmtCreated","sorting":false},
         {"data": "createdBy","sorting":false},
         {"data": "title","sorting":false},
         {"data": null}
     ]
 });
// 查看
$('#contentList').on("click", ".publish", function(){
    var contentId = $(this).attr('value')
    var status = $(this).innerHTML;
    $.get(baseUrl + '/content/publish?contentId=' + contentId, function(data){
        $(this).innerHTML = status == '发布' ? '取消发布' : '发布';
        $('#contentList').DataTable().draw();
    });
})
 // 编辑
 $('#tradeList').on("click", ".edit", function(event){
     event.preventDefault()
     var tid = $(this).attr('tid')
     $('#updateTid').val(tid)
     $('#commentModal').modal()
 })

$('#tradeList').on("click", ".cardConsume", function(event){
    event.preventDefault()
    var tid = $(this).attr('tid')
    if(confirm('确认消费?')){
        $.get(baseUrl + '/trade/confirmConsume?tradeId=' + tid, function(data){
            if(data.errorCode == 0){
                alert('消费成功！')
                $('#tradeList').DataTable().draw();
            }
        })
    }
})
$('#updateCommentBtn').click(function(){
    var tid = $('#updateTid').val()
    var comment = $('#comment').val()
    $.get(baseUrl + '/trade/updateComment?tradeId=' + tid + '&comment=' + comment, function(data){
        if(data.errorCode == 0){
            alert('更新成功！')
        }
    })
})

$('#cardConsume').click(function(event){
    $('#cardConsumeModal').modal()
    event.preventDefault()
})
$('#consumeCardBtn').click(function(){
    var cardNo = $('#cardNo').val()
    $.get(baseUrl + 'trade/orderConfirmSubmit?cardNum=' + cardNo, function(data){
        alert(data.errorMessage)
    })
})

$('#selectAll').click(function(){
    console.log(this.checked)
    $('#contentList td input[type="checkbox"]').attr('checked', this.checked)
    console.log(this.checked)

    $('#contentList td input[type="checkbox"]').prop('checked', this.checked)
})
 $('#tradeSearch').click(function(event){
     $('#tradeList').DataTable().draw();
     event.preventDefault()
 })
$('select[name="type"]').change(function(){
    var type = $(this).val()
    var type2content = {'advertisement': '750 *300',
                        'icard_service' : '36*36',
                        'article' : '170*115'}
    $('#banner_content').html('图片规格: ' + type2content['article'] + ', 不超过1M, 支持jpg,jpeg,png,bmp,gif格式')

    $("#banner").show()
    if(type == 'advertisement' || type == 'icard_service' || type == 'article'){
        $('#banner_content').html('图片规格: ' + type2content[type] + ', 不超过1M, 支持jpg,jpeg,png,bmp,gif格式')
    }
    if(type == 'icard_service'){
        $('#linkUrl').show()
        $('#content_detail').hide()
    }else{
        $('#linkUrl').hide()
        $('#content_detail').show()

    }
})

$('#contentSearch').click(function(event){
    event.preventDefault()
    $('#contentList').DataTable().draw();
})

$('#contentAdd').on('click',function(){
    try {
        freshUrl(baseUrl + '/content/add');
    }catch(e){
        console.dir(e);
    }
    return false;
});
$('#contentDelete').click(function(event){
    event.preventDefault()
    var checkedContents = $('#contentList input[type="checkbox"]:checked')
    var contentIds = []
    $.each(checkedContents, function(index, content){
        contentIds.push($(content).val())
    })

    $.ajax({
        traditional:true,
        type: "POST",
        url: baseUrl + "/content/doDelete",
        data: {deletedIds : contentIds},
        //headers: {
        //    'Accept': 'application/json',
        //    'Content-Type': 'application/json'
        //},
        dataType: "json",
        success: function (event, XMLHttpRequest, ajaxOptions) {
            $('#contentList').DataTable().draw();
        },
        error: function (event, jqXHR, ajaxSettings, thrownError) {
        }
    });
    //$.post(baseUrl + "/content/doDelete", {deletedIds : contentIds}, function(data){
    //    $('#contentList').DataTable().draw();
    //})
})

 $('#updateContent').click(function(){
     var imgId = [];
     var title = $('#updateForm input[name="title"]').val();
     if (isNullOrEmpty(title)) {
         alert('文章标题不能为空');
         return;
     }
     var sort = $('#updateForm input[name="sort"]').val();
     var id = $('#updateForm input[name="id"]').val();
     if (isNullOrEmpty(sort)) {
         alert('文章顺序不能为空');
         return;
     }
     if(!sort.match(REG_QUANTITY)){
         alert('文章顺序为数字')
         return
     }
     var articlePosition = $('#updateForm select[name="articlePosition"]').val();
     if (isNullOrEmpty(articlePosition)) {
         alert('文章位置不能为空');
         return;
     }
     var type = $('#updateForm select[name="type"]').val();
     if (isNullOrEmpty(type)) {
         alert('文章类型必填');
         return;
     }

     var linkUrl = $('#updateForm input[name="linkUrl"]').val();
     if(type == 'icard_service'){
         if (isNullOrEmpty(linkUrl)) {
             alert('链接必填');
             return;
         }
     }
     if(type == 'advertisement'){
         var file = $('#updateForm input[name="file"]').val();
         if (isNullOrEmpty(file)) {
             alert('图片不能为空');
             return;
         }
     }
     //套餐详情
     var contentDetail11 = editContentDetail.getContent();
     if(type != 'icard_service') {
         if (isNullOrEmpty(contentDetail11)) {
             alert('文章内容必填');
             return;
         }
     }
     var status = $('#updateForm select[name="status"]').val();
     if (isNullOrEmpty(status)) {
         alert('发布状态不能为空');
         return;
     }
     //图片
     var fileNames = '';
     $.each($('#updateForm input[type="file"]'), function (index, obj) {
         imgId.push($('#updateForm input[type="file"]').eq(index).attr("id"));
     });
//    var token = $('#token').val();
     contentDetail11 = contentDetail11.replace(/\"/g, "'");

     var data = {"id" : id, "status": status, "title": title, "sort": sort, "type":type, "articlePosition": articlePosition, "contentDetail": contentDetail11, "linkUrl": linkUrl};
     //var postData = JSON.stringify(data).replace(/\"/g, "'");
     $.ajaxFileUpload({
         url: baseUrl + 'content/doEdit',
         secureuri: false,
         fileElementId: imgId,  //这里不在是以前的id了，要写成数组的形式哦！
         dataType: 'json',
         data: data,
         success: function (reponseData) {
             if (reponseData.errorCode == 0) {
                 alert(reponseData.errorMessage);
                 freshUrl(baseUrl + "content/index");
             } else {
                 alert(reponseData.errorMessage);
             }
         },
         error: function (reponseData) {
             alert("保存失败!");
         }
     });
 })

 var REG_QUANTITY = /^([1-9]\d*)$/;
/**
 * 添加文章的提交
 * */
function submitForm(id) {
    var imgId = [];
    var title = $('#addContent input[name="title"]').val();
    if (isNullOrEmpty(title)) {
        alert('文章标题不能为空');
        return;
    }
    var sort = $('#addContent input[name="sort"]').val();
    if (isNullOrEmpty(sort)) {
        alert('文章顺序不能为空');
        return;
    }
    var status = $('#addContent select[name="status"]').val();
    if (isNullOrEmpty(status)) {
        alert('发布状态不能为空');
        return;
    }
    if(!sort.match(REG_QUANTITY)){
        alert('文章顺序为数字')
        return
    }
    var articlePosition = $('#addContent select[name="articlePosition"]').val();
    if (isNullOrEmpty(articlePosition)) {
        alert('文章位置不能为空');
        return;
    }
    var type = $('#addContent select[name="type"]').val();
    if (isNullOrEmpty(type)) {
        alert('文章类型必填');
        return;
    }
    var linkUrl = $('#addContent input[name="linkUrl"]').val();
    if(type == 'icard_service'){
        if (isNullOrEmpty(linkUrl)) {
            alert('链接必填');
            return;
        }
    }
    if(type == 'advertisement'){
        var file = $('#addContent input[name="file"]').val();
        if (isNullOrEmpty(file)) {
            alert('图片不能为空');
            return;
        }
    }
    //套餐详情
    var contentDetail11 = contentDetail.getContent();
    console.log(contentDetail11)
    if(type != 'icard_service') {
        if (isNullOrEmpty(contentDetail11)) {
            alert('文章内容必填');
            return;
        }
    }

    //图片
    var fileNames = '';
    $.each($('#addContent input[type="file"]'), function (index, obj) {
        imgId.push($('#addContent input[type="file"]').eq(index).attr("id"));
    });
//    var token = $('#token').val();
    contentDetail11 = contentDetail11.replace(/\"/g, "'");
    var data = {"status": status, "title": title, "sort": sort, "type":type, "articlePosition": articlePosition, "linkUrl": linkUrl, "contentDetail": contentDetail11};
    //var postData = JSON.stringify(data).replace(/\"/g, "'");
    //console.log(postData)
    console.log(data)
    $.ajaxFileUpload({
        url: baseUrl + 'content/doAdd',
        secureuri: false,
        fileElementId: imgId,  //这里不在是以前的id了，要写成数组的形式哦！
        dataType: 'json',
        contentType: 'json',
        data: data,
        success: function (reponseData) {
            alert('添加成功!')
        },
        error: function (reponseData) {
            alert("保存失败!");
        }
    });
}


