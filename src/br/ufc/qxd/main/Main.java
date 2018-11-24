package br.ufc.qxd.main;

import java.util.List;
import java.util.Scanner;

import br.ufc.qxd.connection.MyLock;
import br.ufc.qxd.dao.DepartmentDAO;
import br.ufc.qxd.dao.ProjectDAO;
import br.ufc.qxd.entities.Department;
import br.ufc.qxd.entities.Project;
import br.ufc.qxd.implementation.DepartmentNeo4jDAO;
import br.ufc.qxd.implementation.ProjectNeo4jDAO;

public class Main {
	private static Scanner input;
	private static Scanner input_cad;
	private static Scanner type_employee;
	private static Scanner input_double;
	private static Scanner input_int;
	private static Scanner input_long;
	
	private static String name, sex, birthday, street, neighborhood;
	private static double salary;
	private static int number, cep;
	
	public static MyLock myLock;
	private static DepartmentDAO departmentDAO = new DepartmentNeo4jDAO();
	private static ProjectDAO projectDAO = new ProjectNeo4jDAO();

	public static void main(String[] args) {
		int option;
		Main.input = new Scanner(System.in);
		Main.type_employee = new Scanner(System.in);
		Main.input_double = new Scanner(System.in);
		Main.input_int = new Scanner(System.in);
		Main.input_long = new Scanner(System.in);
		Main.input_cad = new Scanner(System.in);
		boolean end = false;
		while(!end) {
			System.out.println("| 1 | Cadastrar.");
			System.out.println("| 2 | Atualizar.");
			System.out.println("| 3 | Buscar.");
			System.out.println("| 4 | Remover.");
			
			option = input.nextInt();
			input.nextLine();
			
			switch(option) {
				case 1:{
					boolean flagCad = false;
					int optionCad;
					while(!flagCad) {
						System.out.println("| 1 | Departamento.");
						System.out.println("| 2 | Projeto.");
						
						optionCad = input.nextInt();
						input.nextLine();
						
						switch(optionCad) {
							case 1:{
								System.out.println("Digite o nome do Departamento: ");
								name = input.nextLine();
								System.out.println("Digite o numero do Departamento: ");
								number = input_int.nextInt();
								Department depart = new Department(name, number);
								departmentDAO.insert(depart);
								System.out.println("Departamento cadastrado com sucesso!");
								break;
							}
							case 2:{
								String term;
								long idDepartment;
								System.out.println("Digite o nome do Projeto: ");
								name = input.nextLine();
								System.out.println("Digite o numero do Projeto: ");
								number = input_int.nextInt();
								System.out.println("Digite o prazo do Projeto: ");
								term = input.nextLine();
								System.out.println("Digite o ID do DEPARTAMENTO que esse Projeto vai pertencer: ");
								idDepartment = input_long.nextLong();
								Project proj = new Project(name, number, term);								
								projectDAO.insert(proj);
								projectDAO.relationshipTodepartment(proj.getId_project(), idDepartment);
								System.out.println("Projeto cadastrado com sucesso!");								
								break;
							}
							default:{
								flagCad = true;
								break;
							}
						}
					}
					break;
				}
				case 2:{
					boolean flagUpdate = false;
					int optionUpdate;
					while(!flagUpdate) {
						System.out.println("| 1 | Departamento.");
						System.out.println("| 2 | Projeto.");
						optionUpdate = input.nextInt();
						input.nextLine();
						switch(optionUpdate) {
							case 1:{
								long idOldDep;
								System.out.println("Digite o ID do Departamento que deseja alterar: ");
								idOldDep = input_long.nextLong();
								while(departmentDAO.findById(idOldDep) == null) {
									System.out.println("Departamento invalido, digite outro ID: ");
									idOldDep = input_long.nextLong();
								}
								System.out.println("Digite novo nome para o Departamento: ");
								name = input.nextLine();
								System.out.println("Digite novo numero para o Departamento: ");
								number = input_int.nextInt();
								Department dep = new Department(name, number);
								if(departmentDAO.update(idOldDep, dep))
									System.out.println("Departamento atualizado com sucesso!");
								break;
							}
							case 2:{
								long idOldProj;
								String term;
								System.out.println("Digite o ID do Projeto que deseja alterar: ");
								idOldProj = input_long.nextLong();
								while(projectDAO.findById(idOldProj) == null) {
									System.out.println("Projeto inválido, digite outro ID: ");
									idOldProj = input_long.nextLong();
								}
								System.out.println("Digite novo nome para o Projeto: ");
								name = input.nextLine();
								System.out.println("Digite novo numero para o Projeto: ");
								number = input_int.nextInt();
								System.out.println("Digite novo prazo para o Projeto: ");
								term = input.nextLine();
								Project project = new Project(name, number, term);
								if(projectDAO.update(idOldProj, project))
									System.out.println("Projeto atualizado com sucesso!");
								break;
							}
							default:{
								flagUpdate = true;
								break;
							}
						}
					}
					break;
				}
				case 3:{
					boolean flagFind = false;
					int optionFind;
					while(!flagFind) {
						System.out.println("| 1 | Departamentos.");
						System.out.println("| 2 | Projetos.");
						optionFind = input.nextInt();
						input.nextLine();
						switch(optionFind) {
							case 1:{
								List<Department> list = departmentDAO.findAll();
								for(Department dep : list)
									System.out.println(dep);
								break;
							}
							case 2:{
								List<Project> list = projectDAO.findAll();
								for(Project proj : list)
									System.out.println(proj);
								break;
							}
							default:{
								flagFind = true;
								break;
							}
						}
					}
					break;
				}
				case 4:{
					boolean flagRemove = false;
					int optionRemove;
					while(!flagRemove) {
						System.out.println("| 1 | Departamento.");
						System.out.println("| 2 | Projeto.");
						optionRemove = input.nextInt();
						input.nextLine();
						switch(optionRemove) {
							case 1:{
								long idDep;
								System.out.println("Digite o ID do Departamento: ");
								idDep = input_long.nextLong();
								while(departmentDAO.findById(idDep).equals(null)) {
									System.out.println("Departamento inválido, digite outro ID: ");
									idDep = input_long.nextLong();
								}
								if(departmentDAO.remove(idDep))
									System.out.println("Departamento exluido com sucesso!");
								break;
							}
							case 2:{
								long idProj;
								System.out.println("Digite o ID do Projeto: ");
								idProj = input_long.nextLong();
								while(projectDAO.findById(idProj).equals(null)) {
									System.out.println("Projeto inválido, digite outro ID: ");
									idProj = input_long.nextLong();
								}
								if(projectDAO.remove(idProj))
									System.out.println("Projeto excluido com sucesso!");
								break;
							}
							default:{
								flagRemove = true;
								break;
							}
						}
					}
					break;
				}
				default:{
					end = true;
					System.exit(0);
					break;
				}
			}
		}
	}
}
