package pl.gienius.biblioteka.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.gienius.biblioteka.Service.RentalService;

@Controller
public class RentalController {
    private RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/rented")
    public String getAllRentals(Model model) {
        model.addAttribute("rentalList", rentalService.getRentalList());
        return "rented";
    }

    @GetMapping("/activeRentals")
    public String getActiveRentals(Model model) {
        model.addAttribute("activeRental", rentalService.getActiveRentals());
        return "activeRentals";
    }
}
