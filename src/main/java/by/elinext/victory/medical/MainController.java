package by.elinext.victory.medical;

import by.elinext.victory.medical.base.booking.Booking;
import by.elinext.victory.medical.base.booking.BookingService;
import by.elinext.victory.medical.base.room.Room;
import by.elinext.victory.medical.base.room.RoomService;
import by.elinext.victory.medical.base.room.RoomType;
import by.elinext.victory.medical.base.room.RoomTypeService;
import by.elinext.victory.medical.base.user.PositionService;
import by.elinext.victory.medical.base.user.User;
import by.elinext.victory.medical.base.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @Inject
    private RoomService roomService;

    @Inject
    private UserService userService;

    @Inject
    private BookingService bookingService;

    @Inject
    private PositionService positionService;

    @Inject
    private RoomTypeService roomTypeService;

    @RolesAllowed("USER")
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView getLoginForm(HttpSession session) {
        if (session.getAttribute("username") != null) {
            ModelAndView mav = new ModelAndView("rooms/homePage");
            mav.addObject("freeRooms", roomService.getFreeRooms());
            return mav;
        } else {
            return new ModelAndView("redirect:/login");
        }
    }

    @RolesAllowed("USER")
    @RequestMapping(value = "/room/{id}", method = RequestMethod.GET)
    public ModelAndView getRoomPage(@PathVariable Integer id, HttpSession session) {

        if (session.getAttribute("username") != null) {
            ModelAndView mav = new ModelAndView("rooms/book");
            mav.addObject("booking", new Booking(id));
            return mav;
        } else {
            return new ModelAndView("redirect:/login");
        }
    }

    @RolesAllowed("USER")
    @Transactional
    @RequestMapping(value = "/room/{id}/book", method = RequestMethod.POST)
    public ModelAndView bookRoom(@PathVariable Integer id, HttpSession session, @ModelAttribute Booking booking) {

        if (session.getAttribute("username") != null) {
            booking.setUserID(userService.getByUsername(session.getAttribute("username").toString()).getId());
            booking.setRoomID(id);

            if (bookingService.createAndSave(booking) == null) {
                //warn message
            }

            return new ModelAndView("redirect:/rooms/myBooking");
        } else {
            return new ModelAndView("redirect:/login");
        }
    }

    @RolesAllowed("USER")
    @Transactional
    @RequestMapping(value = "/rooms/myBooking", method = RequestMethod.GET)
    public ModelAndView myRooms(HttpSession session) {

        if (session.getAttribute("username") != null) {
            ModelAndView mav = new ModelAndView("rooms/myBooking");
            mav.addObject("bookings",
                    bookingService.findUsersBookings(userService.getByUsername(session.getAttribute("username").toString()).getId()));
            return mav;
        } else {
            return new ModelAndView("redirect:/login");
        }
    }

    @RolesAllowed("USER")
    @Transactional
    @RequestMapping(value = "/room/{bookId}/free", method = RequestMethod.GET)
    public ModelAndView freeRoom(@PathVariable String bookId, HttpSession session) {

        if (session.getAttribute("username") != null) {

            bookingService.freeRoomNow(bookId);

            ModelAndView mav = new ModelAndView("rooms/myBooking");
            mav.addObject("bookings",
                    bookingService.findUsersBookings(userService.getByUsername(session.getAttribute("username").toString()).getId()));
            return mav;
        } else {
            return new ModelAndView("redirect:/login");
        }
    }

    @RolesAllowed("USER")
    @Transactional
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newPage(HttpSession session) {

        if (session.getAttribute("username") != null) {
            ModelAndView mav = new ModelAndView("rooms/addEntity");
            mav.addObject("roomType", new RoomType());
            mav.addObject("room", new Room());
            mav.addObject("user", new User());
            mav.addObject("positions", positionService.getAllPositions());
            mav.addObject("roomTypes", roomTypeService.getAllTypes());
            return mav;
        } else {
            return new ModelAndView("redirect:/login");
        }
    }

    @RolesAllowed("USER")
    @Transactional
    @RequestMapping(value = "/new/addRoomType", method = RequestMethod.POST)
    public ModelAndView newRoomType(@ModelAttribute RoomType roomType, HttpSession session) {

        if (session.getAttribute("username") != null) {

            roomTypeService.save(roomType);

            return new ModelAndView("redirect:/new");
        } else {
            return new ModelAndView("redirect:/login");
        }
    }

    @RolesAllowed("USER")
    @Transactional
    @RequestMapping(value = "/new/addRoom", method = RequestMethod.POST)
    public ModelAndView newRoom(@ModelAttribute Room room, HttpSession session) {

        if (session.getAttribute("username") != null) {

            roomService.save(room);

            return new ModelAndView("redirect:/new");
        } else {
            return new ModelAndView("redirect:/login");
        }
    }

    @RolesAllowed("USER")
    @Transactional
    @RequestMapping(value = "/new/addUser", method = RequestMethod.POST)
    public ModelAndView newUser(@ModelAttribute User user, HttpSession session) {

        if (session.getAttribute("username") != null) {

            userService.save(user);

            return new ModelAndView("redirect:/new");
        } else {
            return new ModelAndView("redirect:/login");
        }
    }
}
