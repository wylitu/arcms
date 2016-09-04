/**
 * Created by apple on 15/12/7.
 */


getHotGoods();
function getHotGoods() {

    $.ajax({
        type: "post", // 请求方式
        url: baseUrl + "/mobile/index/getGoodsTopHot", //url地址
        data: {lng: 30}, //数据
        dataType: "json",
        success: function (data) {
            if (data.errorCode == 0) {


                $.each(data.info, function (i, value) {

                    $(".home-hot-pro-bd ul").append('<li><a href="'+baseUrl+'/mobile/goods/detail?goodsId='+value.goodsId+'"><div class="table"><div class="table-cell item-photo">' +
                        '<div class="br5 photo"><img src="'+baseUrl+'/media/mobile/images/p1.jpg"></div></div><div class="table-cell item-con">' +
                        '<div class="item-tit"><span class="fl tit">'+value.title+'</span><span class="fr distance">'+value.distance/1000+'</span></div>' +
                        '<div class="item-info">'+value.goodsSummary+'</div><div class="item-price"><strong><em>￥</em>'+value.nowPrice+'</strong><i>￥'+value.price+'</i></div></div></div></a></li>');
                });




            }
        }, error: function () {
            alert("error");
        }
    });



}