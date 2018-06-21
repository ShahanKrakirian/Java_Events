package com.shahan.events.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shahan.events.models.Event;
import com.shahan.events.models.EventsAttendees;
import com.shahan.events.models.Message;
import com.shahan.events.models.User;
import com.shahan.events.services.EventAttendeeService;
import com.shahan.events.services.EventService;
import com.shahan.events.services.MessageService;
import com.shahan.events.services.UserService;
import com.shahan.events.validator.UserValidator;

@Controller
public class HomeController {
	private final UserValidator userValidator;
	private final UserService userService;
	private final EventService eventService;
	private final EventAttendeeService eventAttendeeService;
	private final MessageService messageService;
	
	public HomeController(UserValidator userValidator, UserService userService, EventService eventService, EventAttendeeService eventAttendeeService, MessageService messageService) {
		this.userValidator = userValidator;
		this.userService = userService;
		this.eventService = eventService;
		this.eventAttendeeService = eventAttendeeService;
		this.messageService = messageService;
	}
	
	@RequestMapping("/")
	public String register(@ModelAttribute("user") User user, Model model) {
		return "/events/loginReg.jsp";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registerProcess(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
		userValidator.validate(user, result);
		if (result.hasErrors()) {			
			return "/events/loginReg.jsp";
		} else {
			userService.registerUser(user);
			Long id = user.getId();
			session.setAttribute("id", id);
			return "redirect:/dashboard";
		}
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginProcess(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, Model model) {
		if (userService.authenticateUser(email, password) == true) {
			 User currUser = userService.findByEmail(email);
			 Long id = currUser.getId();
			 session.setAttribute("id", id);
			 return "redirect:/dashboard";
		 } else {
			 model.addAttribute("error", "Make sure you have the correct email and password!");
			 return "/events/loginReg.jsp";
		 }
	}
	
	@RequestMapping("/dashboard")
	public String dashboard(@ModelAttribute("eventToAdd") Event event, HttpSession session, Model model) {
		
		//Adds current user to view
		Long userId = (Long) session.getAttribute("id");
		User currUser = userService.findUserById(userId);
				
		//Things we need 
		ArrayList<Event> eventsHostedInState = new ArrayList<Event>();
		ArrayList<Event> eventsJoinedInState = new ArrayList<Event>();
		ArrayList<Event> eventsNotJoinedInState = new ArrayList<Event>();
		
		ArrayList<Event> eventsHostedOutOfState = new ArrayList<Event>();
		ArrayList<Event> eventsJoinedOutOfState = new ArrayList<Event>();
		ArrayList<Event> eventsNotJoinedOutOfState = new ArrayList<Event>();
		
		//Queries 
		List<Event> eventsInState = eventService.eventsInState(userService.findUserById((Long) session.getAttribute("id")).getState());
		List<Event> eventsOutOfState = eventService.eventsOutOfState(userService.findUserById((Long) session.getAttribute("id")).getState());
		
		for (Event e : eventsInState) {
			if (e.getHost() == currUser) {
				eventsHostedInState.add(e);
			}
		}
		for (Event e : eventsOutOfState) {
			if (e.getHost() == currUser) {
				eventsHostedOutOfState.add(e);
			}
		}
		for (Event e : eventsInState) {
			if (e.getAttendees().contains(currUser) && e.getHost() != currUser) {
				eventsJoinedInState.add(e);
			}
		}
		for (Event e : eventsInState) {
			if (!e.getAttendees().contains(currUser)) {
				eventsNotJoinedInState.add(e);
			}
		}
		for (Event e : eventsOutOfState) {
			if (e.getAttendees().contains(currUser) && e.getHost() != currUser) {
				eventsJoinedOutOfState.add(e);
			}
		}
		for (Event e : eventsOutOfState) {
			if (!e.getAttendees().contains(currUser)) {
				eventsNotJoinedOutOfState.add(e);
			}
		}
		System.out.println(eventsJoinedOutOfState);
		System.out.println(eventsNotJoinedOutOfState);
				
		model.addAttribute("eventsHostedInState", eventsHostedInState);
		model.addAttribute("eventsHostedOutOfState", eventsHostedOutOfState);
		model.addAttribute("eventsJoinedInState", eventsJoinedInState);
		model.addAttribute("eventsNotJoinedInState", eventsNotJoinedInState);
		model.addAttribute("eventsJoinedOutOfState", eventsJoinedOutOfState);
		model.addAttribute("eventsNotJoinedOutOfState", eventsNotJoinedOutOfState);
		model.addAttribute("currUser", currUser);

		
		return "/events/dashboard.jsp";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(value="/events/new", method=RequestMethod.POST)
	public String newEvent(@Valid @ModelAttribute("eventToAdd") Event event, BindingResult result, @RequestParam("day") String date, HttpSession session, Model model) {
		System.out.println(date);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date dateToAdd;
		try {
			
			//Create event
			dateToAdd = df.parse(date);
			
			Date dateToday = new Date();
			if (dateToday.compareTo(dateToAdd) > 0) {
				model.addAttribute("error", "Date must be in the future.");
				return "/events/dashboard.jsp";
			}
			
		    event.setDate(dateToAdd);
		    System.out.println(dateToAdd);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (result.hasErrors()) {
			System.out.println("WTF");
			return "/events/dashboard.jsp";
		}
		else {
			
			User currUser = userService.findUserById((Long) session.getAttribute("id"));
			event.setHost(currUser);
			eventService.createEvent(event);

		    EventsAttendees eventAttendee = new EventsAttendees();
		    eventAttendee.setAttendee(currUser);
		    eventAttendee.setEvent(event);
		    eventAttendeeService.createRelationship(eventAttendee);
		    
			return "redirect:/dashboard";
		}
	}
	
	@RequestMapping("/events/{id}")
	public String eventDetail(@ModelAttribute("messageToAdd") Message message, @PathVariable("id") Long id, Model model, HttpSession session) {
		
		//Get current event 
		Event currEvent = eventService.findEventById(id);
		
		
		
		List<User> usersAttending = currEvent.getAttendees();
		
		
		model.addAttribute("currEvent", currEvent);
		model.addAttribute("usersAttending", usersAttending);
		model.addAttribute("currUser", userService.findUserById((Long) session.getAttribute("id")));
		
		return "/events/eventDetail.jsp";
	}
	
	@RequestMapping(value="/message/new/{id}", method=RequestMethod.POST)
	public String messageProcess(@Valid @ModelAttribute("messageToAdd") Message message, BindingResult result, @PathVariable("id") Long id, HttpSession session) {
		
		//Get current event
		Message newMessage = new Message();
		newMessage.setContent(message.getContent());
		Event currEvent = eventService.findEventById(id);
		newMessage.setUser(userService.findUserById((Long) session.getAttribute("id")));
		newMessage.setEvent(currEvent);
		
		List<Message> currMessages = currEvent.getMessages();
		currMessages.add(newMessage);
		currEvent.setMessages(currMessages);
		
		messageService.createMessage(newMessage);
		eventService.createEvent(currEvent);
		
		return "redirect:/events/" + id;
	}
	
	@RequestMapping("/events/{id}/edit")
	public String edit(@ModelAttribute("event") Event event, @PathVariable("id") Long id, Model model) {
		Event currEvent = eventService.findEventById(id);
		model.addAttribute("currEvent", currEvent);
		return "/events/editEvent.jsp";
	}
	
	@RequestMapping(value="/events/{id}/edit", method=RequestMethod.PUT)
	public String update(@Valid @ModelAttribute("event") Event event, BindingResult result, @RequestParam("day") String date, @PathVariable("id") Long id, Model model) {
        if (result.hasErrors()) {
            return "/events/editEvent.jsp";
        } else {
        	
        	DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    		Date dateToAdd;
    		try {
    			
    			//Create event
    			dateToAdd = df.parse(date);
    			
    			Date dateToday = new Date();
    			if (dateToday.compareTo(dateToAdd) > 0) {
    				model.addAttribute("error", "Date must be in the future.");
    				return "/events/dashboard.jsp";
    			}
    			
    		    event.setDate(dateToAdd);
    			
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
        	
        	//Get form info
        	String newName = event.getName();
        	Date newDate = event.getDate();
        	String newLocation = event.getLocation();
        	String newState = event.getState();
        	
        	Event currEvent = eventService.findEventById(id);
        	currEvent.setName(newName);
        	currEvent.setDate(newDate);
        	currEvent.setLocation(newLocation);
        	currEvent.setState(newState);
        	
            eventService.createEvent(currEvent);
            
            return "redirect:/dashboard";
        }
    }
	
	@RequestMapping(value="/events/{id}/cancel", method=RequestMethod.DELETE)
	public String cancel(@PathVariable("id") Long id, HttpSession session) {
		
		eventService.deleteRelationship(userService.findUserById((Long) session.getAttribute("id")), eventService.findEventById(id));
		
		return "redirect:/dashboard";
	}
	
	@RequestMapping(value="/events/{id}/delete", method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id, HttpSession session) {
		
		eventService.deleteById(id);
		
		return "redirect:/dashboard";
	}
	
	@RequestMapping(value="events/{id}/join", method=RequestMethod.PUT)
	public String joinEvent(@PathVariable("id") Long id, HttpSession session) {
		
		//Get event and current user
		User currUser = userService.findUserById( (Long) session.getAttribute("id"));
		Event currEvent = eventService.findEventById(id);
		
		currUser.addEventAttending(currEvent);
		currEvent.addAttendee(currUser);
		userService.saveUser(currUser);
		eventService.createEvent(currEvent);
		
		return "redirect:/dashboard";
	}

}
