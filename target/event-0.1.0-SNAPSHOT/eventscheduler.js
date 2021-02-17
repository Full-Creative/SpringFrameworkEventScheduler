
$(document).ready(function() {
	var ajaxCall = ajaxCalls();
	ajaxCall.getEvents();
	initializeClick(ajaxCall);
	ajaxCall.setUser();


});


var ajaxCalls = function() {
	var show = print();

	function exportEvents() {
		$.ajax({
			type: "GET",
			url: "taskqueues/enqueue?key=1",
			success: alert(response),
			error: function(jqXHR, textStatus, errorThrown) {
				console.log(textStatus, errorThrown);
			}

		});
	}
	function getEvents() {
		$.ajax({
			type: "GET",
			url: "event",
			contentType: "application/json",
			beforeSend: function() {
				$("#loading").show();
			},
			complete: function() {
				$("#loading").hide();
			},
			success: show.printEvents,
			error: function(jqXHR, textStatus, errorThrown) {
				console.log(textStatus, errorThrown);
			}

		});
	}

	function eventsByMail(email) {

		$.ajax({
			type: "GET",
			url: "participant/event?email=" + email,
			contentType: "application/json",
			success: show.printEvents,
			error: function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR, textStatus, errorThrown);
			}
		});

	}

	function getEventsByTimeRange(start, end) {
		$.ajax({
			type: "GET",
			url: "event/timerange?start=" + start + "&end=" + end,
			contentType: "application/json",
			//	data: JSON.stringify(obj),
			success: show.printEvents
		});
	}

	function getEventsByDate(date) {
		$.ajax({
			type: "GET",
			url: "event/date?date=" + moment(date).format('DD/MM/YYYY'),
			contentType: "application/json",
			success: show.printEvents
		});
	}
	function submitEvent(event) {

		$.ajax({
			type: "POST",
			url: "event",
			contentType: "application/json",
			data: JSON.stringify(event),
			success: function(response) {
				//	getEvents();
				show.clearResponse();
				show.append(response);
				//printEvents(response);
			}

		});

	}

	function deleteEvent(eventID) {
		$.ajax({
			type: "DELETE",
			url: "event/" + eventID,
			//url: "event?id=" + eventID,
			contentType: "application/json",
			success: function(response) {
				alert(response);
				getEvents();
			}
		});
	}

	function updateEvent(event) {
		$.ajax({
			type: "PUT",
			url: "event",
			contentType: "application/json",
			data: JSON.stringify(event),
			success: function(response) {
				//alert(response);
				show.update(response);
				//getEvents();
				//printEvents(response);
			}

		});

	}

	function addEventParticipant(participant) {

		//	var id = $('input[type=radio][name=radioChoose]:checked').attr('id');
		$.ajax({
			type: "POST",
			url: "participant/event",
			contentType: "application/json",
			data: JSON.stringify(participant),
			success: function(response) {
				alert(response);
				getEvents();
				show.clearResponse();
			},
			error: function(xhr, status, error) {
				alert("Participant does not exist. Please Create new participant.");
				var addParticipantDiv = document.getElementById("participantAddition");
				addParticipantDiv.style.display = "block";
			}
		});
	}

	function postUser(user) {
		$.ajax({
			type: "POST",
			url: "user",
			contentType: "application/json",
			data: JSON.stringify(user),
			success: function() {
				window.location.href = "/index.html";
			},
			error: function(xhr, status, error) {
				alert("User name not saved");
			}


		});

	}


	function setUser() {

		$.ajax({
			type: "GET",
			url: "user",
			success: function(response) {

				$('#user').html('Welcome ' + response);
			},
			error: function(xhr, status, error) {
				alert("User name unavailable");
			}

		});

	}
	function exit() {
		$.ajax({
			type: "GET",
			url: "user/exit",
			success: function(response) {
				window.location.href = "/landingPage.html";
				$('#user').html('Exited');
			},
			error: function(xhr, status, error) {
				alert("User name unavailable");
			}

		});
	}
	return {
		exportEvents: exportEvents,
		addEventParticipant: addEventParticipant,
		updateEvent: updateEvent,
		deleteEvent: deleteEvent,
		submitEvent: submitEvent,
		getEventsByDate: getEventsByDate,
		getEventsByTimeRange: getEventsByTimeRange,
		eventsByMail: eventsByMail,
		getEvents: getEvents,
		postUser: postUser,
		setUser: setUser,
		exit: exit
	}
}

var print = function() {
	function update(response) {
		for (var i = 0; i < 2; i++) {

			var column = document.getElementsByClassName(response.eventID)[i].children

			column[3].innerHTML = new Date(response.eventTime).toUTCString()
			column[2].innerHTML = response.eventDuration / 60000;
			column[1].innerHTML = response.eventTitle;
		}
		alert('Modified');
	}
	function printEvents(response) {
		var result = '';
		if (response.length == 0) {
			result = 'No events found';
		}
		else {
			result = '<tr><th>Event Title</th><th>Event Duration in minutes</th><th>Event Start Time</th><th>Event Created Time</th><th>Email</th><th colspan="3">Options</th></tr>';
			for (var i = 0; i < response.length; i++) {
				result += ('<tr class="' + response[i].eventID + '">' +
					'<td style="display:none">' + response[i].eventID + '</td>' +
					'<td>' + response[i].eventTitle + '</td>' +
					'<td>' + response[i].eventDuration / 60000 + '</td>' +
					'<td>' + new Date(response[i].eventTime).toUTCString() + '<br><br>' /*+ new Date(response[i].eventTime).toLocaleString()*/ + '</td>' +
					'<td style ="display:none">' + response[i].eventTime + '</td >' +
					'<td>' + new Date(response[i].eventCreatedTime).toUTCString() + '<br><br>' /*+ new Date(response[i].eventCreatedTime).toLocaleString() */ + '</td>' +
					'<td>' + ((response[i].participantMail != null) ? response[i].participantMail : 'no participant') + '</td>' +

					'<td><button  id=' + response[i].eventID + ' class="updateBtn">Update</button></td>' +
					'<td>' + '<button type="button" id=' + response[i].eventID + ' class="delete">Delete</button>' + '</td>' +
					'<td>' + '<button type="button" id=' + response[i].eventID + ' class="participant">Add participant</button>' + '</td>'

					+ '</tr>');
			}
		}

		$('.events').html(result);

	}

	function clearResponse() {

		document.getElementById('participantmail').value = '';
		document.getElementById('email').value = '';
		document.getElementById('name').value = '';
		document.getElementById('title').value = '';
		document.getElementById('time').value = '';
		document.getElementById('duration').value = '';
	}

	function append(response) {
		var result = ('<tr class="' + response[i].eventID + '">' +
			'<td style="display:none">' + response.eventID + '</td>' +
			'<td>' + response.eventTitle + '</td>' +
			'<td>' + response.eventDuration / 60000 + '</td>' +
			'<td>' + new Date(response.eventTime).toUTCString() + '<br><br>' /*+ new Date(response[i].eventTime).toLocaleString()*/ + '</td>' +
			'<td style ="display:none">' + response.eventTime + '</td >' +
			'<td>' + new Date(response.eventCreatedTime).toUTCString() + '<br><br>' /*+ new Date(response[i].eventCreatedTime).toLocaleString() */ + '</td>' +
			'<td>' + ((response.participantMail != null) ? response.participantMail : 'no participant') + '</td>' +

			'<td><button  id=' + response.eventID + ' class="updateBtn">Update</button></td>' +
			'<td>' + '<button type="button" id=' + response.eventID + ' class="delete">Delete</button>' + '</td>' +
			'<td>' + '<button type="button" id=' + response.eventID + ' class="participant">Add participant</button>' + '</td>'

			+ '</tr>');
		$(".events").append(result);
		alert('Created new event!');

	}




	return {

		printEvents: printEvents,
		clearResponse: clearResponse,
		append: append,
		update: update
	}

}


function initializeClick(ajaxCall) {
	$('#export').click(function(){
		ajaxCall.exportEvents();
	});
	$('#getEventsByMail').click(function() {
		var email = document.getElementById("email").value;
		if (email)
			ajaxCall.eventsByMail(email);
		else {
			//document.getElementById("email").style.borderColor = "red";
			alert('Enter email');
		}
	});
	$('#postEvent').click(function() {
		var event = {
			'eventTitle': $('#title').val(),
			'eventTime': new Date($('#time').val()).getTime(),
			'eventDuration': $('#duration').val() * 60000
		};
		if (event.eventDuration != 0 && !isNaN(event.eventTime)) {

			ajaxCall.submitEvent(event);
		}
		else
			alert("Enter details");
	});

	$('#getEventsByTimeRange').click(function() {

		var start = new Date($('#start').val()).getTime();
		var end = new Date($('#end').val()).getTime();
		if (start > end)
			alert('Start date greater than end date');
		else if (start && end)
			ajaxCall.getEventsByTimeRange(start, end);
		else
			alert('Select the date');
		//getEventsByTimeRange();
	});
	$('#getEventsByDate').click(function() {
		var date = new Date($('#eventDate').val()).getTime();
		if (date)
			ajaxCall.getEventsByDate(date);
		else
			alert('Please select a date');

	});
	$('#exit').click(function() {
		ajaxCall.exit();
	});

	$("table").on("click", ".delete", function() {
		ajaxCall.deleteEvent(this.id);
	});

	$('#userName').click(function() {
		var user = {
			'name': $('#name').val()
		}
		if (user.name)
			ajaxCall.postUser(user);
		else
			alert('Enter your name to continue');
	});
	$('a[href="#searchByTime"]').click(function() {
		ajaxCall.getEvents();
	});
	$('a[href="#searchByDate"]').click(function() {
		ajaxCall.getEvents();
	});
	$('a[href="#home"]').click(function() {
		ajaxCall.getEvents();
	});
	$('#newEvent').click(function() {
		var modal = document.getElementById("createModal");
		var span = document.getElementsByClassName("close")[0];
		//	var updateBtn = document.getElementById("update");

		modal.style.display = "block";

		// When the user clicks on <span> (x), close the modal
		span.onclick = function() {
			modal.style.display = "none";
		}
		// When the user clicks anywhere outside of the modal, close it
		window.onclick = function(event) {
			if (event.target == modal) {
				modal.style.display = "none";
			}
		}
		populateDropDown('duration');
	});
	$("table").on("click", ".participant", function() {
		var modal = document.getElementById("participantModal");
		var addParticipant = document.getElementById("participantAddition");

		var span = document.getElementsByClassName("close")[2];

		var addBtn = document.getElementById("addParticipant");
		var eventID = this.id;

		addParticipant.style.display = "none";
		modal.style.display = "block";
		// When the user clicks on <span> (x), close the modal
		span.onclick = function() {
			modal.style.display = "none";
		}
		// When the user clicks anywhere outside of the modal, close it
		window.onclick = function(event) {
			if (event.target == modal) {
				modal.style.display = "none";
			}
		}

		addBtn.onclick = function() {
			var participant = {
				'eventID': eventID,
				'email': $('#participantmail').val(),
				'name': $('#name').val(),
				'timeZone': $('#timezone').val()
			};
			if (participant.email)
				ajaxCall.addEventParticipant(participant);
			else
				alert('Enter participant Email');

		}

	});


	$("table").on("click", ".updateBtn", function() {
		var modal = document.getElementById("updateModal");
		var span = document.getElementsByClassName("close")[1];
		var updateBtn = document.getElementById("update");
		var eventID = this.parentElement.parentElement.cells[0].innerHTML;
		modal.style.display = "block";
		// When the user clicks on <span> (x), close the modal
		span.onclick = function() {
			modal.style.display = "none";
		}
		// When the user clicks anywhere outside of the modal, close it
		window.onclick = function(event) {
			if (event.target == modal) {
				modal.style.display = "none";
			}
		}
		updateBtn.onclick = function() {

			var event = {
				'eventID': eventID,
				'eventTitle': $('#eventTitle').val(),
				'eventTime': new Date($('#eventTime').val()).getTime(),
				'eventDuration': $('#eventduration').val() * 60000
			};
			ajaxCall.updateEvent(event);
		}
		populateDropDown('eventduration');
		populateForm(this);
		//this.parentElement.parentElement.setAttribute('contenteditable', true);

	});
	function populateDropDown(id) {
		for (var i = 15; i <= 120; i = i + 15) {
			var option = document.createElement("option");

			option.text = i;
			option.value = i;

			var select = document.getElementById(id);
			select.appendChild(option);
		}
	}
	function populateForm(e) {
		document.getElementById('eventTitle').value = e.parentElement.parentElement.cells[1].innerText;
		var d = parseInt(e.parentElement.parentElement.cells[4].innerText);
		document.getElementById('eventTime').value = moment(d).format('YYYY-MM-DDTHH:mm'); //yyyy-MM-ddThh:mm
		//document.getElementById('eventDuration').value = e.parentElement.parentElement.cells[2].innerText;
		// $("#duration").val(e.parentElement.parentElement.cells[2].innerText).attr("selected","selected");
		document.getElementById('eventduration').selectedIndex = e.parentElement.parentElement.cells[2].innerText / 15 - 1;
	};
};




