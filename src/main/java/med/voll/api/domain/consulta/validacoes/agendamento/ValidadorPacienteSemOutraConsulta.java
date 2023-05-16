package med.voll.api.domain.consulta.validacoes.agendamento;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

@Component
public class ValidadorPacienteSemOutraConsulta implements ValidadorAgendamentoDeConsultas {

	@Autowired
	private ConsultaRepository consultaRepository;
	
	public void validar(DadosAgendamentoConsulta dados) {
		LocalDateTime primeiroHorario = dados.data().withHour(7);
		LocalDateTime ultimoHorario = dados.data().withHour(19);
		boolean pacientePossuiOutraConsultaNoDia = consultaRepository.existsByPacienteIdAndDataBetween(dados.idPaciente(),primeiroHorario,ultimoHorario);
		if(pacientePossuiOutraConsultaNoDia) {
			throw new ValidacaoException("Paciente já possui consulta agendada nesse dia");
		}
	
	}
	
}
