package com.healthIo.health.services;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ServerQuestionService {
    
    private static final List<String> PERGUNTAS_PADRAO = List.of(
        "Olá, como posso te ajudar?",
        "Entendi, me diga seu nome completo por favor",
        "Qual é o seu CPF",
        "Qual é o seu RG",
        "Me fale seu endereço completo com CEP",
        "A unidade de atendimento mais próxima para você é na UPA da Link Hackathon",
        "O endereço é Avenida Brigadeiro Luiz Antônio, 5083",
        "Agora está com pouca gente la! O plantonista se chama Douglas O. Luciano"
    );

}
