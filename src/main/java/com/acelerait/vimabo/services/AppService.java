package com.acelerait.vimabo.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.acelerait.vimabo.models.LoginUser;
import com.acelerait.vimabo.models.Task;
import com.acelerait.vimabo.models.User;
import com.acelerait.vimabo.repository.TaskRepository;
import com.acelerait.vimabo.repository.UserRepository;

@Service
public class AppService {

	@Autowired
	private UserRepository repoUser;

	@Autowired
	private TaskRepository repoTask;

	// Función que registra un usuario
	public User register(User nuevoUsuario, BindingResult result) {

		// Comparando las contraseñas
		if (!nuevoUsuario.getPassword().equals(nuevoUsuario.getConfirm())) {
			result.rejectValue("password", "Matches", "Las contraseñas no coinciden.");
		}

		// Revisar si el correo electrónico ya existe
		String nuevoEmail = nuevoUsuario.getEmail();
		if (repoUser.findByEmail(nuevoEmail).isPresent()) {
			result.rejectValue("email", "Unique", "El correo electrónico fue ingresado previamente.");
		}

		if (result.hasErrors()) {
			return null;
		} else {
			// Encriptamos la contraseña
			String contraEncriptada = BCrypt.hashpw(nuevoUsuario.getPassword(), BCrypt.gensalt());
			nuevoUsuario.setPassword(contraEncriptada);
			return repoUser.save(nuevoUsuario);
		}

	}

	// Función de inicio de sesión
	public User login(LoginUser nuevoLogin, BindingResult result) {

		// Buscamos por correo electrónico
		Optional<User> posibleUsuario = repoUser.findByEmail(nuevoLogin.getEmail());
		if (!posibleUsuario.isPresent()) {
			result.rejectValue("email", "Unique", "Correo no registrado");
			return null;
		}

		User userLogin = posibleUsuario.get(); // Usuario que me regresa mi DB
		if (!BCrypt.checkpw(nuevoLogin.getPassword(), userLogin.getPassword())) {
			result.rejectValue("password", "Matches", "Contraseña inválida");
		}

		if (result.hasErrors()) {
			return null;
		} else {
			return userLogin;
		}

	}

	/* Guarda objeto de tarea en BD */
	public Task saveTarea(Task nuevoTarea) {
		return repoTask.save(nuevoTarea);
	}

	/* Regrese objeto de usuario en base a su ID */
	public User findUser(Long id) {
		return repoUser.findById(id).orElse(null);
	}

	/* Guarda en BD los cambios de usuario */
	public User saveUser(User user) {
		return repoUser.save(user);
	}

	/* Regrese la lista de todas las tareas */
	public List<Task> findByAll() {
		return (List<Task>) repoTask.findAll();
	}

	/* Regrese la lista de todos los usuarios */
	public List<User> findByAllUser() {
		return (List<User>) repoUser.findAll();
	}

	/* Regrese objeto de tarea en base a su ID */
	public Task findTask(Long id) {
		return repoTask.findById(id).orElse(null);
	}

	/* Regrese la lista de todos las tareas ordenadas de high-low */
	public List<Task> findByHigh() {
		return repoTask.findByAllByOrderByPriorityAsc();
	}

	/* Regrese la lista de todos las tareas ordenadas de low-high */
	public List<Task> findByLow() {
		return repoTask.findByAllByOrderByPriorityDesc();
	}

	/* Elimina una tarea de la base de datos */
	public void deleteTask(Long taskId) {
		repoTask.deleteById(taskId);
	}

}