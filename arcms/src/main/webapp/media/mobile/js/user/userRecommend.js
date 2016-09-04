
showUserRecommendFromUser();
showUserRecommendFromSeller();
function showUserRecommendFromUser() {



	$.ajax({
		type: "post", // 请求方式
		url: baseUrl + "/mobile/userRecommend?token=", //url地址
		data: {type: 'USER'},
		dataType: "json",
		success: function (data) {

			if (data.errorCode == 0) {

				$.each(data.info, function (i, value) {

					$("#recommendFromUserItem").append('<div class="table"><div class="table-cell t1">' + value.name + '</div><div class="table-cell t2">' + getFormatTime(value.saleTime) + '</div><div class="table-cell t3">' + value.payment + '</div><div class="table-cell t3">' + value.targerIcard+ '</div></div>');
				});


			}
		}, error: function () {
			alert("error");
		}
	});
}

	function showUserRecommendFromSeller() {

		$.ajax({
			type: "post", // 请求方式
			url: baseUrl + "/mobile/userRecommend", //url地址
			data: {type: 'SELLER'},
			dataType: "json",
			success: function (data) {

				if (data.errorCode == 0) {

					$.each(data.info, function (i, value) {

						$("#recommendFromSellerItem").append('<div class="table"><div class="table-cell t1">'+value.name+'</div><div class="table-cell t2">'+getFormatTime(value.saleTime)+'</div><div class="table-cell t3">'+value.payment+'</div><div class="table-cell t3">'+value.targerIcard+'</div></div>');
					});


				}
			}, error: function () {
				alert("error");
			}
		});




}




