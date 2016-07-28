package br.com.kloros.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.kloros.dao.EstadoDAO;
import br.com.kloros.domain.Estado;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EstadoBean implements Serializable {
	private Estado estado;
	private List<Estado> estados;

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@PostConstruct
	public void listar() {
		try {
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar listar os estados");
			e.printStackTrace();
		}
	}

	public void novo() {
		estado = new Estado();
	}

	public void excluir(ActionEvent evento) {

		try {
			estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
			EstadoDAO estadoDAO = new EstadoDAO();
			estadoDAO.excluir(estado);

			estados = estadoDAO.listar(); // Atualizar listagem

		} catch (RuntimeException erro) {
			Messages.addGlobalError(
					"Ocorreu um erro em excluir Estado: " + estado.getNome() + " Sigla: " + estado.getSigla());
			erro.printStackTrace();
		}

		Messages.addGlobalInfo("Nome: " + estado.getNome() + " Sigla: " + estado.getSigla());

	}

	public void editar(ActionEvent evento) {

		
		estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");

		//Messages.addGlobalInfo("Nome: " + estado.getNome() + " Sigla: " + estado.getSigla());
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
			EstadoDAO estadoDAO = new EstadoDAO();
			estadoDAO.merge(estado);

			novo(); // para criar um novo objeto e limpar o formulario

			estados = estadoDAO.listar();

			Messages.addGlobalInfo("Estado criado com sucesso");
		} catch (RuntimeException e) {
			Messages.addGlobalError("RunTimeExceppppppEstado nao criado ");
			e.printStackTrace();
		}
	}

}
