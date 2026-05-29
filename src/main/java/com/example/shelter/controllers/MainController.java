package com.example.shelter.controllers;

import com.example.shelter.entities.*;
import com.example.shelter.repositories.AnimalRepository;
import com.example.shelter.repositories.UserRepository;
import com.example.shelter.services.AnimalService;
import com.example.shelter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;


@Controller
public class MainController {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnimalRepository animalRepository;

    // Главная страница
    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listAnimals", animalService.getAllAvailableAnimals());
        return "index";
    }

    // Страница входа
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Страница регистрации
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Обработка данных с формы регистрации
    @PostMapping("/register")
    public String registerUser(User user) {
        userService.registerUser(user);
        return "redirect:/login?success";
    }

    @GetMapping("/catalog")
    public String showCatalog(@RequestParam(required = false) String species,
                              @RequestParam(required = false) String gender,
                              Model model) {
        model.addAttribute("listAnimals", animalService.getFilteredAnimals(species, gender));
        return "catalog";
    }

    // Открыть страницу с формой
    @GetMapping("/admin/add-animal")
    public String showAddAnimalForm(Model model) {
        model.addAttribute("animal", new Animal());
        return "add-animal";
    }

    // Сохранить данные из формы
    @PostMapping("/admin/add-animal")
    public String saveAnimal(Animal animal, @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        // 1. Сохраняем файл через сервис
        String imagePath = animalService.saveImage(imageFile);

        // 2. Записываем путь к файлу в объект животного
        if (imagePath != null) {
            animal.setImageUrl(imagePath);
        }

        // 3. Сохраняем всё в базу
        animalService.saveAnimal(animal);
        return "redirect:/catalog";
    }

    @PostMapping("/adopt/{id}")
    public String submitRequest(@PathVariable Long id, @RequestParam String message, Principal principal) {
        // 1. Узнаем email текущего вошедшего пользователя
        String email = principal.getName();

        // 2. Ищем пользователя и животное в базе
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Животное не найдено"));

        // 3. Создаем заявку через сервис
        animalService.createRequest(user, animal, message);

        return "redirect:/catalog?success_request";
    }

    // Личный кабинет пользователя
    @GetMapping("/profile")
    public String showUserProfile(Model model, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email).get();
        model.addAttribute("myRequests", animalService.getUserRequests(user));
        return "profile";
    }

    @ModelAttribute("userName")
    public String getUserName(Principal principal) {
        if (principal == null) return null;
        // Ищем пользователя в базе по email (который лежит в principal.getName())
        return userRepository.findByEmail(principal.getName())
                .map(User::getFirstName)
                .orElse("Гость");
    }

    // Просмотр всех заявок для админа
    @GetMapping("/admin/requests")
    public String showAdminRequests(Model model) {
        model.addAttribute("allRequests", animalService.getAllRequests());
        return "admin-requests";
    }

    @PostMapping("/admin/requests/approve/{id}")
    public String approveRequest(@PathVariable Long id) {
        animalService.approveRequest(id);
        return "redirect:/admin/requests";
    }

    @PostMapping("/admin/requests/reject/{id}")
    public String rejectRequest(@PathVariable Long id) {
        animalService.rejectRequest(id);
        return "redirect:/admin/requests";
    }

    // Открыть форму редактирования
    @GetMapping("/admin/edit-animal/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("animal", animalService.getAnimalById(id));
        return "edit-animal"; // Создадим этот файл на основе add-animal
    }

    // Сохранить изменения
    @PostMapping("/admin/edit-animal/{id}")
    public String updateAnimal(@PathVariable Long id,
                               @ModelAttribute("animal") Animal animal,
                               @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        // Получаем текущее состояние из базы, чтобы не потерять данные, которых нет в форме
        Animal existingAnimal = animalService.getAnimalById(id);

        existingAnimal.setName(animal.getName());
        existingAnimal.setSpecies(animal.getSpecies());
        existingAnimal.setGender(animal.getGender());
        existingAnimal.setAge(animal.getAge());
        existingAnimal.setWeight(animal.getWeight());
        existingAnimal.setDescription(animal.getDescription());

        // Если загружено новое фото — обновляем путь
        if (!imageFile.isEmpty()) {
            String imagePath = animalService.saveImage(imageFile);
            existingAnimal.setImageUrl(imagePath);
        }

        animalService.saveAnimal(existingAnimal);
        return "redirect:/catalog";
    }

    @PostMapping("/help/volunteer")
    public String submitVolunteer(VolunteerRequest request, Principal principal) {
        if (principal == null) return "redirect:/login"; // Если гость нажал "отправить"

        User user = userRepository.findByEmail(principal.getName()).get();
        animalService.saveVolunteerRequest(request, user);
        return "redirect:/help?success";
    }

    @GetMapping("/admin/volunteers")
    public String showVolunteerRequests(Model model) {
        model.addAttribute("volunteers", animalService.getAllVolunteerRequests());
        return "admin-volunteers";
    }

    @PostMapping("/help/donate")
    public String processDonation(@RequestParam Double amount,
                                  @RequestParam(required = false) String comment,
                                  Principal principal) {
        User user = null;
        if (principal != null) {
            user = userRepository.findByEmail(principal.getName()).get();
        }
        animalService.saveDonation(amount, comment, user);
        return "redirect:/help?donate_success";
    }

    @GetMapping("/admin/donations")
    public String showDonations(Model model) {
        model.addAttribute("donations", animalService.getAllDonations());
        // Также можно передать общую сумму для статистики
        double total = animalService.getAllDonations().stream().mapToDouble(Donation::getAmount).sum();
        model.addAttribute("totalAmount", total);
        return "admin-donations";
    }

    @GetMapping("/help")
    public String showHelpPage(Model model) {
        // 1. Добавляем данные для шкал товаров (то, что мы только что сделали)
        model.addAttribute("supplies", animalService.getAllSupplies());

        // 2. Добавляем пустой объект для формы волонтера
        model.addAttribute("volunteerRequest", new VolunteerRequest());

        return "help";
    }

    @GetMapping("/about")
    public String showAboutPage(Model model) {
        model.addAttribute("adoptedCount", animalService.getAdoptedCount());
        model.addAttribute("inShelterCount", animalService.getInShelterCount());
        model.addAttribute("volunteersCount", animalService.getVolunteersCount());
        model.addAttribute("totalDonations", animalService.getTotalDonationsSum());
        return "about";
    }

    // Страница управления складом
    @GetMapping("/admin/supplies")
    public String manageSupplies(Model model) {
        model.addAttribute("supplies", animalService.getAllSupplies());
        model.addAttribute("newSupply", new Supply()); // Для формы добавления
        return "admin-supplies";
    }

    // Сохранение нового или измененного товара
    @PostMapping("/admin/supplies/save")
    public String saveSupply(@ModelAttribute("newSupply") Supply supply) {
        animalService.saveSupply(supply);
        return "redirect:/admin/supplies";
    }

    // Удаление товара (опционально, но полезно)
    @GetMapping("/admin/supplies/delete/{id}")
    public String deleteSupply(@PathVariable Long id) {
        animalService.deleteSupply(id);
        return "redirect:/admin/supplies";
    }
}