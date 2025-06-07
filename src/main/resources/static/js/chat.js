let chatStep = 0;
  const userData = {};

  function sendMessage() {
    const userInput = document.getElementById("user-input").value.trim();
    const chatBox = document.getElementById("chat-box");

    if (userInput === "") return;

    // Mensagem do usuário
    const userMessage = document.createElement("div");
    userMessage.classList.add("message", "sent");
    userMessage.textContent = userInput;
    chatBox.appendChild(userMessage);

    // Resposta do bot com base na etapa
    let botResponse = "";

    switch (chatStep) {
      case 0:
        botResponse = "Me diga quais são seus sintomas.";
        chatStep++;
        break;
      case 1:
        userData.sintomas = userInput;
        botResponse = "Me diga seu nome completo.";
        chatStep++;
        break;
      case 2:
        userData.nome = userInput;
        botResponse = `Obrigado, ${userInput}. Qual é o número da sua carteirinha do SUS?`;
        chatStep++;
        break;
      case 3:
        userData.carteirinha = userInput;
        botResponse = "Você tem plano de saúde? (sim/não)";
        chatStep++;
        break;
      case 4:
        userData.planoSaude = userInput;
        botResponse = "Obrigado. Estamos analisando suas informações para indicar a melhor unidade de atendimento.";
        chatStep++;
        break;
      default:
        botResponse = "Deseja reiniciar o atendimento?";
        break;
    }

    // Resposta do bot
    const botMessage = document.createElement("div");
    botMessage.classList.add("message", "received");
    botMessage.textContent = botResponse;
    chatBox.appendChild(botMessage);

    document.getElementById("user-input").value = "";
    chatBox.scrollTop = chatBox.scrollHeight;
  }