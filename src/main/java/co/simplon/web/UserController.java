package co.simplon.web;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.simplon.model.User;
import co.simplon.service.business.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserService userService;


    @RequestMapping
    public ModelAndView getList(ModelMap model) {
        List<User> userList = userService.getAll();
        model.addAttribute("userList", userList);
        return new ModelAndView("user", model);
    }

    @RequestMapping(path = "/userById")
    public ModelAndView getById(@RequestParam("id") Integer id, ModelMap model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return new ModelAndView("search-user", model);
    }

    @RequestMapping(path = "/addUser")
    public ModelAndView addUser(@RequestParam("name") String name, @RequestParam("surname") String surname, String password,
                                String email, String role, Integer isEnable) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User(name, surname, email, encoder.encode(password), role, isEnable);
        userService.addOrUpdate(user);
        return new ModelAndView("redirect:/user");
    }

    @RequestMapping(path = "/deleteUser")
    public ModelAndView deleteRoom(@RequestParam("id") Integer id, ModelMap model, RedirectAttributes redirectAttr) {
    	
    	try{
        userService.delete(id);}
    	catch(Exception e){
    		redirectAttr.addFlashAttribute("erreur","Erreur, l'user a certainement déjà effectué une réservation.");	
    	}
        return new ModelAndView("redirect:/user");
    }

    @RequestMapping(path = "/profil")
    public ModelAndView showProfilInfo(ModelMap model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.authenticateUser(name);
        model.addAttribute("currentUser", currentUser);
        return new ModelAndView("profil", model);
    }

    @RequestMapping("/modifyUser")
    public ModelAndView modifyUser(@RequestParam("id") Integer id, ModelMap model) {
        User user=userService.findById(id);
        model.addAttribute("user",user);
        return new ModelAndView("modifyUser",model);
    }

    @RequestMapping("/modifyUserWithInput")
    public ModelAndView modifyUserWithInput(@RequestParam("id") Integer id,@RequestParam("name") String name, @RequestParam("surname") String surname,
                                            String email, String role){

        User user = userService.findById(id);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setRole(role);

        userService.addOrUpdate(user);
        return new ModelAndView("redirect:/user");
    }

    @RequestMapping("/unableUser")
    public ModelAndView unableUser(@RequestParam("id") Integer id){
        User user = userService.findById(id);
        if(userService.unableUser(user.getId())) {
            user.setIsEnable(1);
            userService.addOrUpdate(user);
        }
        return new ModelAndView("redirect:/user");
    }

    @RequestMapping("/enableUser")
    public ModelAndView enableUser(@RequestParam("id") Integer id){
        User user = userService.findById(id);
        if(!userService.unableUser(user.getId())) {
            user.setIsEnable(0);
            userService.addOrUpdate(user);
        }
        return new ModelAndView("redirect:/user");
    }
}
