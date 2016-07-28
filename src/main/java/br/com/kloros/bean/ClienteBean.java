package br.com.kloros.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.kloros.dao.ClienteDAO;
import br.com.kloros.dao.PessoaDAO;
import br.com.kloros.domain.Cliente;
import br.com.kloros.domain.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable{
	private Cliente cliente;
	private List<Pessoa> pessoas;
	private List<Cliente> clientes;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	@PostConstruct
	public void listar() {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listar();
		} catch (RuntimeException e) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar listar os clientes");
			e.printStackTrace();
		}
	}

	public void novo() {
		try {
			cliente = new Cliente();

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("nome");
		
		} catch (RuntimeException e) {
			Messages.addGlobalError("Errro ao criar um novo cliente");
			e.printStackTrace();
		}
	}
	
	public void salvar(){
		try{
			
			//Abre a sessao com BD e chama metodo merge do DAO
			ClienteDAO clienteDAO = new ClienteDAO();
			clienteDAO.merge(cliente);
			
			//Cria novo objeto
			cliente = new Cliente();
	
			//Carrega a listagem de clientes
			clientes = clienteDAO.listar("dataCadastro");
			
			//Recarrega combo de Pessoas
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("nome");
			
			
		}catch(RuntimeException e){
			Messages.addGlobalError("Erro ao salvar Cliente!!");
			e.printStackTrace();
		}
	}
}