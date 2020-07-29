package by.elinext.victory.medical.demo.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Inject
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginForm() {
        ModelAndView mav = new ModelAndView("authorisation/login", "authRequest", new JwtRequest());
        mav.addObject("error", "");
        return mav;
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logOut(HttpSession session) {
        session.removeAttribute("email");
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
