package med.voll.api.domain.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
@Component
public class ValidadorMedicoComOutraConsulta implements ValidadorAgendamentoDeConsultas {

	@Autowired
	private ConsultaRepository consultaRepository;
	
	public void validar(DadosAgendamentoConsulta dados) {
		
		boolean medicoPossuiOutraConsultaNoMesmoHorario = consultaRepository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(), dados.data());
		if(medicoPossuiOutraConsultaNoMesmoHorario) {
			throw new ValidacaoException("Medicos já possui consulta agendada pra esse mesmo horário");
		}
		
	}
	
}
