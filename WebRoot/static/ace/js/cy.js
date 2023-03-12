
	
$(function () {

	$(".row thead").find("th:first").addClass("cy_th");
	$(".row tbody").find("tr").find("td:first").addClass("cy_td");
	$(".row thead").find("th:first").after("<th id='cy_thk'></th>");
	$(".row tbody").find("tr").find("td:first").after("<td id='cy_thk'></td>");
	$('.seat-middle').find(".row:first").hide();
	
	$(".xtyh-middle-r").find(".row:last").addClass("row-zg");
	$(".xtyh-middle-r").find(".row:eq(1)").addClass("row-two");

});