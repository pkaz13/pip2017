/*
 *   Copyright 2012-2014 the original author or authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package pl.hycom.pip.messanger.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.hycom.pip.messanger.model.Greeting;
import pl.hycom.pip.messanger.model.GreetingListWrapper;
import pl.hycom.pip.messanger.service.GreetingService;

import javax.validation.Valid;


@Log4j2
@Controller
public class GreetingController {


    @Autowired
    private GreetingService greetingService;


    @GetMapping("/admin/greetings")
    public String getGreetings(Model model) {
        return greetingService.getGreetings(model);
    }

    @PostMapping("/admin/greetings")
    public String addGreetings(@Valid GreetingListWrapper greetingListWrapper, BindingResult bindingResult, Model model) {
        return greetingService.addGreetings(greetingListWrapper,bindingResult,model);
        }


    @PostMapping("/admin/greeting")
    public String addGreeting(@Valid Greeting greeting, BindingResult bindingResult, Model model) {
      return greetingService.addGreeting(greeting, bindingResult,model);
    }


    @DeleteMapping("/admin/deleteGreeting/{locale}")
    public String removeGreeting(@PathVariable String locale, Model model) {
        return greetingService.removeGreeting(locale,model);
    }








}
