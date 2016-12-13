$(document).ready(function() {
 	var calendar = $('#calendar').fullCalendar({
 		locale: 'fr',
 		events: '/getCalendarBookings',
 		dayClick: function() {
 	        console.log('a day has been clicked!');
 	    },
 	    eventClick: function(e) {
 	    	console.log("event click ", e);
 	    }
 	});
});