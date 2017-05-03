package edu.lyon1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class RootController {

  @RequestMapping(value="/",method = RequestMethod.GET)
  public ModelAndView test(@RequestHeader HttpHeaders headers) {
    String headerName = "headerName";
    List<HttpHeader> header = new ArrayList<>();
    // Méthode avec Set (ne fonctionne pas ... )
    // Set<Entry<String, List<String>>> entries = headers.entrySet();
    // for (Entry<String, List<String>> entry : entries) {
    //  header.add(new HttpHeader(entry.getKey(),String.join(",",entry.getValue())));
    // }
    for (String name : headers.keySet()) {
      header.add(new HttpHeader(name,String.join(",",headers.getValuesAsList(name))));
    }

    ModelAndView mav = new ModelAndView();
    mav.addObject(headerName, header);
    mav.addObject("titre", "IUT");
    mav.addObject("corps", "bonjour");
    mav.setViewName("template");
    return mav;
  }

  @ResponseBody
  @RequestMapping(value="/",method = RequestMethod.POST)
  public String intercepterPost () {
    return "OK";
  }

  @ResponseBody
  @RequestMapping(value="/user",method = RequestMethod.GET)
  public String getUser () {
    User user1 = new User("Moi", "Rien");
    return user1.getFirstName();
  }

  private class HttpHeader {

    String name;
    String value;

    private HttpHeader(String name, String value) {
      this.name = name;
      this.value = value;
    }

    private String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    private String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }

  private class User {

    String firstName;
    String lastName;

    private User(String firstName, String lastName) {
      this.firstName = firstName;
      this.lastName = lastName;
    }

    private String getFirstName() {
      return firstName;
    }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    private String getLastName() {
      return lastName;
    }

    public void setLastName(String lastName) {
      this.lastName = lastName;
    }
  }

}
