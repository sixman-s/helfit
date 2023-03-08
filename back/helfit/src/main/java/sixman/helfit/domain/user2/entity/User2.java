package sixman.helfit.domain.user2.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "USERS2")
public class User2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private int age;
    private double weight;
    private double height;
    private String sex;
    private String goal;
    private double bmr; //기초대사량임.
    private double hb;  //기초대사량 x ActiveLevel 한값. 유지칼로리임.
    public User2(int age, double weight, double height, String sex) {
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.sex = sex;
    }


}
