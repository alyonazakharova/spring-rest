//package main.web;
//
//import main.entity.User;
//import main.repository.GoodRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.HashMap;
//
//@Controller
//@RequestMapping("/")
//public class MainController {
//
//    private final GoodRepository goodRepository;
//
//    @Autowired
//    public MainController(GoodRepository goodRepository) {
//        this.goodRepository = goodRepository;
//    }
//
//    @GetMapping
//    public String main(Model model, @AuthenticationPrincipal User user) {
//        HashMap<Object, Object> data = new HashMap<>();
//        data.put("profile", user);
//        data.put("goods", goodRepository.findAll());
//        model.addAttribute("frontendData", data);
//
//        return "index";
//    }
//}
