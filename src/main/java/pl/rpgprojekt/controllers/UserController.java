package pl.rpgprojekt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.rpgprojekt.dao.Fight;
import pl.rpgprojekt.dao.MonsterDao;
import pl.rpgprojekt.dao.UserDao;
import pl.rpgprojekt.entities.Monster;
import pl.rpgprojekt.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Controller
@RequestMapping(value = "/user", produces = "text/html;charset=UTF-8")
public class UserController {




    @Autowired
    private MonsterDao monsterDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private Fight fight;

    @GetMapping()
    public String userPanel (Model user) {
        user.addAttribute("user", userDao.getCurrentUser());
        userDao.recoverHp();
        return "user/userPanel";
    }

    @GetMapping(value = "/showAllMonster")
    public String showAllMonsters (Model monsters) {
        monsters.addAttribute("monsters", monsterDao.getAllMonsters());
        return "user/showAllMonster";
    }

    @GetMapping(value = "/fight/{monsterId}")
    @ResponseBody
    public String fight (@PathVariable int monsterId) {
        User user = userDao.findById(userDao.getCurrentUserId());
        Monster monster = monsterDao.findById(monsterId);
        if (user.getHp() <= 0) {
            return "Masz za mało hp";
        }
        fight.fight(monster, user);
        return "Walka";
    }
}














