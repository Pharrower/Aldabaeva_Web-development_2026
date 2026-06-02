# Пример реализации сквозного взаимодействия

На примере операции «Получение списка животных» показана связь между слоями:

1. **Entity (Сущность):**
   ```java
   @Entity @Table(name = "animals")
   public class Animal { @Id @GeneratedValue Long id; String name; }
   ```

2. **Repository (Foundation):**
    ```java
    public interface AnimalRepository extends JpaRepository<Animal, Long> {}
    ```

3. **Service (Mediator):**
    ```java
    @Service @RequiredArgsConstructor
    public class AnimalService {
        private final AnimalRepository repository;
        public List<Animal> getAll() { return repository.findAll(); }
    }
    ```

4. **Controller (Control):**
    ```java
    @Controller @RequiredArgsConstructor
    public class AnimalController {
        private final AnimalService service;
        @GetMapping("/animals")
        public String list(Model model) {
            model.addAttribute("animals", service.getAll());
            return "catalog"; // возвращает шаблон Thymeleaf
        }
    }
    ```

