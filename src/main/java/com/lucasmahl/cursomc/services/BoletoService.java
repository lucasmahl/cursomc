package com.lucasmahl.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.lucasmahl.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	//acrescenta data de vencimento no pagamento
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7); //acrescenta 7 dias
		
		pagto.setDataVencimento(cal.getTime());
	}
}
