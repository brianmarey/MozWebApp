
var thisHost;

function beginCheck(localhost) {
	thisHost = localhost;
	var domain = encodeURIComponent($('#website').val());
	var url = thisHost + ":8080/MozMetrics/fetchBasicMetrics?domain="+domain;

	$('#entryForm').hide();
	
	$.get(url, processBasicMetrics);
}

function processBasicMetrics(data) {
	//alert(data);
	
	//{"apo":608092.7,"fmrp":3.7812910847062042,"fmrr":5.22927443276084e-10,"pda":20.296778766386748,"ueid":2,"uid":475,"ulc":1454241315,"umrp":5.481724213183218,"umrr":3.3557280892252386e-10,"upa":25.688152456000367,"us":200,"ut":"Blog - Brian M. Carey","uu":"brianmcarey.com/"}

	var json = JSON.parse(data);
	
	$("#results").show();
	
	var siteTitle = json.ut;
	var siteUrl = json.uu;
	var equityLinks = json.ueid;
	var allLinks = json.uid;
	var mozRank10 = json.umrp;
	var mozRankRaw = json.umrr;
	var pageAuthority = json.upa;
	var domainAuthority = json.pda;
	
	$("#siteTitle").html(siteTitle);
	$("#siteUrl").html(siteUrl);
	$("#equityLinks").html(equityLinks);
	$("#allLinks").html(allLinks);
	$("#mozRank10").html(mozRank10);
	$("#mozRankRaw").html(mozRankRaw);
	$("#pageAuthority").html(pageAuthority);
	$("#domainAuthority").html(domainAuthority);
}

function anotherRun() {
	$("#results").hide();
	$('#entryForm').show();
	$('#website').val("");
}