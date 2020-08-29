package sobar.app.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sobar.app.service.Utils;
import sobar.app.service.algorithm.BinarySearch;
import sobar.app.service.algorithm.Graph;
import sobar.app.service.algorithm.QuickSort;
import sobar.app.service.algorithm.ShortestHamiltonianPath;
import sobar.app.service.exception.BusinessNotFoundException;
import sobar.app.service.exception.InvalidParamFormatException;
import sobar.app.service.model.Business;
import sobar.app.service.repository.BusinessRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class BusinessController {

    private final BusinessRepository repository;

    public BusinessController(BusinessRepository repository) {
        this.repository = repository;
    }

    // Single item

    @GetMapping("/nearby")
    public List<Business> getNearby(@RequestParam String origin, @RequestParam Integer radius) {
        String[] tok = origin.split(",");

        if (tok.length != 2) {
            throw new InvalidParamFormatException();
        }

        List<Business> nearby = new ArrayList<>();
        double lat = Double.parseDouble(tok[0]);
        double lon = Double.parseDouble(tok[1]);

        for (Business b : repository.findAll()) {
            if (Utils.dist(lat, lon, b.getLatitude(), b.getLongitude()) <= radius) {
                nearby.add(b);
            }
        }
        
        nearby = QuickSort.sortByStars(nearby, 0, nearby.size() - 1);

        return nearby;
    }

    @GetMapping("/nearby/filter")
    public List<Business> getNearbyWithPrefs(@RequestParam String origin, @RequestParam Integer radius, @RequestParam String pref) {
        String[] originTok = origin.split(",");
        String[] prefTok = pref.split(",");

        if (originTok.length != 2) {
            throw new InvalidParamFormatException();
        }

        List<Business> nearby = new ArrayList<>();
        double lat = Double.parseDouble(originTok[0]);
        double lon = Double.parseDouble(originTok[1]);

        for (Business b : repository.findAll()) {
            if (Utils.dist(lat, lon, b.getLatitude(), b.getLongitude()) <= radius) {
                for (String s : prefTok) {
                    if (b.getCategories().toLowerCase().contains(s.toLowerCase())) {
                        nearby.add(b);
                    }
                }
            }
        }
        
        nearby = QuickSort.sortByStars(nearby, 0, nearby.size() - 1);

        return nearby;
    }

    @GetMapping("/path")
    public List<Long> getPath(@RequestParam String origin, @RequestParam final List<Long> id) {
        String[] tok = origin.split(",");

        if (tok.length != 2) {
            throw new InvalidParamFormatException();
        }

        double lat = Double.parseDouble(tok[0]);
        double lon = Double.parseDouble(tok[1]);

        List<Business> businesses = repository.findAll();
        businesses = QuickSort.sortByID(businesses, 0, businesses.size() - 1);
        
        List<Business> filtered = new ArrayList<>();
        for (Long key : id) {
        	int index = BinarySearch.search(businesses, key, 0, businesses.size() - 1);
        	filtered.add(businesses.get(index));
        }
        
        Business start = filtered.get(0);

        Graph g = new Graph();

        for (Business b : filtered) {
            g.addVertex(b);
        }

        for (Business b : filtered) {
            double currentDist =  Utils.dist(lat, lon, b.getLatitude(), b.getLongitude());
            double minDist = Utils.dist(lat, lon, start.getLatitude(), start.getLongitude());
            if (currentDist < minDist) {
                start = b;
            }

            for (Business bb : filtered) {
                if (!bb.equals(b)) {
                    g.addEdge(b, bb, Utils.dist(b.getLatitude(), b.getLongitude(), bb.getLatitude(), bb.getLongitude()));
                }
            }
        }

        ShortestHamiltonianPath shp = new ShortestHamiltonianPath(g, start);

        List<Long> path = new ArrayList<>();
        for (Business b : shp.findShortest()) {
            path.add(b.getId());
        }

        return path;
    }

    @GetMapping("/businesses/{id}")
    public Business one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessNotFoundException(id));
    }

}
