package com.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FaturamentoMensal {

    public static void main(String[] args) {
        try {
            // Carregar dados do JSON
            ObjectMapper objectMapper = new ObjectMapper();
            List<Faturamento> faturamentos = objectMapper.readValue(new File("src/main/resources/faturamento.json"),
                    new TypeReference<List<Faturamento>>() {});

            // Filtrar dias com faturamento
            List<Double> valores = faturamentos.stream()
                    .map(Faturamento::getValor)
                    .filter(valor -> valor > 0)
                    .collect(Collectors.toList());

            // Verificar se há dados para processamento
            if (valores.isEmpty()) {
                System.out.println("Não há dados de faturamento para análise.");
                return;
            }

            // Calcular menor e maior valor de faturamento
            double menorFaturamento = valores.stream().min(Double::compare).orElse(0.0);
            double maiorFaturamento = valores.stream().max(Double::compare).orElse(0.0);

            // Calcular média mensal
            double mediaMensal = valores.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

            // Contar dias com faturamento superior à média mensal
            long diasAcimaMedia = valores.stream().filter(valor -> valor > mediaMensal).count();

            // Exibir resultados
            System.out.println("Menor valor de faturamento: " + menorFaturamento);
            System.out.println("Maior valor de faturamento: " + maiorFaturamento);
            System.out.println("Número de dias com faturamento acima da média mensal: " + diasAcimaMedia);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}