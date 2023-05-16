package med.voll.api.domain.consulta.validacoes.agendamento;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
@Component
public class ValidadorFuncionamentoHorarioClinica implements ValidadorAgendamentoDeConsultas {

	public void validar(DadosAgendamentoConsulta dados) {
		LocalDateTime dataconsulta = dados.data();
		
		boolean domingo = dataconsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
		boolean antesDaAberturaDaClinica = dataconsulta.getHour() < 7;
		boolean depoisDoEncerramentoDaClinica = dataconsulta.getHour() > 19;
		
		if(domingo || antesDaAberturaDaClinica || depoisDoEncerramentoDaClinica) {
			throw new ValidacaoException("Consulta fora do horário da clínica");
		}
	}
}
