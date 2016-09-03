
integraSignRuleDetail();
/**
 * 获取表单详细信息
 */
function integraSignRuleDetail(){
    $.post( baseUrl+"/integral/querySignIntegral", '', function(data){
        if(!isNullOrEmpty(data.signInPointConfigVO)){
            loadData(data.signInPointConfigVO);
        }
    }, "json")
}


/**
 * 加载数据
 * @param topSelector
 * @param obj
 */
function loadData(obj){

    $.each(obj.signInVOList,function(index,value){
        if(index>1){
            loadSignIntegralInput(index)
        }
        var r = index+1;

        $('#signInNum_'+r).val(value.signInTimes);
        $('#integralNum_'+r).val(value.point);
        $('#getConfig_'+r).val(value.getTimes);
    });
}

function loadSignIntegralInput(index){
    var i = index +1;
    $("#mvGruop").append('<div class="integralSignIn col-xs-16 col-sm-10" id="input_group_'+i+'" style="margin-top: 10px"><div class="input-group" style="margin-left: 200px;"><span class="ml20" style="margin-left: 15px">每连续签到</span><input  type="text" id="signInNum_'+i+'" style="margin-left: 10px;margin-right: 10px;"/>天<span class="ml20" style="margin-left: 15px;">送积分</span><input  type="text" id="integralNum_'+i+'" style="margin-left: 10px;margin-right: 10px"/>个<span class="ml20" style="margin-left: 15px;">领取限制</span><input id="getConfig_'+i+'" type="radio" class="ace" value="1" checked="checked"/><span class="lbl" style="margin-left: 15px;">每人仅限领取一次</span></div></div>');
}

var signCount = 3;
function addSignInCofig(){
    $("#mvGruop").append('<div class="integralSignIn col-xs-16 col-sm-10" id="input_group_'+signCount+'" style="margin-top: 10px"><div class="input-group" style="margin-left: 200px;"><span class="ml20" style="margin-left: 15px">每连续签到</span><input  type="text" id="signInNum_'+signCount+'" style="margin-left: 10px;margin-right: 10px;"/>天<span class="ml20" style="margin-left: 15px;">送积分</span><input  type="text" id="integralNum_'+signCount+'" style="margin-left: 10px;margin-right: 10px"/>个<span class="ml20" style="margin-left: 15px;">领取限制</span><input id="getConfig_'+signCount+'" type="radio" class="ace" value="1" checked="checked"/><span class="lbl" style="margin-left: 15px;">每人仅限领取一次</span></div></div>');
    signCount++;

}


function saveOrUpdateFrom(){
    //var data = $('#signInConifgForm').serialize();
    var ids = [];
    $(".integralSignIn").each(function(index){
        var i = index+1;
        var signInNum = $("#signInNum_"+i).val();



        var point = $("#integralNum_"+i).val();

        var getNum = $("#getConfig_"+i).val();


        //ids
        var person = {"signInTimes":signInNum,"point":point,"getTimes":getNum};

        ids.push(person);
    });
    //var saveDataAry=[];
    //var data1={"signInTimes":1,"point":2};
    //var data2={"signInTimes":2,"point":3};
    //saveDataAry.push(data1);
    //saveDataAry.push(data2);
    $.ajax({
        type : "post",
        url : baseUrl+"/signIntegralConfig/save",
        //data : {signInConfig:ids.toString()},
        data : JSON.stringify(ids),
        dataType : "json",
        contentType:"application/json",
        success : function(data){
            if (data.errorCode == '0'){
                alert("保存成功！");
            }else{
                alert("保存失败！");
                return false;
            }
        }
    });








}








var oTable = $('#signPointQueryTable').dataTable({

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
    "processing": true,
    "searching": false,
    "serverSide": true,
    "ajax": {
        "url": baseUrl + '/integral/signIntegralDetailList',
        "data": function (d) {

            //var wxNickName = $("#pointUserQueryForm :input[name='wxNickName']").val();

            //if (!isNullOrEmpty(mobile)) {
            //    d.mobile = mobile;
            //}
            //
            //if (!isNullOrEmpty(wxNickName)) {
            //    d.wxNickName = wxNickName;
            //}
            //
            //if (!isNullOrEmpty(queryType)) {
            //    d.queryType = queryType;
            //}

        }
    },
    "columnDefs": [
        {
            "render": function (data, type, row) {
                return data;
            },
            "targets": 0
        },
        {
            "render": function (data, type, row) {

                return data;
            },
            "targets": 4
        },
        {
            "render": function (data, type, row) {

                return data;

            },
            "targets": 5
        }, {
            "render": function (data, type, row) {
                return data;
            },
            "targets": 6
        }, {
            "render": function (data, type, row) {
                return data;
            },
            "targets": 7
        },
//                    {"visible": false, "targets": [3]}
    ],
    "sort": false,
    "columns": [
        {"data": "wxNickName"},
        {"data": "sex"},
        {"data": "amount"},
        {"data": "signInTimes"},
        {"data": "lastSignInDate"},
        {"data": "pointAmountTotal"},
        {"data": "isOldUser"},
        {"data": "mobile"},
        {"data": "name"},
        {"data": "signInTimes"},

    ]
});



