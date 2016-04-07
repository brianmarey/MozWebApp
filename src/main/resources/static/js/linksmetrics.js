
var thisHost;

function beginCheck(localhost) {
	thisHost = localhost;
	var domain = encodeURIComponent($('#website').val());
	var url = thisHost + ":8080/MozMetrics/fetchLinksMetrics?domain="+domain;

	$('#entryForm').hide();
	
	$.get(url, processLinksMetrics);
}

function processLinksMetrics(data) {
	//alert(data);
	
	//{"apo":608092.7,"fmrp":3.7812910847062042,"fmrr":5.22927443276084e-10,"pda":20.296778766386748,"ueid":2,"uid":475,"ulc":1454241315,"umrp":5.481724213183218,"umrr":3.3557280892252386e-10,"upa":25.688152456000367,"us":200,"ut":"Blog - Brian M. Carey","uu":"brianmcarey.com/"}

	var json = JSON.parse(data);
	
	alert(json.length);
	
}

function anotherRun() {
	$("#results").hide();
	$('#entryForm').show();
	$('#website').val("");
}