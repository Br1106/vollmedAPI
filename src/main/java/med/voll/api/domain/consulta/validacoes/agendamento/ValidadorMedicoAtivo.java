package med.voll.api.domain.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;
@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsultas {
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	public void validar (DadosAgendamentoConsulta dados) {
		
		if(dados.idMedico()== null) {
			return;
		}
		
		boolean medicoEstaAtivo = medicoRepository.findAtivoByID(dados.idMedico());
		if(!medicoEstaAtivo){
			throw new ValidacaoException("Consultas precisam ser marcadas com um médico que está ativo");
		}	
	}
}
