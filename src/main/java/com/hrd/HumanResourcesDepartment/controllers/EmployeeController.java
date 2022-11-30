package com.hrd.HumanResourcesDepartment.controllers;

import com.hrd.HumanResourcesDepartment.config.GlobalClass;
import com.hrd.HumanResourcesDepartment.models.Employee;
import com.hrd.HumanResourcesDepartment.models.Position;
import com.hrd.HumanResourcesDepartment.models.Role;
import com.hrd.HumanResourcesDepartment.repository.DepartmentRepository;
import com.hrd.HumanResourcesDepartment.repository.EmployeeRepository;
import com.hrd.HumanResourcesDepartment.repository.PositionRepository;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class EmployeeController {

    @Autowired
    GlobalClass globalClass;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/statements") // страница для работы с выписками
    public String statements(Map<String, Object> model) {
        model.put("title", "Выписки");

        Iterable<Employee> employees = employeeRepository.findAll();
        model.put("employees", employees);

        return "statements";
    }

    @GetMapping ("/employeeStatement/{id}")
    public String employeeStatement(@PathVariable(value = "id") long  id, Map<String, Object> model) throws Docx4JException {

        Optional<Employee> employee = employeeRepository.findById(id);
        ArrayList<Employee> resEmployee = new ArrayList<>();
        employee.ifPresent(resEmployee::add);

        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");

        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();

        mainDocumentPart.addStyledParagraphOfText("Heading2", "ООО \"HRD\"");
        mainDocumentPart.addStyledParagraphOfText("Heading2", "ИНН: 263516479611");
        mainDocumentPart.addStyledParagraphOfText("Heading2", "Адрес: г.Минск, ул. Гикало, 9");

        mainDocumentPart.addParagraphOfText("____________________________________ СПРАВКА ____________________________________");
        mainDocumentPart.addParagraphOfText("Cправка выдана " + resEmployee.get(0).getFirstName() + " " + resEmployee.get(0).getLastName() + " "
                + resEmployee.get(0).getPatronymic() + " в подтверждение того, что он(она) с " + resEmployee.get(0).getStartWork()
                + "г. по настоящее время работает в ООО \"HRD\" в должности " + resEmployee.get(0).getPosition() + ".");
        mainDocumentPart.addParagraphOfText("Справка подлежит предъявлению по месту требования. Срок действия 6 месяцев со дня выдачи.");
        mainDocumentPart.addParagraphOfText(" ");

        mainDocumentPart.addParagraphOfText("Дата выдачи  " + formatForDateNow.format(dateNow));

        File exportFile = new File("Справка " + resEmployee.get(0).getFirstName() + ".docx");
        wordPackage.save(exportFile);

        return "redirect:/statements";
    }

    @GetMapping("/statementAllEmployees")
    public String statementAllEmployees(Map<String, Object> model) {
        model.put("title", "Выписка всех сотрудников");


        return "/statementAllEmployees";
    }

    @PostMapping("/statementAllEmployees")
    public String statementAllEmployeesSave(Map<String, Object> model) throws Docx4JException {

        Iterable<Employee> employees = employeeRepository.findAll();
        model.put("employees", employees);

        ArrayList<Employee> resEmployee = new ArrayList<>();
        employees.forEach(resEmployee::add);

        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
        mainDocumentPart.addStyledParagraphOfText("Title", "Список всех сотрудников предприятия");


        for(int i =0; i<resEmployee.size();i++){
            mainDocumentPart.addParagraphOfText((i+1) + "| " + resEmployee.get(i).getFirstName() + " " + resEmployee.get(i).getLastName() + " " + resEmployee.get(i).getPatronymic() + ", Телефон: " + resEmployee.get(i).getTelephone() + ", Должность: " + resEmployee.get(i).getPosition());
        }

        File exportFile = new File("Список всех сотрудников предприятия.docx");
        wordPackage.save(exportFile);


        return "redirect:statementAllEmployees";
    }

    @GetMapping("/statementSalary")
    public String statementSalary(Map<String, Object> model) {
        model.put("title", "Выписка о ЗП");

        return "/statementSalary";
    }

    @PostMapping("/statementSalary")
    public String statementSalarySave(Map<String, Object> model) throws Docx4JException {

        Iterable<Employee> employees = employeeRepository.findAll();
        ArrayList<Employee> resEmployee = new ArrayList<>();
        employees.forEach(resEmployee::add);

        Iterable<Position> positions = positionRepository.findAll();
        ArrayList<Position> resPositions = new ArrayList<>();
        positions.forEach(resPositions::add);

        WordprocessingMLPackage wordPackage2 = WordprocessingMLPackage.createPackage();
        MainDocumentPart mainDocumentPart2 = wordPackage2.getMainDocumentPart();
        mainDocumentPart2.addStyledParagraphOfText("Title", "Заработная плата сотрудников");

        Double salaryQ = 0.0;

        for( int i = 0; i < resEmployee.size(); i++){

            for(int j=0; j<resPositions.size(); j++){

                System.out.println("resPositions.get(j).getPositionName() = " + resPositions.get(j).getPositionName());
                System.out.println("resEmployee.get(i).getPosition() = " + resEmployee.get(i).getPosition());
                if( (resPositions.get(j).getPositionName()).equals(resEmployee.get(i).getPosition())){
                    salaryQ = resPositions.get(j).getSalary();

                    mainDocumentPart2.addParagraphOfText((i+1) + "| " + resEmployee.get(i).getFirstName() + " " + resEmployee.get(i).getLastName() + " " + resEmployee.get(i).getPatronymic() + ", Должность: " + resEmployee.get(i).getPosition() + ", Зарплата: " + salaryQ + " бел.руб.");

                }

            }
        }

        File exportFile2 = new File("Заработная плата сотрудников.docx");
        wordPackage2.save(exportFile2);


        return "redirect:statementSalary";
    }

    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("title", "about");
        return "about";
    }

    @GetMapping("/")  //первая страница, что видит любой пользователь
    public String home(Model model) {
        model.addAttribute("title", "HRD");
        return "index";
    }

    @GetMapping("/main")  //первая страница, что видит любой пользователь
    public String main(Model model) {
        model.addAttribute("title", "Ваша система");

        Role roleAdmin = Role.ADMIN;
        Set<Role> allRolesUser = globalClass.getUserRole();
        System.out.println("roleAdmin = " + roleAdmin);


        for(int i=0; i<allRolesUser.size(); i++){
            if(allRolesUser.contains(roleAdmin)){
                System.out.println("This account have ADMIN!!");
                model.addAttribute("role",true);
                break;
            }
        }


        return "main";
    }

    @GetMapping("/employees")
    public String viewEmployees(Map<String, Object> model) {

        model.put("title", "Работа с сотрудниками");

        Iterable<Employee> employees = employeeRepository.findAll();
//        Iterable<PositionEmployee> positionEmployees = positionEmployeeRepository.findAllById("");
        model.put("employees", employees);

//        List<Employee> result = new ArrayList<>();
//        for (Employee str : employees) {
//            result.add(str);
//        }
//
//        Iterable<Position> positions = positionRepository.findAll();
//        List<Position> resultPosition = new ArrayList<>();
//        for (Position str : positions) {
//            resultPosition.add(str);
//        }
//
//        for (int i = 0; i < result.size(); i++) {
//            System.out.println("result.get(i).getPosition()" + result.get(i).getPosition());
//            for (int j = 0; i < resultPosition.size(); j++) {
//                System.out.println("result.get(i).getPositionName()" + resultPosition.get(j).getPositionName());
//                if ((result.get(i).getPosition()).equals(resultPosition.get(j).getPositionName())==true) {
//                    System.out.println("result.get(i).getPosition()" + result.get(i).getPosition());
//                    System.out.println("result.get(i).getPositionName()" + resultPosition.get(j).getPositionName());
//                }
//            }
//            System.out.println("-------------------------------");
//        }


        return "employees";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model){

        Iterable<Employee> employees;
        if(filter != null && !filter.isEmpty()){
            employees = employeeRepository.findByFirstName(filter);
        }
        else{
            employees = employeeRepository.findAll();
        }

        model.put("employees", employees);

        return "employees";
    }

    @GetMapping("/addEmployee") // страница для работы с сотрудниками
    public String addEmployee(Map<String, Object> model) {
        model.put("title", "Найм сотрудника");

//        Iterable<Department> departments = departmentRepository.findAll();
//        model.put("departments", departments);

        Iterable<Position> positions = positionRepository.findAll();
        model.put("positions", positions);

        return "addEmployee";
    }

    @PostMapping("/addEmployee")
    public String saveEmployee(
                                @RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String patronymic,
                                @RequestParam String position,
                                @RequestParam int experience,
                                @RequestParam String startWork,
                                @RequestParam String telephone,
                                @RequestParam String email,
                                @RequestParam int timeWork,
                                Map<String, Object> model
    ){

        System.out.println("Before position!");
        System.out.println("position = " + position);
        if(position.equals("0")){
            model.put("message", "Необходимо указать должность!");
            //System.out.println("need input position!");
            Iterable<Position> positions = positionRepository.findAll();
            model.put("positions", positions);

            return "addEmployee";
        }
        System.out.println("After position!");

        System.out.println(1);
        Employee employee = new Employee(firstName, lastName, patronymic, experience, startWork, telephone, email, timeWork, position);
        employeeRepository.save(employee);
        System.out.println(2);

        return "redirect:employees";
    }

    @GetMapping("/edit")
    public String editEmployee(Map<String, Object> model){
        model.put("title", "Редактирование сотрудника");

        Iterable<Employee> employees = employeeRepository.findAll();
//        Iterable<PositionEmployee> positionEmployees = positionEmployeeRepository.findAllById("");
        model.put("employees", employees);

        return "editEmployees";
    }

    @GetMapping("/edit/{id}")
    public String editIdEmployee(@PathVariable(value = "id") long  id, Map<String, Object> model){

        Iterable<Position> positions = positionRepository.findAll();
        model.put("positions", positions);
//        Iterable<Department> departments = departmentRepository.findAll();
//        model.put("departments", departments);


        if(!employeeRepository.existsById(id)){
            return "employees";
        }

        Optional<Employee> employee = employeeRepository.findById(id);

        ArrayList<Employee> resEmployee = new ArrayList<>();
        employee.ifPresent(resEmployee::add);
        model.put("resEmployee", resEmployee);

        model.put("title", employee.get().getFirstName());

        return "editIdEmployee";
    }

    @PostMapping("/edit/{id}")
    public String editIdEmployeeUpdate(
            @PathVariable(value = "id") long  id,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String patronymic,
            @RequestParam String position,
            @RequestParam int experience,
            @RequestParam String startWork,
            @RequestParam String telephone,
            @RequestParam String email,
            @RequestParam int timeWork,
            Map<String, Object> model
    ){
        if(position.equals("0")){
            model.put("message", "Необходимо указать должность!");
            //System.out.println("need input position!");
            Iterable<Position> positions = positionRepository.findAll();
            model.put("positions", positions);

            return "editIdEmployee";
        }

        Employee employee = employeeRepository.findById(id).orElseThrow();

        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setPatronymic(patronymic);
        employee.setExperience(experience);
        employee.setStartWork(startWork);
        employee.setTelephone(telephone);
        employee.setEmail(email);
        employee.setTimeWork(timeWork);
        employee.setPosition(position);
        employeeRepository.save(employee);

        return "redirect:/employees";
    }

    @GetMapping("/remove")
    public String removeEmployee(Map<String, Object> model){
        model.put("title", "Увольнение сотрудника");

        Iterable<Employee> employees = employeeRepository.findAll();
//        Iterable<PositionEmployee> positionEmployees = positionEmployeeRepository.findAllById("");
        model.put("employees", employees);

        return "removeEmployee";
    }

    @PostMapping("/remove/{id}")
    public String removeIdEmployee(@PathVariable(value = "id") long  id){

//        Employee employee = employeeRepository.findById(id).orElseThrow();
//        employeeRepository.delete(employee);
        employeeRepository.deleteById(id);

        return "redirect:/employees";
    }

}
