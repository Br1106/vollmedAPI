package med.voll.api.domain.consulta.validacoes.cancelamento;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;

@Component("ValidadorDorarioAntecedenciaCancelamento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoDeConsulta{

	@Autowired
	private ConsultaRepository repository;
	
	@Override
	public void cancelar(DadosCancelamentoConsulta dados) {
		var consulta = repository.getReferenceById(dados.idConsulta());
		var horarioAtual = LocalDateTime.now();
		var diferencaEmHoras = Duration.between(horarioAtual, consulta.getData()).toHours();
		
		if(diferencaEmHoras < 24) {
            throw new ValidacaoException("Consulta somente pode ser cancelada com antecedência mínima de 24h!");
		}
	}

}
