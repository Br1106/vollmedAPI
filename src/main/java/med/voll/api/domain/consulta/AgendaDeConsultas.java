package med.voll.api.domain.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsultas;
import med.voll.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;

@Service
public class AgendaDeConsultas {

	@Autowired
	private ConsultaRepository consultaRepository;
	@Autowired
	private PacienteRepository pacienteRepository;
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private List<ValidadorAgendamentoDeConsultas> validadores;
	
	@Autowired
	private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;
	
	public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
		
		if(!pacienteRepository.existsById(dados.idPaciente())) {
			throw new ValidacaoException("O id do paciente informado não existe!");
		}
		
		if(dados.idMedico() != null &&  !medicoRepository.existsById(dados.idMedico())) {
			throw new ValidacaoException("O id do medico informado não existe!");
		}
		
		validadores.forEach(v -> v.validar(dados));
		
		Paciente paciente = pacienteRepository.getReferenceById(dados.idPaciente());
		Medico medico = escolherMedicos(dados);
		if(medico == null) {
			throw new ValidacaoException("Não existe médico disponível nessa data");
		}
		
		Consulta consulta = new Consulta(null,medico,paciente,dados.data(),null);
		consultaRepository.save(consulta);
		
		return new DadosDetalhamentoConsulta(consulta);
	}

	public void cancelar(@Valid DadosCancelamentoConsulta dados) {

		if(!consultaRepository.existsById(dados.idConsulta())) {
			throw new ValidacaoException("Não existem consultas com esse id!");
		}
		if(consultaRepository.getReferenceById(dados.idConsulta()).getMotivoCancelamento() != null) {
			throw new ValidacaoException("A consulta desse Id já foi cancelada por algum motivo");
		}
		validadoresCancelamento.forEach(v -> v.cancelar(dados));
		
		Consulta consulta = consultaRepository.getReferenceById(dados.idConsulta());
		consulta.cancelar(dados.motivo());
	}
	
	private Medico escolherMedicos(DadosAgendamentoConsulta dados) {
		if(dados.idMedico() != null) {
			return medicoRepository.getReferenceById(dados.idMedico());
		}
		if(dados.especialidade() == null) {
			throw new ValidacaoException("Quando o id do médico não for informado, é necessário a especificação da Especialidade");
		}
		return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
	}




}
