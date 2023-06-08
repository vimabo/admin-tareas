package com.acelerait.vimabo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.acelerait.vimabo.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	// Seleccionamos las tareas ordenadas ascendentemente
	@Query("SELECT t FROM Task t WHERE t.priority != 'medium' ORDER BY t.priority ASC")
	List<Task> findByAllByOrderByPriorityAsc();

	// Seleccionamos las tareas ordenadas descendentemente
	@Query("SELECT t FROM Task t WHERE t.priority != 'medium' ORDER BY t.priority DESC")
	List<Task> findByAllByOrderByPriorityDesc();

}
