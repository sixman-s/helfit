package sixman.helfit.domain.user2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sixman.helfit.domain.calculator.Calculator;
import sixman.helfit.domain.user2.entity.User2;
import sixman.helfit.domain.user2.enums.ActivityLevel;

@RestController
@RequestMapping("/api/v1/users2")
public class UserController2 {

    @PostMapping
    public ResponseEntity<User2> createUser(@RequestParam("age") int age,
                                            @RequestParam("weight") double weight,
                                            @RequestParam("height") double height,
                                            @RequestParam("sex") String sex,
                                            @RequestParam("goal") String goal,
                                            @RequestParam ActivityLevel activityLevel) {
        User2 user2 = new User2(age, weight, height, sex);
        double bmr;
        if(sex.equals("male")){
            bmr = Calculator.calculateBMR_Male(user2);
        }else{
            bmr = Calculator.calculateBMR_Female(user2);
        }
        user2.setBmr(bmr);

        double hb = Calculator.calculateHb(bmr, activityLevel, goal);
        user2.setHb(hb);

        return new ResponseEntity<>(user2, HttpStatus.CREATED);
    }
}
