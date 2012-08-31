var CrawlerTest = function(pk, mediaId) {
	this.LOADS_URL = "http://localhost:8080/realtime/collect/session/create";
	this.PLAYS_URL = "http://localhost:8080/realtime/collect/session/update";
	
	this.samba_player_session = "";
	this.pk = pk;
	this.mediaId = mediaId;
	
	if($("#"+this.mediaId).length < 1)
		this.createCountDiv();	
	
}

CrawlerTest.prototype.generateSession = function() {
	 if( this.samba_player_session === "" ) {
         var S4 = function() {
             return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
         };
         this.samba_player_session = (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
     }
	 return this.samba_player_session;
}

CrawlerTest.prototype.generateLoadUrl = function(domainId) {
	var url = this.LOADS_URL + "?s="+this.generateSession()+"&ns=" + this.pk + '&id=' + this.mediaId;
	return url;
}

CrawlerTest.prototype.generatePlayUrl = function(sessionId) {
	var url = this.PLAYS_URL + "?s="+sessionId+"&ns=" + this.pk + '&id=' + this.mediaId;
	return url;
}

CrawlerTest.prototype.makeLoadsRequest = function(domainId, action) {
	var self = this;
	var img = document.createElement("img");
	img.setAttribute("src", this.generateLoadUrl(domainId));
	
	img.onload = function() {
		$("#"+self.mediaId).find("span.loads").text(+$("#"+self.mediaId).find("span.loads").text()+1);
		if(action == "plays") {
			self.makePlaysRequest();
		}
	} 
	
}

CrawlerTest.prototype.makePlaysRequest = function() {
	var self = this;
	var pingPlayImg = new Image(1,1);
	pingPlayImg.src = this.generatePlayUrl(this.samba_player_session) + '&c=' + (new Date).getTime();
	pingPlayImg.onload = function() {
		$("#"+self.mediaId).find("span.plays").text(+$("#"+self.mediaId).find("span.plays").text()+1);
		setTimeout(function(){
			self.makePlaysRequest();
		}, 5000);
	}
}

CrawlerTest.prototype.createCountDiv = function() {
	$("#countDown").append("<ul id="+this.mediaId+" class=\"mediaCount\"><li>Media: "+this.mediaId+"</li><li>Loads: <span class=\"loads\">0</span></li><li>Plays: <span class=\"plays\">0</span></li></ul>");
}


$(document).ready(function() {
	$("#request-form").submit(function(e){
		var requestNumber = $("#requests option:selected").val();
		var reqNum = $("#reqNum").val();
		var action = $('input[name=action]:checked', '#request-form').val();
		var pk =  $("#player-key").val();
		var mediaId =  $("#media-id").val();
		
		var forInterval = setInterval(function() {
			if(requestNumber > 0 && reqNum == "") {
				var crawlerTest = new CrawlerTest(pk, mediaId);
				crawlerTest.makeLoadsRequest(pk, action);
				requestNumber--;
			}else if(reqNum > 0 && requestNumber == "") {
					var crawlerTest = new CrawlerTest(pk, mediaId);
					crawlerTest.makeLoadsRequest(pk, action);
					reqNum--;
				  } else { 
					  clearInterval(forInterval);
				  }
			
		}, 10);
		
		return false;
	});
});