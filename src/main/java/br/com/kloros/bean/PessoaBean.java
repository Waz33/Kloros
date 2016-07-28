package br.com.kloros.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.kloros.dao.CidadeDAO;
import br.com.kloros.dao.EstadoDAO;
import br.com.kloros.dao.PessoaDAO;
import br.com.kloros.domain.Cidade;
import br.com.kloros.domain.Estado;
import br.com.kloros.domain.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class PessoaBean implements Serializable {
	private Pessoa pessoa;
	private List<Pessoa> pessoas;
	private Estado estado; // variavel temporario para segurar o estado
							// selecionado e filtrar as cidade
	private List<Estado> estados;
	private List<Cidade> cidades;

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	@PostConstruct
	public void listar() {
		try {
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar listar as pessoas");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			pessoa = new Pessoa();
			estado = new Estado();	

			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar(); //Carrega Estados para depois filtrar as cidades

			cidades = new ArrayList<>();//Inicia Cidades Vazia

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao gerar uma nova fabricante");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {


			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoaDAO.merge(pessoa);


			pessoas = pessoaDAO.listar("nome");

			pessoa = new Pessoa();
			estado = new Estado();	

			Messages.addGlobalInfo("Pessoa salva com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar um novo pessoa");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {

		try {
			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionado");

			estado = pessoa.getCidade().getEstado();

			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar("nome");

			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.buscarPorEstado(estado.getCodigo());

			Messages.addFlashGlobalInfo("Pessoa salva com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao selecionar uma Cidade");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionado");
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoaDAO.excluir(pessoa);

			pessoas = pessoaDAO.listar(); // Atualizar listagem

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro em excluir : " + pessoa.getNome());
			erro.printStackTrace();
		}

		Messages.addGlobalInfo("Excluido: " + pessoa.getNome());

	}

	public void popular() {
		if (estado != null) {
			System.out.println(estado.getCodigo() + estado.getNome() + estado.getSigla());

			try {
				 CidadeDAO cidadeDAO = new CidadeDAO();
				 cidades = cidadeDAO.buscarPorEstado(estado.getCodigo());
				System.out.println("total: "+cidades.size());
			} catch (RuntimeException e) {
				System.out.println("Erro ao filtra as cidades por estado");
				e.printStackTrace();
			}
		} else {
			cidades = new ArrayList<>();
		}
	}
}