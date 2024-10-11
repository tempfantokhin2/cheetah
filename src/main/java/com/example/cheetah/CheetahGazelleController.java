package com.example.cheetah;

import com.example.cheetah.animals.Cheetah;
import com.example.cheetah.animals.Gazelle;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CheetahGazelleController {

    private static final String CHEETAH_FILE = "cheetah.txt";
    private static final String GAZELLES_FILE = "gazelles.txt";

    @GetMapping("/cheetah")
    public String home(Model model) throws IOException {
        loadCheetahData(model);
        loadGazellesData(model);

        // Get last updated times
        long cheetahUpdateTime = getLastUpdatedTime(CHEETAH_FILE);
        long gazellesUpdateTime = getLastUpdatedTime(GAZELLES_FILE);

        model.addAttribute("lastUpdated", LocalDateTime.now());
        model.addAttribute("cheetahUpdateTime", cheetahUpdateTime);
        model.addAttribute("gazellesUpdateTime", gazellesUpdateTime);

        // Read current cheetah values
        Cheetah currentCheetah = readCheetah();
        model.addAttribute("currentCheetahName", currentCheetah.getName());
        model.addAttribute("currentCheetahSpeed", currentCheetah.getSpeed());
        model.addAttribute("currentCheetahAge", currentCheetah.getAge());

        return "cheetah";
    }

    @PostMapping("/cheetah/hunt")
    public String hunt(Model model) throws IOException {
        Cheetah cheetah = readCheetah();
        List<Gazelle> gazelles = readGazelles();
        int result = cheetah.doHunt(gazelles);

        loadGazellesData(model);
        return "redirect:/cheetah";
    }

    @PostMapping("/cheetah/update-cheetah")
    public String updateCheetah(
            @RequestParam double speed,
            @RequestParam double age,
            @RequestParam String name,
            Model model
    ) throws IOException {
        Cheetah cheetah = readCheetah();
        cheetah.setName(name);
        cheetah.setSpeed(speed);
        cheetah.setAge(age);

        saveCheetah(cheetah);
        setLastUpdatedTime(GAZELLES_FILE, System.currentTimeMillis());

        loadGazellesData(model);
        return "redirect:/cheetah";
    }

    @PostMapping("/cheetah/clear-gazelles")
    public String clearGazelles(Model model) throws IOException {
        List<Gazelle> gazelles = new ArrayList<>();
        saveGazelles(gazelles);

        //setLastUpdatedTime(CHEETAH_FILE, System.currentTimeMillis());
        setLastUpdatedTime(GAZELLES_FILE, System.currentTimeMillis());

        loadGazellesData(model);
        return "redirect:/cheetah";
    }

    @PostMapping("/cheetah/add-gazelle")
    public String addGazelle(@RequestParam String name, @RequestParam double speed, Model model) throws IOException {
        List<Gazelle> gazelles = readGazelles();
        gazelles.add(new Gazelle(name, speed));
        saveGazelles(gazelles);

        //setLastUpdatedTime(CHEETAH_FILE, System.currentTimeMillis());
        setLastUpdatedTime(GAZELLES_FILE, System.currentTimeMillis());

        loadGazellesData(model);
        return "redirect:/cheetah";
    }

    private void loadCheetahData(Model model) throws IOException {
        Cheetah cheetah = readCheetah();
        model.addAttribute("cheetah", cheetah);
    }

    private void saveCheetah(Cheetah cheetah) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CHEETAH_FILE))) {
            writer.write(cheetah.getName() + " " + cheetah.getAge() + " " + cheetah.getSpeed());
        }
    }

    private List<Gazelle> readGazelles() throws IOException {
        List<Gazelle> gazelles = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(GAZELLES_FILE))) {
            String line = reader.readLine();
            if (line != null && !line.equals(" ")) {
                String[] parts = line.split(",");
                for (String part : parts) {
                    String[] gazelleData = part.split(" ");
                    gazelles.add(new Gazelle(gazelleData[0], Double.parseDouble(gazelleData[1])));
                }
            }
        }
        return gazelles;
    }

    private void saveGazelles(List<Gazelle> gazelles) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < gazelles.size(); i++) {
            sb.append(gazelles.get(i).dumpData()).append((i == gazelles.size() - 1) ? "" : ",");
        }
        sb.append(" ");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(GAZELLES_FILE))) {
            writer.write(sb.toString());
        }
    }

    private long getLastUpdatedTime(String fileName) throws IOException {
        File file = new File(fileName);
        return file.lastModified();
    }

    private void setLastUpdatedTime(String fileName, long time) throws IOException {
        File file = new File(fileName);
        file.setLastModified(time);
    }

    private Cheetah readCheetah() throws IOException {
        Cheetah cheetah;
        try (BufferedReader reader = new BufferedReader(new FileReader(CHEETAH_FILE))) {
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(" ");
                cheetah = new Cheetah(parts[0], Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
            } else {
                cheetah = new Cheetah("Unknown", 0.0, 0.0);
            }
        }
        return cheetah;
    }

    private void loadGazellesData(Model model) throws IOException {
        List<Gazelle> gazelles = readGazelles();
        model.addAttribute("gazelles", gazelles);
    }
}
