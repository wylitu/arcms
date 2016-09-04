var buyerId = jQuery('#userDimension').attr("buyerId")
jQuery('#userDimension').qrcode({width:440,height:440,correctLevel:0,text:baseUrl + "/mobile/seller/offTrade?buyerId=" + buyerId});

jQuery('#cardDimension').qrcode({width:440,height:440,correctLevel:0,text:baseUrl + "/mobile/seller/cardConsume?cardId=" + $("cardDimension").attr("cardId") + "&pass=" + $("cardDimension").attr("pass")});
