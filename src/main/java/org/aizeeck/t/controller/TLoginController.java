package org.aizeeck.t.controller;

import org.aizeeck.t.config.Configuration;
import org.aizeeck.t.domain.CookieValidator;
import org.aizeeck.t.domain.Device;
import org.aizeeck.t.domain.DeviceParserFactory;
import org.aizeeck.t.domain.TUserCookie;
import org.aizeeck.t.repos.CookieRepo;
import org.aizeeck.t.repos.DeviceRepo;
import org.aizeeck.t.repos.UserRepo;
import org.aizeeck.t.tservice.SyncDeviceObservationTask;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class TLoginController {

    private static final String LOGIN = Configuration.getInstance().getProperty("LOGIN");

    @Autowired
    private CookieRepo cookieRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DeviceRepo deviceRepo;

    @Autowired
    private TaskScheduler taskScheduler;

    @GetMapping("/tcontrol")
    public String tControl(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, String> dbCookies = getDbCookies(auth.getName());

        if (!CookieValidator.validateDbCookies(dbCookies)) {
            return "tcontrollogin";
        }
        List<Device> devices = new DeviceParserFactory().parse(getDbCookies(auth.getName()));
        model.addAttribute("devices", devices);
        return "tcontrol";
    }

    @PostMapping("/tcontrol")
    public String addTUser(@RequestParam String email, @RequestParam String password, Model model) throws IOException {
        if (!CookieValidator.validateDbCookies(getDbCookies(email))) {
            loginIntoTesy(email, password);
        }

        List<Device> devices = new DeviceParserFactory().parse(getDbCookies(email));
        model.addAttribute("devices", devices);

        return "tcontrol";
    }

    @GetMapping("/tcontrol/sync")
    public String tControlSync(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SyncDeviceObservationTask task = new SyncDeviceObservationTask(getDbCookies(auth.getName()), deviceRepo);
        taskScheduler.schedule(task, new PeriodicTrigger(60, TimeUnit.SECONDS));
        return "tcontrol";
    }

    private void loginIntoTesy(String email, String password) {
        try {
            Connection.Response loginForm = Jsoup.connect(LOGIN)
                    .method(Connection.Method.GET).execute();
            Map<String, String> cookies = loginForm.cookies();
            Connection.Response logedInResponse = Jsoup.connect(LOGIN)
                    .data("token", "")
                    .data("os", "")
                    .data("user", email)
                    .data("pass", password)
                    .cookies(loginForm.cookies())
                    .method(Connection.Method.POST)
                    .execute();
            cookies.putAll(logedInResponse.cookies());

            Document document = logedInResponse.parse();

            if (document.body().text().contains("LogOut")) {
                cookieRepo.updateCookieActivity(userRepo.findByUsername(email).getId());
                saveCookies(email, cookies);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveCookies(String email, Map<String, String> cookies) {
        for (String s : cookies.keySet()) {
            TUserCookie cookie = new TUserCookie();
            cookie.setName(s);
            cookie.setValue(cookies.get(s));
            cookie.setActive(true);
            cookie.setOwner(userRepo.findByUsername(email));
            cookieRepo.save(cookie);
        }
    }

    private Map<String, String> getDbCookies(String email) {
        Map<String, String> cookies = new HashMap<>();
        List<TUserCookie> dbCoocies = cookieRepo.findByOwnerUsernameAndActiveIsTrue(email);
        for (TUserCookie cookie : dbCoocies) {
            cookies.put(cookie.getName(), cookie.getValue());
        }
        return cookies;
    }
}