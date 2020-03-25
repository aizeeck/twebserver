package org.aizeeck.t.controller;

import org.aizeeck.t.domain.Device;
import org.aizeeck.t.repos.DeviceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/device")
public class DeviceDetailesController {

    @Autowired
    private DeviceRepo deviceRepo;

    @GetMapping("/hourlyconsumption/{tId}")
    public String getDeviceHourlyConsumption(@PathVariable String tId, Model model) {
        List<Device> devices = deviceRepo.getDeviceHourly(tId);
        model.addAttribute("devicesHourly", devices);
        return "hourlyconsumption";
    }

    @GetMapping("/dailyconsumption/{tId}")
    public String getDeviceDailyConsumption(@PathVariable String tId, Model model) {
        List<Device> devices = deviceRepo.getDeviceDaily(tId);
        model.addAttribute("devicesDaily", devices);
        return "dailyconsumption";
    }

    @GetMapping("/alldevconsumptiondaily")
    public String getDeviceAllDevDailyConsumption(Model model) {
        List<Device> devices = deviceRepo.getAllDeviceDaily();
        String pattern = "yyyy-MM-dd";
        Map<String, Map<String, Integer>> devicesDaily = getNormalizedDevLists(devices, pattern);
        model.addAttribute("devicesDaily", devicesDaily);
        return "alldevconsumptiondaily";
    }

    @GetMapping("/alldevconsumptionmonthly")
    public String getDeviceAllDevMonthlyConsumption(Model model) {
        List<Device> devices = deviceRepo.getAllDeviceMonthly(10);
        String pattern = "yyyy-MM-dd";
        Map<String, Map<String, Integer>> devicesDaily = getNormalizedDevLists(devices, pattern);
        model.addAttribute("devicesMonthly", devicesDaily);
        return "alldevconsumptionmonthly";
    }

    @GetMapping("/alldevconsumptionhourly")
    public String getDeviceAllDevHourlyConsumption(Model model) {
        List<Device> devices = deviceRepo.getAllDeviceHourly();
        String pattern = "yyyy-MM-dd HH-mm";
        Map<String, Map<String, Integer>> devicesHourly = getNormalizedDevLists(devices, pattern);
        model.addAttribute("devicesHourly", devicesHourly);
        return "alldevconsumptionhourly";
    }

    private Map<String, Map<String, Integer>> getNormalizedDevLists(List<Device> deviceList, String pattern) {
        Map<String, Map<String, Integer>> data = new HashMap<>();
        List<List<Device>> lists = new ArrayList<>();

        Set<String> datesSet = new HashSet<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        for (Device d : deviceList) {
            String date = simpleDateFormat.format(d.getObsDate());
            datesSet.add(date);
        }
        List<String> dates = new ArrayList<>(datesSet);
        Collections.sort(dates);

        Set<String> namesSet = new HashSet<>();
        for (Device d : deviceList) {
            namesSet.add(d.getName());
        }
        List<String> names = new ArrayList<>(namesSet);
        Collections.sort(names);

        Comparator<Device> comparator = (o1, o2) -> o1.getObsDate().after(o2.getObsDate()) ? 1 : -1;

        for (String name : names) {
            Map<String, Integer> datesForDev = new HashMap<>();

            List<Device> sublist = deviceList.stream().filter(d -> d.getName().equals(name)).collect(Collectors.toList());

            sublist.sort(comparator);
            for (int i = 1; i < sublist.size(); i++) {
                Device d = sublist.get(i);
                Device d0 = sublist.get(i-1);
                String nameD = d.getName();
                String dateD = simpleDateFormat.format(d.getObsDate());
                if (nameD.equals(name)) {
                    datesForDev.put(dateD, (d.getSum()/d.getType().getValue() - d0.getSum()/d.getType().getValue()));
                }
            }
            data.put(name, datesForDev);
        }
        return data;
    }


}
