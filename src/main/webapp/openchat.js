window.onload = function() {
	$.ajax({
	    type: "POST",
	    url: "loadmsg",
	    dataType: "json",
	    success: function(json) {
	    	if(jQuery.isEmptyObject(json) == false) {
		    	for (var i = 0; i < json.message.length; i++) {
		    		var d = new Date(parseInt(json.message[i].date, 10));
		    		if(json.message[i].to.length == 0) {
			    		$("#panel-msg").append("<div class=\"well well-sm\"><span class=\"badge\" onclick=\"addReceiver(this)\">" + 
			    				json.message[i].from + "</span>[" + d.toLocaleString() + "]:  " + json.message[i].text + "</div>");
		    		} else {
			    		$("#panel-msg").append("<div class=\"well well-sm\"><span class=\"badge\" onclick=\"addReceiver(this)\">" + 
			    				json.message[i].from + "</span><span class=\"badge\" onclick=\"addReceiver(this)\">" + json.message[i].to + 
			    				"</span>[" + d.toLocaleString() + "]:  " + json.message[i].text + "</div>");
		    		}
		    		var elem = document.getElementById("panel-chat");
		    		elem.scrollTop = elem.scrollHeight;
		    		date = d;
		    	}
	    	}
	    }
	});
	var datastr = "state=" + "onload";
	$.ajax({
	    type: "POST",
	    url: "getusers",
	    data: datastr,
	    dataType: "json",
		success: function(json) {
			if(jQuery.isEmptyObject(json) == false) {
				for(var i = 0; i < json.users.length; i++) {
					$("#users-list").append("<div id=\"" + json.users[i].id +
							"\" class=\"list-group-item users-list\" onclick=\"addReceiver(this)\">" + json.users[i].user + "</div>");
				}
				$("#count").text(json.users.length);
			}
		}
	});
	msg_timer = setTimeout(update, 3000);
}

function messageUpdate() {
	var datastr = "last_msg_time=" + date.getTime();
	$.ajax({
	    type: "POST",
	    url: "update",
	    data: datastr,
	    dataType: "json",
	    success: function(json) {
	    	if(jQuery.isEmptyObject(json) == false) {
		    	for (var i = 0; i < json.message.length; i++) {
		    		var d = new Date(parseInt(json.message[i].date, 10));
		    		if(json.message[i].to.length == 0) {
			    		$("#panel-msg").append("<div class=\"well well-sm\"><span class=\"badge\" onclick=\"addReceiver(this)\">" + 
			    				json.message[i].from + "</span>[" + d.toLocaleString() + "]:  " + json.message[i].text + "</div>");
		    		} else {
			    		$("#panel-msg").append("<div class=\"well well-sm\"><span class=\"badge\" onclick=\"addReceiver(this)\">" + 
			    				json.message[i].from + "</span><span class=\"badge\" onclick=\"addReceiver(this)\">" + json.message[i].to + 
			    				"</span>[" + d.toLocaleString() + "]:  " + json.message[i].text + "</div>");
		    		}
		    		var elem = document.getElementById("panel-chat");
		    		elem.scrollTop = elem.scrollHeight;
		    		date = d;
		    	}
	    	}
	    }
	});
}

function usersUpdate() {
	$.ajax({
		type: "POST",
		url: "getusers",
		datatype: "json",
		success: function(json) {
			if(jQuery.isEmptyObject(json) == false) {
				$(".users-list").remove();
				for(var i = 0; i < json.users.length; i++) {
					if(!$("#" + json.users[i].id).length) {
						$("#users-list").append("<div id=\"" + json.users[i].id +
								"\" class=\"list-group-item users-list\ onclick=\"addReceiver(this)\">" + json.users[i].user + "</div>");
					}
				}
				$("#count").text(json.users.length);
			}
		}
	});
}

function outRemoveGlyph() {
	$("#remove-glyph").css("color", "#333");
}

function overRemoveGlyph() {
	$("#remove-glyph").css("color", "#337ab7");
}

function addReceiver(elem) {
	var str = $(elem).text();
	if(str != user) {
		$("#receiver-name").text(str);
		$("#remove-glyph").css("visibility", "visible");
	}
}

function clickRemoveGlyph() {
	$("#receiver-name").text("");
	$("#remove-glyph").css("visibility", "hidden");
}

function outEmoji (elem) {
	$(elem).css("background-color", "white");
}

function overEmoji (elem) {
	$(elem).css("background-color", "#d9edf7");
	$(elem).css("border-radius", "3px");
}

function addEmoji(elem) {
	var emoji = $(elem).clone();
	var space = "&nbsp;";
	emoji.attr('onclick', null);
	emoji.attr('onmouseover', null);
	emoji.attr('onmouseout', null);
	emoji.attr('style', null);
	$("#msgsend").append(space);
	$("#msgsend").append(emoji);
	$("#msgsend").append(space);
	placeCaretAtEnd(document.getElementById("msgsend"));
}

function placeCaretAtEnd(el) {
    el.focus();
    if (typeof window.getSelection != "undefined"
            && typeof document.createRange != "undefined") {
        var range = document.createRange();
        range.selectNodeContents(el);
        range.collapse(false);
        var sel = window.getSelection();
        sel.removeAllRanges();
        sel.addRange(range);
    } else if (typeof document.body.createTextRange != "undefined") {
        var textRange = document.body.createTextRange();
        textRange.moveToElementText(el);
        textRange.collapse(false);
        textRange.select();
    }
}

function update() {
	usersUpdate(); 
	messageUpdate();
	setTimeout(update, 3000);
}

var msg_timer;
var users_timer;
var users_count = "0";
var date = new Date();

function addToChat() {
    var message = $("#msgsend").html().replace(/&nbsp;/gi,'');
    if(!!message) {
	    var to = $("#receiver-name").text();
	    var elem = document.getElementById("panel-chat");
	    date = new Date();
		if(to.length == 0) {
    		$("#panel-msg").append("<div class=\"well well-sm\"><span class=\"badge\" onclick=\"addReceiver(this)\">" + 
    				user + "</span>[" + date.toLocaleString() + "]:  " + message + "</div>");
		} else {
    		$("#panel-msg").append("<div class=\"well well-sm\"><span class=\"badge\" onclick=\"addReceiver(this)\">" + 
    				user + "</span><span class=\"badge\" onclick=\"addReceiver(this)\">" + to + 
    				"</span>[" + date.toLocaleString() + "]:  " + message + "</div>");
		}
		elem.scrollTop = elem.scrollHeight;
		sendToServlet(date);
		$("#msgsend").html("");
		clickRemoveGlyph()
	}
}

function sendToServlet(date) {
	var message = $("#msgsend").html().replace(/&nbsp;/gi,'');
	var datastr = "user=" + user + "&message=" + message + "&receiver=" + $("#receiver-name").text() + "&date=" + date.getTime();
    $.ajax({
        type: "POST",
        url: "send",
        data: datastr,
        success: function() { }	
      });
}