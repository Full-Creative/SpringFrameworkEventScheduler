
$(document).ready(function() {
	var ajaxCall = ajaxCalls();
	ajaxCall.getEvents();
	initializeClick(ajaxCall);

});


var ajaxCalls = function() {
	var show = print();

	function getEvents() {
		$.ajax({
			type: "GET",
			url: "events",
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
		if (email) {
			$.ajax({
				type: "GET",
				url: "ParticipantEvent?email=" + email,
				contentType: "application/json",
				success: show.printParticipantEvents,
				error: function(jqXHR, textStatus, errorThrown) {
					console.log(jqXHR, textStatus, errorThrown);
				}
			});
		}
		else {
			//document.getElementById("email").style.borderColor = "red";
			alert('Enter email');
		}
	}

	function getEventsByTimeRange(start, end) {
		if (start && end)
			$.ajax({
				type: "GET",
				url: "TimeRange?start=" + start + "&end=" + end,
				contentType: "application/json",
				//	data: JSON.stringify(obj),
				success: show.printParticipantEvents
			});
		else
			alert('Please select the dates');
	}

	function getEventsByDate(date) {
		if (date)
			$.ajax({
				type: "GET",
				url: "EventDate?date=" + moment(date).format('DD/MM/YYYY'),
				contentType: "application/json",
				success: show.printParticipantEvents
			});
		else
			alert('Please select a date');

	}
	function submitEvent() {
		var obj = {
			'eventTitle': $('#title').val(),
			'eventTime': new Date($('#eventtime').val()).getTime(),
			'eventDuration': $('#duration').val() * 60000
		};

		$.ajax({
			type: "POST",
			url: "event",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				alert(response);
				//printEvents(response);
			}

		});
	}

	function deleteEvent(eventID) {
		$.ajax({
			type: "DELETE",
			url: "event?id=" + eventID,
			contentType: "application/json",
			success: function(response) {
				alert(response);
				getEvents();
			}
		});
	}

	function updateEvent(eventID) {

		var obj = {
			'eventID': eventID,
			'eventTitle': $('#eventTitle').val(),
			'eventTime': new Date($('#eventTime').val()).getTime(),
			'eventDuration': $('#duration').val() * 60000
		};
		$.ajax({
			type: "PUT",
			url: "event",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				alert(response);
				getEvents();
				//printEvents(response);
			}

		});

	}

	function addEventParticipant(eventID) {
		var obj = {
			'eventID': eventID,
			'email': $('#email').val(),
			'name': $('#name').val(),
			'timeZone': $('#timezone').val()
		};
		//	var id = $('input[type=radio][name=radioChoose]:checked').attr('id');
		if (obj.email)
			$.ajax({
				type: "POST",
				url: "event/participant",
				contentType: "application/json",
				data: JSON.stringify(obj),
				success: function(response) {
					alert(response);
					show.clearResponse();
					getEvents();
				},
				error: function(xhr, status, error) {
					alert("Participant does not exist. Please Create new participant.");
					var addParticipantDiv = document.getElementById("participantAddition");
					addParticipantDiv.style.display = "block";
				}
			});
		else
			alert('Enter participant Email');
	}

	return {

		addEventParticipant: addEventParticipant,
		updateEvent: updateEvent,
		deleteEvent: deleteEvent,
		submitEvent: submitEvent,
		getEventsByDate: getEventsByDate,
		getEventsByTimeRange: getEventsByTimeRange,
		eventsByMail: eventsByMail,
		getEvents: getEvents
	}
}

var print = function() {
	function printEvents(response) {
		var result = '<tr><td>Event Title</td><td>Event Duration in minutes</td><td>Event Start Time</td><td>Event Created Time</td><td>Email</td><td>Update</td><td>Select</td><td></td></tr>';
		for (var i = 0; i < response.length; i++) {
			result += ('<tr>' +
				'<td style="display:none">' + response[i].eventID + '</td>' +
				'<td>' + response[i].eventTitle + '</td>' +
				'<td>' + response[i].eventDuration / 60000 + '</td>' +
				'<td style="display:none">' + response[i].eventTime + '<!--<br>' + new Date(response[i].eventTime).toUTCString() + '<br>' + new Date(response[i].eventTime).toLocaleString() + '--></td>' +
				'<td>' + new Date(response[i].eventTime).toUTCString() + '<br><br>' + new Date(response[i].eventTime).toLocaleString() + '</td>' +

				'<td>' + new Date(response[i].eventCreatedTime).toUTCString() + '<br><br>' + new Date(response[i].eventCreatedTime).toLocaleString() + '</td>' +
				'<td>' + ((response[i].participantEmail != null) ? response[i].participantEmail : 'no participant') + '</td>' +
				//	'<td>' + ((response[i].participantKey != null) ? response[i].participantKey : 'no participant') + '</td>' +

				//	'<td>' + '<input type="radio" id=' + response[i].eventID + ' name="radioChoose"></input>' + '</td>'
				'<td><button class="updateBtn">Update</button></td>' +
				//'<td>' + '<button type="button" id=' + response[i].eventID + ' class="update">Update</button>' + '</td>' +

				'<td>' + '<button type="button" id=' + response[i].eventID + ' class="delete">Delete</button>' + '</td>' +
				'<td>' + '<button type="button" id=' + response[i].eventID + ' class="participant">Add participant</button>' + '</td>'

				+ '</tr>');
		}

		$('#events').html(result);

	}

	function clearResponse() {
		document.getElementById('email').value = '';
		document.getElementById('name').value = '';
	}

	function printParticipantEvents(response) {
		var result = '<tr><td>Event Title</td><td>Event Duration</td><td>Event Start Time</td><td>Event Created Time</td><td>Participant Email</td></tr>';
		for (var i = 0; i < response.length; i++) {
			result += ('<tr>' +
				'<td>' + response[i].eventTitle + '</td>' +
				'<td>' + response[i].eventDuration / 60000 + 'min</td>' +
				'<td>' + new Date(response[i].eventTime).toUTCString() + '<br><br>' + new Date(response[i].eventTime).toLocaleString() + '</td>' +
				'<td>' + new Date(response[i].eventCreatedTime).toUTCString() + '<br><br>' + new Date(response[i].eventCreatedTime).toLocaleString() + '</td>' +
				'<td>' + ((response[i].participantEmail != null) ? response[i].participantEmail : 'no participant') + '</td>' +
				+ '</tr>');
		}

		$('#participantEvent').html(result);

	}
	return {

		printEvents: printEvents,
		clearResponse: clearResponse,
		printParticipantEvents: printParticipantEvents
	}

}


function initializeClick(ajaxCall) {

	$('#getEventsByMail').click(function() {
		ajaxCall.eventsByMail(document.getElementById("email").value);
		//getEventByMail();
	});
	$('#postEvent').click(function() {
		ajaxCall.submitEvent();
	});

	$('#getEventsByTimeRange').click(function() {

		var start = new Date($('#start').val()).getTime();
		var end = new Date($('#end').val()).getTime();
		ajaxCall.getEventsByTimeRange(start, end);
		//getEventsByTimeRange();
	});
	$('#getEventsByDate').click(function() {
		ajaxCall.getEventsByDate(new Date($('#eventDate').val()).getTime());
	});

	$("table").on("click", ".delete", function() {
		ajaxCall.deleteEvent(this.id);
	});
	$('#duration').click(function() {
		populateDropDown();
	});




	$("table").on("click", ".participant", function() {
		var modal = document.getElementById("participantModal");
		var addParticipant = document.getElementById("participantAddition");

		var span = document.getElementsByClassName("close")[1];

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
			ajaxCall.addEventParticipant(eventID);
		}

	});



	$("table").on("click", ".updateBtn", function() {
		var modal = document.getElementById("updateModal");
		var span = document.getElementsByClassName("close")[0];
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
			ajaxCall.updateEvent(eventID);
		}
		populateDropDown();
		populateForm(this);
		//this.parentElement.parentElement.setAttribute('contenteditable', true);

	});
	function populateDropDown() {
		for (var i = 15; i <= 120; i = i + 15) {
			var option = document.createElement("option");

			option.text = i;
			option.value = i;

			var select = document.getElementById("duration");
			select.appendChild(option);
		}
	}
	function populateForm(e) {
		document.getElementById('eventTitle').value = e.parentElement.parentElement.cells[1].innerText;
		var d = parseInt(e.parentElement.parentElement.cells[3].innerText);
		document.getElementById('eventTime').value = moment(d).format('YYYY-MM-DDTHH:mm'); //yyyy-MM-ddThh:mm
		//document.getElementById('eventDuration').value = e.parentElement.parentElement.cells[2].innerText;
		// $("#duration").val(e.parentElement.parentElement.cells[2].innerText).attr("selected","selected");
		document.getElementById('duration').selectedIndex = e.parentElement.parentElement.cells[2].innerText / 15 - 1;
	};
};




