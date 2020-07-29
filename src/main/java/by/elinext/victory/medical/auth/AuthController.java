package by.elinext.victory.medical.auth;

import by.elinext.victory.medical.base.user.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

@RestController
public class AuthController {

    @Inject
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginForm() {
        ModelAndView mav = new ModelAndView("loginPage", "authRequest", new JwtRequest());
        mav.addObject("error", "");
        return mav;
    }

    /*Sign in, publicly accessible*/
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ModelAndView createAuthenticationToken(@ModelAttribute JwtRequest authenticationRequest,
                                                  HttpSession session) throws Exception {

        if (authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword()) != null) {
            Authentication auth = authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

                SecurityContextHolder.getContext().setAuthentication(auth);

                session.setAttribute("username", authenticationRequest.getUsername());

                return new ModelAndView("redirect:/home");

        } else {

            session.removeAttribute("username");

            ModelAndView mav = new ModelAndView("loginPage", "authRequest", new JwtRequest());
            mav.addObject("error", "Username or Password are incorrect.");
            return mav;
        }
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logOut(HttpSession session) {
        session.removeAttribute("username");
        return new ModelAndView("redirect:/login", "authRequest", new JwtRequest());
    }

    private Authentication authenticate(String email, String password) throws Exception {

        Authentication auth;

        try {
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        } catch (DisabledException e) {
            //  throw new Exception("USER_DISABLED", e);
            return null;
        } catch (BadCredentialsException e) {
            System.out.println("invalid cred");
            return null;
            // throw new Exception("INVALID_CREDENTIALS", e);
        }

        return auth;
    }
}
