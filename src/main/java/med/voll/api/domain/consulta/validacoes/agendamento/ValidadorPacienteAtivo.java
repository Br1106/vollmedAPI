package med.voll.api.domain.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsultas {
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	public void validar (DadosAgendamentoConsulta dados) {
		
		if(dados.idPaciente()== null) {
			return;
		}
		
		boolean pacienteEstaAtivo = pacienteRepository.findAtivoByID(dados.idPaciente());
		if(!pacienteEstaAtivo){
			throw new ValidacaoException("Consultas não podem ser marcadas com um paciente excluído");
		}	
	}
}
