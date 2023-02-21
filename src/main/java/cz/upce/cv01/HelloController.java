package cz.upce.cv01;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/*
 * The key difference between @Controler and @RestController annotation is @ResponseBody annotation,
 *  @Controler does not automatically add the @ResponseBody annotation to all of the controller’s methods,
 *  which means that you need to add it to each method individually if you want to return a JSON or XML response.
 * @RestController automatically adds the @ResponseBody annotation to all of the controller’s methods.
 */

/*
* XML uses tags to define the elements and stores data in a tree structure,
*
* JSON is stored like a map with key/value pairs,
*
* YAML allows representation of data both in list or sequence format and in the form
*  of a map with key/value pairs. It uses indentation to define structured data. So each
*  block in the YAML is differentiated by the number of white spaces.
*/
@RestController
public class HelloController {
    @GetMapping("")
    public String helloWorld() {
        return "Hello world from Spring Boot application.";
    }

    @GetMapping(path="/hello/{parameter}")
    public String helloWorldPath(@PathVariable String parameter) {
        return String.format("Hello, %s.", parameter);
    }

    @GetMapping("/hello/query")
    @ResponseBody
    public String helloWorldQuery(@RequestParam String param) {
        return String.format("Hello, %s.", param);
    }

    @PostMapping("students")
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@RequestBody Student student){
        System.out.println("Name: " + student.getName());
        System.out.println("Age: " + student.getAge());
        return student;
    }
}
