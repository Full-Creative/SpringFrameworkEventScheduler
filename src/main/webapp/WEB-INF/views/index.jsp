
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<meta http-equiv="content-type"
	content="application/xhtml+xml; charset=UTF-8" />
<title>EventScheduler</title>
<link rel="stylesheet" href="style.css">
</head>

<body>
	<h1>Schedule Event!</h1>
	<nav>
		<a href="index.html">Home</a> 
		<a href="eventCreate.html">Create Event</a>
		<a href="searchByMail.html">Search Event by mail</a>
		<a	href="searchByDate.html">Search Event by date</a>
		<a href="searchByTimeRange.html">Search by time range</a>
	</nav>
	<br>
	<br>
	<div id="loading">Loading events..</div>
	<table id="events"></table>
	<br>
	<br>

	<!-- The Modal --
	<div id="myModal" class="modal">

		<!-- Modal content --
		<div class="modal-content">
			<span class="close">&times;</span>
			<p>Some text in the Modal..</p>
		</div>

	</div>-->
	<div id="updateModal" class="modal">
		<div class="modal-content">
			<span class="close">&times;</span> 
			Event Title: <input type="text"	id="eventTitle"></input> <br><br>
			Event Duration: <select id="duration" name="duration"></select><!--<input type="text" id="eventDuration"></input>-->
			<br><br>
			Event Time: <input type="datetime-local" id="eventTime"></input><br><br>
			<button type="button" id="update">Update</button>
		</div>
	</div>
	<div id="participantModal" class="modal">
		<div class="modal-content">
			<span class="close">&times;</span> 
			 <div id="participantAddition">
			 Participant Name: <input type="text" id="name"></input> <br><br>
			 Participant Time Zone: 
			 <select id="timezone" name="timezone">
			 	<option value="Asia/Kolkata">Asia/Kolkata</option>
  				<option value="Asia/Singapore">Asia/Singapore</option>
 			 </select><br><br>
			 </div>
			 Participant Email: <input type="text" id="email"></input> <br><br>			   
			<button type="button" id="addParticipant">Add</button>
		</div>
	</div>
	<br>
	<br>
	<br>

	<script src="https://MomentJS.com/downloads/moment.js"></script> 
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
	<script src="eventscheduler.js"></script>
</body>
</html>