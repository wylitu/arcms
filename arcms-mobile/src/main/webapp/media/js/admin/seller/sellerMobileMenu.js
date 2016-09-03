/**
 * 查看详细信息方法
 * @type {*|jQuery}
 */
detail();

/**
 * 获取表单详细信息
 */
function  detail(){
    $.post( baseUrl+"/sellerMobileMenu/detail",  function(data){
        if(!isNullOrEmpty(data.sellerMobileMenus)){
            $.each(data.sellerMobileMenus,function(i,value){
                var  isSelect=value.isSelect;
                var  name=value.name;
                var functionType=value.functionType;
                var isShowOther=value.isShowOther;
                $(":input[type='hidden'][name='"+name+"']").val(value.id);
                if("0"==isSelect){
                    return true;
                }
                $(":input[name='isShowOther'][value='"+isShowOther+"']").prop("checked","checked");
                if("advertisement"==functionType){
                    var  link=value.link;
                    var  url=value.url;
                    $("#menuForm  img[name='"+ name+"']").css("display","block");
                    $("#menuForm  img[name='"+ name+"']").attr("src",baseUrl+url);
                    $("."+name).val(link)
                }else{
                    $(":input[name='"+name+"']").prop("checked","checked");
                }
            })

        }
    }, "json")
}

/**
 * 保存或更新方法
 */
$("#saveOrUpdate").click(function (event) {
    event.preventDefault()
    var saveDataAry=[];
    var arrId = []
    //遍历广告内容
    $.each($(':input[type="file"]'),function(i,value){
        var name=$(':input[type="file"]').eq(i).attr('name');
        var data={'id':$(":input[type='hidden'][name='"+name+"']").val(),'type':'indexpage','functionType':'advertisement','name':name,'link':$(':input[name="link"]').eq(i).val(),'priority':i,'isShowOther':$(':input[name="isShowOther"]:checked').val(),'isSelect':'1'};
        saveDataAry.push(data);
        arrId.push($(':input[type="file"]').eq(i).attr('id'));
    });
    //遍历功能模块内容
    $.each($(':input[type="checkbox"]'),function(i,value){
        var  isSelect;
        if($(':input[type="checkbox"]').eq(i).prop("checked")==true){
            isSelect="1"
        }else{
            isSelect="0"
        }
        var name=$(':input[type="checkbox"]').eq(i).attr("name");
        data1={'id':$(":input[type='hidden'][name='"+name+"']").val(),'type':'indexpage','functionType':'module','name':name,'link':'/mobile/'+name,'url':'/upload/mobileIndex/'+name+'.png','priority':i,'isShowOther':$(':input[name="isShowOther"]:checked').val(),'isSelect':isSelect};
        saveDataAry.push(data1);
    });

    var  saveStr=JSON.stringify(saveDataAry).replace(/\"/g,"'");  ;
    $.ajaxFileUpload({
        url:  baseUrl+"/sellerMobileMenu/saveOrUpdate", //地址
        secureuri:false,
        fileElementId: arrId,  //这里不在是以前的id了，要写成数组的形式哦！
        dataType: 'json',
        data: {
            SellerMobileMenus:saveStr
        },
        success: function (data){
            alert("保存成功!");
            freshUrl(baseUrl+"/sellerMobileMenu/get");
        },
        error: function(data){
            alert("保存失败!");

        }
    });

});