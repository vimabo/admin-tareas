package com.acelerait.vimabo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acelerait.vimabo.models.Task;
import com.acelerait.vimabo.models.User;
import com.acelerait.vimabo.services.AppService;

@Controller
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private AppService servicio;

	@GetMapping("/new")
	public String newProject(@ModelAttribute("task") Task task, HttpSession session, Model model) {
		/* REVISAMOS LA SESIÓN */
		User currentUser = (User) session.getAttribute("userSession");

		if (currentUser == null) {
			return "redirect:/";
		}
		/* REVISAMOS LA SESIÓN */

		/* CONSULTAMOS TODOS USUARIOS */
		List<User> listUsers = servicio.findByAllUser();
		model.addAttribute("listUsers", listUsers);

		return "new.jsp";
	}

	@PostMapping("/create")
	public String createProject(@Valid @ModelAttribute("task") Task task, BindingResult result, HttpSession session,
			Model model) {

		/* REVISAMOS LA SESIÓN */
		User currentUser = (User) session.getAttribute("userSession");

		if (currentUser == null) {
			return "redirect:/";
		}
		/* REVISAMOS LA SESIÓN */

		// Revisamos si hay errores en el registro
		if (result.hasErrors()) {
			List<User> listUsers = servicio.findByAllUser();
			model.addAttribute("listUsers", listUsers);
			return "new.jsp";
		} else {
			// Guardamos la tarea
			servicio.saveTarea(task);
			return "redirect:/tasks";
		}

	}

	@GetMapping("/detail/{taskId}")
	public String detail(@PathVariable("taskId") Long id, HttpSession session, Model model) {

		/* REVISAMOS LA SESIÓN */
		User currentUser = (User) session.getAttribute("userSession");

		if (currentUser == null) {
			return "redirect:/";
		}
		/* REVISAMOS LA SESIÓN */
		Task taskDetail = servicio.findTask(id);
		model.addAttribute("nameTask", taskDetail.getName());
		model.addAttribute("taskDetail", taskDetail);

		if (currentUser.getId() == taskDetail.getLead().getId()) {
			session.setAttribute("editDelete", "btn btn-primary");
		} else {
			session.setAttribute("editDelete", "btn btn-primary disabled");
		}

		if (currentUser.getId() == taskDetail.getAssignee().getId()) {
			session.setAttribute("editComplete", "btn btn-primary");
		} else {
			session.setAttribute("editComplete", "btn btn-primary disabled");
		}

		return "detail.jsp";

	}

	@GetMapping("/edit/{taskId}")
	public String edit(@PathVariable("taskId") Long id, HttpSession session, Model model) {

		/* REVISAMOS LA SESIÓN */
		User currentUser = (User) session.getAttribute("userSession");

		if (currentUser == null) {
			return "redirect:/";
		}
		/* REVISAMOS LA SESIÓN */

		Task taskEdit = servicio.findTask(id);
		List<User> listUsers = servicio.findByAllUser();
		model.addAttribute("listUsers", listUsers);

		/* Revisión que el id del lead coincida con el de la sesión */
		if (currentUser.getId() != taskEdit.getLead().getId()) {
			return "redirect:/tasks";
		}

		model.addAttribute("taskEdit", taskEdit);
		model.addAttribute("nameTask", taskEdit.getName());
		return "edit.jsp";

	}

	@PutMapping("/update")
	public String update(@Valid @ModelAttribute("taskEdit") Task task, BindingResult result, Model model,
			HttpSession session) {
		/* REVISAMOS LA SESIÓN */
		User currentUser = (User) session.getAttribute("userSession");

		if (currentUser == null) {
			List<User> listUsers = servicio.findByAllUser();
			model.addAttribute("listUsers", listUsers);
			return "redirect:/";
		}
		/* REVISAMOS LA SESIÓN */

		if (result.hasErrors()) {
			return "edit.jsp";
		} else {
			servicio.saveTarea(task);
			return "redirect:/tasks";
		}

	}

	@GetMapping("/high")
	public String orderByHigh(HttpSession session, Model model) {
		/* REVISAMOS LA SESIÓN */
		User currentUser = (User) session.getAttribute("userSession");

		if (currentUser == null) {
			return "redirect:/";
		}
		/* REVISAMOS LA SESIÓN */
		List<Task> listTask = servicio.findByHigh();
		model.addAttribute("listTasks", listTask);
		return "dashboard.jsp";
	}

	@GetMapping("/low")
	public String orderBylow(HttpSession session, Model model) {
		/* REVISAMOS LA SESIÓN */
		User currentUser = (User) session.getAttribute("userSession");

		if (currentUser == null) {
			return "redirect:/";
		}
		/* REVISAMOS LA SESIÓN */
		List<Task> listTask = servicio.findByLow();
		model.addAttribute("listTasks", listTask);
		return "dashboard.jsp";
	}

	@DeleteMapping(value = "/{id}")
	public String destroy(@PathVariable("id") Long id, HttpSession session) {

		/* REVISAMOS LA SESIÓN */
		User currentUser = (User) session.getAttribute("userSession");

		if (currentUser == null) {
			return "redirect:/";
		}
		/* REVISAMOS LA SESIÓN */

		servicio.deleteTask(id);
		return "redirect:/tasks";
	}

}
