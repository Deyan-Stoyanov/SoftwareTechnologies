package projectrider.controller;

import projectrider.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import projectrider.bindingModel.ProjectBindingModel;
import projectrider.repository.ProjectRepository;

import java.util.List;

@Controller
public class ProjectController {
	private final ProjectRepository projectRepository;

	@Autowired
	public ProjectController(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	@GetMapping("/")
	public String index(Model model) {
		List<Project> projects = projectRepository.findAll();

		model.addAttribute("projects", projects);
		model.addAttribute("view", "project/index");

		return "base-layout";
	}

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("view", "project/create");
		return "base-layout";
	}

	@PostMapping("/create")
	public String createProcess(Model model, ProjectBindingModel projectBindingModel) {
		Project newProject = new Project(
				projectBindingModel.getTitle(),
				projectBindingModel.getDescription(),
				projectBindingModel.getBudget()
		);
		this.projectRepository.saveAndFlush(newProject);
		return "redirect:/";
	}

	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable int id) {
		Project project = projectRepository.findOne(id);
		if (project != null) {
			model.addAttribute("project", project);
			model.addAttribute("view", "project/edit");
			return "base-layout";
		}
		return "redirect:/";
	}

	@PostMapping("/edit/{id}")
	public String editProcess(@PathVariable int id, Model model, ProjectBindingModel projectBindingModel) {
		if (projectBindingModel.getTitle().equals("") ||
				projectBindingModel.getDescription().equals("")) {
			Project project = new Project();
			project.setId(id);
			project.setTitle(projectBindingModel.getTitle());
			project.setDescription(projectBindingModel.getDescription());
			project.setBudget(projectBindingModel.getBudget());
			model.addAttribute("project", project);
			model.addAttribute("view", "project/edit");
			return "base-layout";
		}

		Project project = projectRepository.findOne(id);
		if (project != null) {
			project.setTitle(projectBindingModel.getTitle());
			project.setDescription(projectBindingModel.getDescription());
			project.setBudget(projectBindingModel.getBudget());
			projectRepository.saveAndFlush(project);
		}

		return "redirect:/";
	}

	@GetMapping("/delete/{id}")
	public String delete(Model model, @PathVariable int id) {
		Project deleteProject = this.projectRepository.findOne(id);
		model.addAttribute("view", "project/delete");
		model.addAttribute("project", deleteProject);
		return "base-layout";
	}

	@PostMapping("/delete/{id}")
	public String deleteProcess(@PathVariable int id, Model model, ProjectBindingModel projectBindingModel) {
		Project deleteProject = this.projectRepository.findOne(id);
		model.addAttribute("view", "project/delete");
		model.addAttribute("task", deleteProject);
		projectRepository.delete(deleteProject);
		return "redirect:/";
	}
}
