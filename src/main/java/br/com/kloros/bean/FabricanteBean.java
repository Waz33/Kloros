package br.com.kloros.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.kloros.dao.FabricanteDAO;
import br.com.kloros.domain.Fabricante;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FabricanteBean implements Serializable {
	private Fabricante fabricante;
	private List<Fabricante> fabricantes;

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	@PostConstruct
	public void listar() {
		try {
			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar listar os estados");
			e.printStackTrace();
		}
	}

	public void novo() {
		fabricante = new Fabricante();
	}

	public void excluir(ActionEvent evento) {

		try {
			fabricante = (Fabricante) evento.getComponent().getAttributes().get("fabricanteSelecionado");
			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricanteDAO.excluir(fabricante);

			fabricantes = fabricanteDAO.listar(); // Atualizar listagem

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro em excluir Fabricante: " + fabricante.getDescricao());
			erro.printStackTrace();
		}

		Messages.addGlobalInfo("Nome: " + fabricante.getDescricao());

	}

	public void editar(ActionEvent evento) {

		fabricante = (Fabricante) evento.getComponent().getAttributes().get("fabricanteSelecionado");

		// Messages.addGlobalInfo("Nome: " + estado.getNome() + " Sigla: " +
		// estado.getSigla());
	}

	public void salvar() {
		// String texto = "JSF - ManagedBean";
		// System.out.println(texto);
		// FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR,
		// texto, texto);
		//
		// FacesContext contexto = FacesContext.getCurrentInstance();
		//
		// contexto.addMessage(null, mensagem);

		// Using OminiFaces
		// Messages.addGlobalInfo("Nome:" + estado.getNome() + " Sigla:" +
		// estado.getSigla());

		try {
			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricanteDAO.merge(fabricante);

			novo(); // para criar um novo objeto e limpar o formulario

			fabricantes = fabricanteDAO.listar();

			Messages.addGlobalInfo("Fabricante criado com sucesso");
		} catch (RuntimeException e) {
			Messages.addGlobalError("RunTimeExcepppppp Fabricante nao criado ");
			e.printStackTrace();
		}
	}

}
