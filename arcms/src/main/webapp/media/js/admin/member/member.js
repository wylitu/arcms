var signCount = 1;
$(document).ready(
        function () {
            $('.delIcon').on("click",function(){
                    $(this).parent().parent().remove();
                });
        }
    );

function addSignInCofig(){
    signCount++;

    $("#package1").append('<div class="col-xs-16 col-sm-12" id="input_group_'+signCount+'" style="margin-top: 10px"><div class="col-sm-3"></div><div class="integralSignIn1 input-group">&nbsp;会员等级：&nbsp;&nbsp;<input type="text" class="memberLevel" id="memberLevel_'+signCount+'"/>&nbsp;&nbsp;&nbsp;&nbsp;购物金额满：&nbsp;&nbsp;<input  type="text" class="shopMoney" id="shopMoney_'+signCount+'"/>&nbsp;&nbsp;<span class="delIcon" id="del_'+signCount+'"></span></div> </div>');

    $('.delIcon').on("click",function(){
        $(this).parent().parent().remove();
    });
}

function saveOrUpdateFrom(){
//var data = $('#signInConifgForm').serialize();
var ids = [];

var errCode = 0;

$(".integralSignIn1").each(function(index){

       var memberLevel = $(this).find(".memberLevel").val();
       var shopMoney = $(this).find(".shopMoney").val();

       if ((memberLevel == null || memberLevel =='') || (shopMoney == null || shopMoney == '')){
           alert("信息不能为空。");
           errCode = -1;
           return;
       }

       if (memberLevel !=null && shopMoney!=null){
           var member = {"name":memberLevel,"value":shopMoney};
           ids.push(member);
       }

   });
   if (errCode != -1){
       $.ajax({
           type : "post",
           url : baseUrl+"member/memberLevelSave",
           //data : {signInConfig:ids.toString()},
           data : JSON.stringify(ids),
           async:false,
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
}

function addMemberReturn(){
    signCount++;
    $("#package1").append('<div class="col-xs-16 col-sm-12" id="input_group_'+signCount+'" style="margin-top: 10px"><div class="col-sm-3"></div><div class="integralSignIn1 input-group">&nbsp;会员等级：&nbsp;&nbsp;&nbsp;<select name="memberLevel_'+signCount+'" id="memberLevel_'+signCount+'" class="memberLevel">'+$(".select").html()+'</select>&nbsp;&nbsp;&nbsp;人民币返现比例：&nbsp;&nbsp;<input  type="text" class="rebReturnRatio" id="rebReturnRatio_'+signCount+'"/>&nbsp;&nbsp;爱卡币返现比例：&nbsp;&nbsp;<input  type="text" class="icardReturnRatio" id="icardReturnRatio_'+signCount+'"/>&nbsp;&nbsp;<span class="delIcon" id="del_'+signCount+'"></span></div> </div>');
    $('.delIcon').on("click",function(){
        $(this).parent().parent().remove();
    });
}
function saveMemberReturn(){
    var ids = [];
    var errCode = 0;

    $(".integralSignIn1").each(function(index){
           var memberLevel =  $(this).find(".memberLevel").val();
           var rebReturnRatio = $(this).find(".rebReturnRatio").val();
           var icardReturnRatio = $(this).find(".icardReturnRatio").val();

           if ((memberLevel == null || memberLevel =='') || (rebReturnRatio == null || rebReturnRatio == '')|| (icardReturnRatio == null || icardReturnRatio == '')){
               alert("信息不能为空。");
               errCode = -1;
               return;
           }

           if (memberLevel !=null && rebReturnRatio!=null&& icardReturnRatio!=null){
               var member = {"memberLevel":memberLevel,"rebReturnRatio":rebReturnRatio,"icardReturnRatio":icardReturnRatio};
               ids.push(member);
           }

       });
       if (errCode != -1){
           $.ajax({
               type : "post",
               url : baseUrl+"member/memberRatioSave",
               data : JSON.stringify(ids),
               async:false,
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

}






