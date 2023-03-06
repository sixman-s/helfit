package sixman.helfit.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sixman.helfit.domain.calculator.Calculator;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.domain.user.enums.ActivityLevel;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @PostMapping
    public ResponseEntity<User> createUser(@RequestParam("age") int age,
                                           @RequestParam("weight") double weight,
                                           @RequestParam("height") double height,
                                           @RequestParam("sex") String sex,
                                           @RequestParam("goal") String goal,
                                           @RequestParam ActivityLevel activityLevel) {
        User user = new User(age, weight, height, sex);
        double bmr;
        if(sex.equals("male")){
            bmr = Calculator.calculateBMR_Male(user);
        }else{
            bmr = Calculator.calculateBMR_Female(user);
        }
        user.setBmr(bmr);

        double hb = Calculator.calculateHb(bmr, activityLevel, goal);
        user.setHb(hb);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
