package br.ufc.qxd.main;

import java.util.List;
import java.util.Scanner;

import br.ufc.qxd.connection.MyLock;
import br.ufc.qxd.dao.AddressDAO;
import br.ufc.qxd.dao.ClearEmployeeDAO;
import br.ufc.qxd.dao.DepartmentDAO;
import br.ufc.qxd.dao.DependentDAO;
import br.ufc.qxd.dao.ProjectDAO;
import br.ufc.qxd.dao.ResearcherDAO;
import br.ufc.qxd.dao.SecretaryDAO;
import br.ufc.qxd.dao.WorkedHoursDAO;
import br.ufc.qxd.entities.Address;
import br.ufc.qxd.entities.ClearEmployee;
import br.ufc.qxd.entities.Department;
import br.ufc.qxd.entities.Dependent;
import br.ufc.qxd.entities.Employee;
import br.ufc.qxd.entities.Project;
import br.ufc.qxd.entities.Researcher;
import br.ufc.qxd.entities.Secretary;
import br.ufc.qxd.entities.WorkedHours;
import br.ufc.qxd.implementation.AddressNeo4jDAO;
import br.ufc.qxd.implementation.ClearEmployeeNeo4jDAO;
import br.ufc.qxd.implementation.DepartmentNeo4jDAO;
import br.ufc.qxd.implementation.DependetNeo4jDAO;
import br.ufc.qxd.implementation.ProjectNeo4jDAO;
import br.ufc.qxd.implementation.ResearcherNeo4jDAO;
import br.ufc.qxd.implementation.SecretaryNeo4jDAO;
import br.ufc.qxd.implementation.WorkedHoursNeo4jDAO;
import br.ufc.qxd.util.*;

public class Main {
	private static Scanner input;
	private static Scanner input_double;
	private static Scanner input_int;
	private static Scanner input_long;
	
	private static String name, sex, birthday, street, neighborhood;
	private static double salary;
	private static int number, cep;
	
	public static MyLock myLock;
	private static DepartmentDAO departmentDAO = new DepartmentNeo4jDAO();
	private static ProjectDAO projectDAO = new ProjectNeo4jDAO();
	private static AddressDAO addressDAO = new AddressNeo4jDAO();
	private static ClearEmployeeDAO clearEmployeeDAO = new ClearEmployeeNeo4jDAO();
	private static DependentDAO dependentDAO = new DependetNeo4jDAO();
	private static ResearcherDAO researcherDAO = new ResearcherNeo4jDAO();
	private static SecretaryDAO secretaryDAO = new SecretaryNeo4jDAO();
	private static WorkedHoursDAO workedHoursDAO = new WorkedHoursNeo4jDAO();

	public static void main(String[] args) {
		int option;
		Main.input = new Scanner(System.in);
		Main.input_double = new Scanner(System.in);
		Main.input_int = new Scanner(System.in);
		Main.input_long = new Scanner(System.in);
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
						System.out.println("| 3 | Funcionário.");
						System.out.println("| 4 | Horas Trabalhadas para Pesquisador.");
						System.out.println("| 5 | Dependente.");
						System.out.println("| 6 | Funcionário a um Projeto.");
						System.out.println("| 0 | Sair.");
						
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
								while(departmentDAO.findById(idDepartment).equals(null)) {
									System.out.println("Departamento inválido, digite outro ID: ");
									idDepartment = input_long.nextLong();
								}
								Project proj = new Project(name, number, term);								
								projectDAO.insert(proj);
								projectDAO.relationshipTodepartment(proj.getProjectId(), idDepartment);
								System.out.println("Projeto cadastrado com sucesso!");								
								break;
							}
							case 3:{
								boolean flagCadFunc = false;
								int opt;
								while(!flagCadFunc) {
									System.out.println("| 1 | Secretário.");
									System.out.println("| 2 | Pesquisador.");
									System.out.println("| 3 | Funcionário de Limpeza.");
									System.out.println("| 4 | Gerente.");
									System.out.println("| 0 | sair.");
									
									opt = input_int.nextInt();
									input_int.nextLine();
									switch(opt) {
										case 1:{
											String degreeOfSchooling;
											long idDepartment = Main.getIdDepartment();
											if(Main.getPersonalData()){
												System.out.println("Digite o grau de escolaridade do Funcionário: ");
												degreeOfSchooling = input.nextLine();
												Main.getAddressData();											
												Address address = new Address(Main.street, Main.number, Main.cep, Main.neighborhood);
												addressDAO.insert(address);
												Employee secretary = new Secretary(Main.name, Main.sex, Main.birthday, Main.salary, degreeOfSchooling);
												secretaryDAO.insert((Secretary) secretary);
												secretaryDAO.relationshipToDepartment(secretary.getEmployeeId(), idDepartment);
												addressDAO.relationshipToEmployee(address.getAddressId(), secretary.getEmployeeId());
												System.out.println("Funcionário cadastrado com sucesso.");
											}											
											break;
										}
										case 2:{
											String occupationArea;
											long idDepartment = Main.getIdDepartment();
											Main.getPersonalData();
											System.out.println("Digite a area de atuação: ");
											occupationArea = input.nextLine();
											getAddressData();
											Address address = new Address(Main.street, Main.number, Main.cep, Main.neighborhood);
											addressDAO.insert(address);
											Employee researcher = new Researcher(Main.name, Main.sex, Main.birthday, Main.salary, occupationArea);
											researcherDAO.insert((Researcher) researcher);
											addressDAO.relationshipToEmployee(address.getAddressId(), researcher.getEmployeeId());
											researcherDAO.relationshipToDepartament(idDepartment, researcher.getEmployeeId());
											System.out.println("Funcionário cadastrado com sucesso.");											
											break;
										}
										case 3:{
											String workingDays;
											long idDepartment = Main.getIdDepartment();
											Main.getPersonalData();
											System.out.println("Digite a Jornada Trabalhada: ");
											workingDays = input.nextLine();
											Main.getAddressData();
											Address address = new Address(Main.street, Main.number, Main.cep, Main.neighborhood);
											addressDAO.insert(address);
											Employee clearEmployee = new ClearEmployee(Main.name, Main.sex, Main.birthday, Main.salary, workingDays);
											clearEmployeeDAO.insert((ClearEmployee) clearEmployee);
											clearEmployeeDAO.relationshipToDepartment(clearEmployee.getEmployeeId(), idDepartment);
											addressDAO.relationshipToEmployee(address.getAddressId(), clearEmployee.getEmployeeId());
											System.out.println("Funcionário cadastrado com sucesso.");											
											break;
										}
										case 4:{
											long idManager, idEmployee;
											System.out.println("Digite o ID do funcionário que vai ser gerente: ");
											idManager = input_long.nextLong();
											while(clearEmployeeDAO.findById(idManager).equals(null)) {
												System.out.println("Funcionário inválido, digite outro ID: ");
												idManager = input_long.nextLong();
											}
											System.out.println("Digite o ID do funcionário que será gerenciado: ");
											idEmployee = input_long.nextLong();
											while(clearEmployeeDAO.findById(idEmployee).equals(null)) {
												System.out.println("Funcionário inválido, digite outro ID: ");
												idEmployee = input_long.nextLong();
											}
											clearEmployeeDAO.relationshipToClearEmployee(idManager, idEmployee);
											System.out.println("Gerente adicionado com sucesso.");
											break;
										}
										default:{
											flagCadFunc = true;
											break;
										}
									}
								}
								break;
							}
							case 4:{
								long idEmployee, idProject;
								double amountOfHours;
								System.out.println("Digite o numero de horas semanais: ");
								amountOfHours = Main.input_double.nextDouble();
								System.out.println("Digite o ID do funcionário Pesquisador: ");
								idEmployee = Main.input_long.nextLong();
								while(researcherDAO.findById(idEmployee).equals(null)) {
									System.out.println("Funcionário Pesquisador inválido, digite outro ID: ");
									idEmployee = Main.input_long.nextLong();
								}
								System.out.println("Digite o ID do Projeto: ");
								idProject = Main.input_long.nextLong();
								while(projectDAO.findById(idProject).equals(null)) {
									System.out.println("Projeto inválido, digite outro ID: ");
									idProject = Main.input_long.nextLong();
								}
								WorkedHours workedHours = new WorkedHours(amountOfHours);
								workedHoursDAO.insert(workedHours);
								workedHoursDAO.relationshipToresearcher(idEmployee, workedHours.getId());
								workedHoursDAO.relationshioToProject(workedHours.getId(), idProject);
								System.out.println("Horas cadastradas com sucesso.");								
								break;
							}
							case 5:{
								String name, sex, birthday, degree_of_kinship;
								long idEmployee;
								System.out.println("Digite o nome do Dependente: ");
								name = Main.input.nextLine();
								System.out.println("Digite o sexo: ");
								sex = Main.input.nextLine();
								System.out.println("Digite a data de nascimento: ");
								birthday = Main.input.nextLine();
								System.out.println("Digite o grau de parentesco: ");
								degree_of_kinship = Main.input.nextLine();
								System.out.println("Digite o ID do funcionário: ");
								idEmployee = Main.input_long.nextLong();
								Dependent dependent = new Dependent(name, sex, birthday, degree_of_kinship);
								dependentDAO.insert(dependent);
								while(!dependentDAO.relationshipToEmployee(idEmployee, dependent.getDependentId())){
									System.out.println("Funcionário inválido, digite outro ID: ");
									idEmployee = Main.input_long.nextLong();
								}
								System.out.println("Dependente cadastrado com sucesso.");
								break;
							}
							case 6:{
								long idEmployee, idProject;
								System.out.println("Digite o ID do funcionário: ");
								idEmployee = input_long.nextLong();
								while(secretaryDAO.findById(idEmployee).equals(null) && researcherDAO.findById(idEmployee).equals(null) && clearEmployeeDAO.findById(idEmployee).equals(null)) {
									System.out.println("Funcionário inválido, digite outro ID: ");
									idEmployee = input_long.nextLong();
								}
								System.out.println("Digite o ID do projeto: ");
								idProject = input_long.nextLong();
								while(projectDAO.findById(idProject).equals(null)) {
									System.out.println("Projeto inválido, digite outro ID: ");
									idProject = input_long.nextLong();
								}
								if(secretaryDAO.findById(idEmployee).getName() != null) {
									if(secretaryDAO.relationshipToProject(idEmployee, idProject))
										System.out.println("Funcionário adicionado com sucesso.");
									else
										System.out.println("Error ao realizar o relacionamento!!!");
								}
								else if(researcherDAO.findById(idEmployee).getName() != null) {
									if(researcherDAO.relationshipToProject(idProject, idEmployee))
										System.out.println("Funcionário adicionado com sucesso.");
									else
										System.out.println("Error ao realizar o relacionamento!!!");
								}
								else if(clearEmployeeDAO.findById(idEmployee).getName() != null) {
									if(clearEmployeeDAO.relationshipToProject(idEmployee, idProject))
										System.out.println("Funcionário adicionado com sucesso.");
									else
										System.out.println("Error ao realizar o relacionamento!!!");
								}
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
						System.out.println("| 3 | Funcionário.");
						System.out.println("| 0 | Sair.");
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
							case 3:{
								boolean flag = false;
								int opt;
								while(!flag) {
									System.out.println("| 1 | Secratário.");
									System.out.println("| 2 | Pesquisador.");
									System.out.println("| 3 | Funcionário da Limpeza.");
									System.out.println("| 0 | Sair.");
									opt = input.nextInt();
									input.nextLine();
									switch(opt) {
										case 1:{
											String degreeOfSchooling;
											long idOldSecretary;
											System.out.println("Digite o ID do funcionário que deseja editar: ");
											idOldSecretary = input_long.nextLong();
											while(secretaryDAO.findById(idOldSecretary).equals(null)) {
												System.out.println("Funcionário inválido, digite outro ID: ");
												idOldSecretary = input_long.nextLong();
											}
											Main.getPersonalData();
											System.out.println("Digite o grau de escolaridade do Funcionário: ");
											degreeOfSchooling = input.nextLine();
											Main.getAddressData();											
											Address address = new Address(Main.street, Main.number, Main.cep, Main.neighborhood);
											Employee secretary = new Secretary(Main.name, Main.sex, Main.birthday, Main.salary, degreeOfSchooling);
											secretaryDAO.update(idOldSecretary, (Secretary) secretary);
											addressDAO.update(secretary.getEmployeeId(), address);
											System.out.println("Funcionário atualizado com sucesso.");
											break;
										}
										case 2:{
											String occupationArea;
											long idEmployee;
											System.out.println("Digite o ID do funcionário que deseja editar: ");
											idEmployee = input_long.nextLong();
											while(researcherDAO.findById(idEmployee).equals(null)) {
												System.out.println("Funcionário inválido, digite outro ID: ");
												idEmployee = input_long.nextLong();
											}
											Main.getPersonalData();
											System.out.println("Digite a area de atuação: ");
											occupationArea = input.nextLine();
											getAddressData();
											Address address = new Address(Main.street, Main.number, Main.cep, Main.neighborhood);
										    Employee researcher = new Researcher(Main.name, Main.sex, Main.birthday, Main.salary, occupationArea);
											researcherDAO.update(idEmployee, (Researcher) researcher);
											addressDAO.update(researcher.getEmployeeId(), address);
											System.out.println("Funcionário atualizado com sucesso.");
											break;
										}
										case 3:{
											String workingDays;
											long idEmployee;
											System.out.println("Digite o ID do funcionário que deseja editar: ");
											idEmployee = input_long.nextLong();
											while(clearEmployeeDAO.findById(idEmployee).equals(null)) {
												System.out.println("Funcionário inválido, digite outro ID: ");
												idEmployee = input_long.nextLong();
											}
											Main.getPersonalData();
											System.out.println("Digite a Jornada Trabalhada: ");
											workingDays = input.nextLine();
											Main.getAddressData();
											Address address = new Address(Main.street, Main.number, Main.cep, Main.neighborhood);
											Employee clearEmployee = new ClearEmployee(Main.name, Main.sex, Main.birthday, Main.salary, workingDays);
											clearEmployeeDAO.update(idEmployee, (ClearEmployee) clearEmployee);
											addressDAO.update(clearEmployee.getEmployeeId(), address);
											System.out.println("Funcionário atualizado com sucesso.");
											break;
										}
										default:{
											flag = true;
											break;
										}
									}
								}
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
						System.out.println("| 3 | Funcionários do tipo secretário.");
						System.out.println("| 4 | Funcionários do tipo pesquisador.");
						System.out.println("| 5 | Funcionários do tipo limpeza.");
						System.out.println("| 6 | Dependentes.");
						System.out.println("| 0 | Sair.");
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
							case 3:{
								List<Secretary> list = secretaryDAO.findAll();
								for(Secretary emp : list)
									System.out.println(emp);
								break;
							}
							case 4:{
								List<Researcher> list = researcherDAO.findAll();
								for(Researcher emp : list)
									System.out.println(emp);
								break;
							}
							case 5:{
								List<ClearEmployee> list = clearEmployeeDAO.findAll();
								for(ClearEmployee emp : list)
									System.out.println(emp);
								break;
							}
							case 6:{
								long idEmployee;
								System.out.println("Digite o ID do funcionário: ");
								idEmployee = input_long.nextLong();
								List<Dependent> list = null;
								if(secretaryDAO.findById(idEmployee).getName() != null) {
									list = secretaryDAO.findAllDependets(idEmployee);
								}
								else if(researcherDAO.findById(idEmployee).getName() != null) {
									list = researcherDAO.findAllDependets(idEmployee);
								}
								else if(clearEmployeeDAO.findById(idEmployee).getName() != null) {
									list = clearEmployeeDAO.findAllDependets(idEmployee);
								}
								else {
									System.out.println("Funcionário inválido!!!");
								}
								for(Dependent dep : list)
									System.out.println(dep);
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
						System.out.println("| 3 | Funcionário.");
						System.out.println("| 0 | Sair.");
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
							case 3:{
								long idEmployee;
								System.out.println("Digite o ID do Funcionário: ");
								idEmployee = input_long.nextLong();
								addressDAO.remove(idEmployee);
								dependentDAO.removeRelationshipToEmployee(idEmployee);
								if(secretaryDAO.findById(idEmployee).getName() != null) {
									if(secretaryDAO.remove(idEmployee))
										System.out.println("Funcionário excluido com sucesso.");
								}
								if(researcherDAO.findById(idEmployee).getName() != null) {
									if(workedHoursDAO.remove(idEmployee)){
										if(researcherDAO.remove(idEmployee))
											System.out.println("Funcionário excluido com sucesso.");
									}	 
								}
								if(clearEmployeeDAO.findById(idEmployee).getName() != null) {
									if(clearEmployeeDAO.remove(idEmployee))
										System.out.println("Funcionário excluido com sucesso.");
								}
								else
									System.out.println("Funcionário inválido!");
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
	
	public static boolean getPersonalData() {
		ValidDate valid = new ValidDate();
		System.out.println("Digite o nome do Funcionário: ");
		Main.name = input.nextLine();
		
		System.out.println("Digite o sexo do Funcionário: ");
		Main.sex = input.nextLine();
		
		System.out.println("Digite a data de nascimento do Funcionário: ");
		Main.birthday = input.nextLine();
		
		try {
			if(!valid.compareTo(birthday)) {
				System.out.println("Data fora dos limites!!!");
				return false;
			}
		}catch(Exception e) {
			System.out.println("A data não está no formato certo(dia/mês/ano)!!!");
			return false;
		}
		
		System.out.println("Digite o salário do Funcionário: ");
		Main.salary = input_double.nextDouble();
		return true;
	}
	
	public static void getAddressData() {
		System.out.println("Digite a rua: ");
		Main.street = input.nextLine();
		
		System.out.println("Digite o bairro: ");
		Main.neighborhood = input.nextLine();
		
		System.out.println("Digite o numero: ");
		Main.number = input_int.nextInt();
		
		System.out.println("Digite o CEP: ");
		Main.cep = input_int.nextInt();
	}
	
	public static long getIdDepartment() {
		long idDepartment;
		System.out.println("Digite o ID do departamento desse funcionário: ");
		idDepartment = input_long.nextLong();
		while(departmentDAO.findById(idDepartment).equals(null)) {
			System.out.println("Departamento inválido, digite outro ID: ");
			idDepartment = input_long.nextLong();
		}
		return idDepartment;
	}
}
