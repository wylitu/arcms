$(document).ready(function() {
	
	$(document).on('click',"[data-href]",function(){
    	 var link = $(this).attr("data-href");
		 location.href = link;
    })
	
	//tab switcher
	$(".box .tab").click(function(){
		var e = $(this);
		var $t = $(this).attr("rel");
		
			e.parent().children(".tab").removeClass("selected");
			e.addClass("selected");
			e.parent().next(".box-content").children(".sub-content").hide();
			e.parent().next(".box-content").children("."+$t).show();
		
	})
	
	
  $(".message-tips li").on("swiperight",function(){
	  var $_this = $(this)
 if($_this.hasClass('on')){
   $_this.removeClass('on');
   }else{
   $_this.addClass('on');
 }
 });    
 
 
  $(".message-tips li").on("swipeleft",function(){
	   var $_this = $(this)
  if($_this.hasClass('on')){
   $_this.removeClass('on');
   }else{
  $_this.addClass('on');
   }
 });    
	
	
	$(".box_count .plus").click(function(){
		$this = $(this);
		v = parseFloat($this.prev().val());
		$this.prev().val(v+1);
		var totalPrice = parseFloat($('#generateOrder input[name="price"]').val()) * (v + 1)
		$('#generateOrder input[name="quantity"]').val(v+1)
		$('#generateOrder input[name="payment"]').parent().find('span').html(totalPrice)
		$('#generateOrder input[name="payment"]').val(totalPrice)
	})
	
	
	$(".box_count .minus").click(function(){
		$this = $(this);
		v = parseInt($this.next().val());
		console.log(v)
		if(v>1){
			//$this.next().val(v-1);
			var totalPrice = parseFloat($('#generateOrder input[name="price"]').val()) * (v - 1)
			$('#generateOrder input[name="quantity"]').val(v - 1)
			$('#generateOrder input[name="payment"]').parent().find('span').html(totalPrice)
			$('#generateOrder input[name="payment"]').val(totalPrice)
		}
	})

	$(".box_count .duobao_plus").click(function(){
		$this = $(this);
		v = parseFloat($this.prev().val())
		$this.prev().val(v+1);
		var totalPrice = parseFloat($('#generateOrder input[name="price"]').val()) * (v + 1)
		$('#generateOrder input[name="quantity"]').val(v+1)
		$('#generateOrder input[name="payment"]').parent().find('span').html(totalPrice)
		$('#generateOrder input[name="payment"]').val(totalPrice)
		$("#payment").text(v+1);
	})


	$(".box_count .duobao_minus").click(function(){
		$this = $(this);
		v = parseInt($this.next().val());
		//console.log(v)
		if(v>1){
			$this.next().focus();
			$this.next().val(v-1);
			var totalPrice = parseFloat($('#generateOrder input[name="price"]').val()) * (v - 1)
			$('#generateOrder input[name="quantity"]').val(v - 1)
			$('#generateOrder input[name="payment"]').parent().find('span').html(totalPrice)
			$('#generateOrder input[name="payment"]').val(totalPrice)
			$("#payment").text(v-1);
		}
	});



	$(".no_last").each(function(){
		$(this).find("li:last").css({'border':'0'});
	})
	
	$("input[type='password'],input[type='text'],input[type='number'],textarea").focus(function(){
		$(".footer").hide();
		}
	)
	$("input[type='password'],input[type='text'],input[type='number'],textarea").blur(function(){
		$(".footer").fadeIn();
		}
	)

	
$(".select select").on("click",function(){
	var v = $(this).find("option:selected").html();
	$(this).parent().prev().find(".icon-dot").html(v)
	})
	


	$(".inner-cate-nav li").click(function(){
		$(this).addClass("active").siblings().removeClass("active")
		})
		
		
		$(".buycart .item").each(function(){
$(this).find(".item-check .check").click(function(){
	if(!$(this).parent().parent().hasClass('on')){
		$(this).parent().parent().addClass("on");
		}else{
			$(this).parent().parent().removeClass("on");
			}
	})
	})	
	
	
	$(".cart-submit .check").click(function(){
	if(!$(this).parent().hasClass('on')){
		$(this).parent().addClass("on");
		$($(".buycart-list .item")).addClass("on");
		}else{
			$(this).parent().removeClass("on");
		$($(".buycart-list .item")).removeClass("on");
			}
	})
		



$(".item-4 .check-label input").each(function(){
	var _this = $(this).parent();
$(this).on("click",function(){
	console.log('check-label')
	_this.addClass("on").parent().siblings().find(".check-label").removeClass("on");
	$(this).attr("checked", true);
	_this.parent().siblings().find(".check-label input").attr("checked", false);
/*
	if(!_this.hasClass("on")){
		_this.addClass("on");
		_this.find("input[type=check]").attr("checked",true)
		_this.find("input[type=check]").prop("checked",true)
				}
			else {
				_this.removeClass("on");
				_this.find("input[type=check]").removeAttr("checked")
				}*/
}); 
	}) 		


$(".radio-label input").each(function(){
	var _this = $(this).parent();
$(this).on("click",function(){
if(!_this.hasClass("on")){
		_this.addClass("on");
		_this.find("input[type=radio]").attr("checked",true);
		//_this.find("input[type=radio]").prop("checked",true);
				}
			else {
				_this.removeClass("on");
				//_this.find("input[type=radio]").removeAttr("checked");
				}
});
	})
	
$(".switch span").click(function(){
		if(!$(this).parent().hasClass("active")){
			$(this).parent().addClass("active")
			}else{
				$(this).parent().removeClass("active")
				}
		})	



$(".payment-order-choose .radio-label").on("click",function(){
	$(this).addClass("on").parent().siblings().find(".radio-label").removeClass("on");
	})
$(".refund .item-1").on("click", ".check-label input", function(){
	var lable = $(this).parent();
	if(!lable.hasClass("on")){
		lable.addClass("on");
		$(this).attr("checked",true)
		$(this).prop("checked",true)
	}
	else {
		lable.removeClass("on");
		$(this).removeAttr("checked")
	}
	var refundFee = 0;
	var refundCard = $(".refund .item-1 .check-label input:checked");
	refundCard.each(function(){
		refundFee += parseFloat($(this).attr("fee"));
	})

	$("#refundFee").html(refundFee)
	//$(this).addClass("on").parent().siblings().find(".check-label").removeClass("on");
})

$(".shop-byself-bd li").click(function(){
$(this).addClass("on").siblings().removeClass("on");
		})	


$(".card-recharge .item li").click(function(){
$(this).addClass("on").siblings().removeClass("on");
		})	


$(".user-center-length .item-bd li").click(function(){
var val = $(this).find(".t1 i").html();
$(this).addClass("on").siblings().removeClass("on");
$(".user-center-length .item-value i").html(val);
		})	


	function hasdarkbg(){
	if(!$('body').hasClass("darkbg")){
		$('body').addClass("darkbg");
		$('body').append("<i class=darkbg></i>");
		}
		else{
			$('body').removeClass("darkbg");
			$(".darkbg").remove()
			}
	}
	

$(".select-pop-con li").click(function(){
$(this).addClass("on").siblings().removeClass("on");
		})	
		
$(".search-result-hd .select-pop").hide()		
$(".search-result-hd .item-con-2").click(function(){
$(".select-pop").show()

		})		


$(".search-result-hd .table-cell").click(function(){
$(this).addClass("active").siblings().removeClass("active");
		})	

});




jQuery(function(){

//tab
function Tab(args){
	var tabMenu = args.tabMenu;
	var tabCont = args.tabCont;
	var evt = args.evt || 'click'
	tabMenu.eq(0).addClass('on');
	tabCont.eq(0).show().siblings().hide();
	tabMenu[evt](function(){
		var _this = jQuery(this);
		var _index = tabMenu.index(_this);
		_this.addClass('on').siblings().removeClass('on');
		tabCont.eq(_index).show().siblings().hide();
		return true;
	});
}

	
	new Tab({
			tabMenu : jQuery('.myOrder-tab li'),
			tabCont : jQuery('.myOrder-content  .content '),
			evt     : 'click'
	});
	
	
	new Tab({
			tabMenu : jQuery('.myRecommend-tab li'),
			tabCont : jQuery('.myRecommend-content  .content'),
			evt     : 'click'
	});
	
	
	new Tab({
			tabMenu : jQuery('.select-pop-tab li'),
			tabCont : jQuery('.select-pop-con  ul'),
			evt     : 'click'
	});
	
	 });  

