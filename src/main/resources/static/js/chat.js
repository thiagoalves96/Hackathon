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

  // Inicia a busca pelo mapa após a última etapa
  if (chatStep === 5) {
    getUserLocation(); // Obtém a localização do usuário
  }
}

function getUserLocation() {
  // Checa se o navegador suporta geolocalização
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(
      function (position) {
        const lat = position.coords.latitude;
        const lng = position.coords.longitude;
        console.log(`Latitude: ${lat}, Longitude: ${lng}`); // Verifique no console se a localização foi obtida

        // Chama a função para buscar as unidades de saúde
        findNearbyUnits(lat, lng);
      },
      function () {
        alert("Não foi possível obter sua localização.");
      }
    );
  } else {
    alert("Geolocalização não é suportada por este navegador.");
  }
}

function findNearbyUnits(lat, lng) {
  // Inicializa o mapa usando o Leaflet
  const map = L.map("map").setView([lat, lng], 14); // Centraliza o mapa na posição do usuário
  console.log("Mapa inicializado com sucesso");

  // Adiciona a camada de mapa com OpenStreetMap
  L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    attribution: "© OpenStreetMap contributors",
  }).addTo(map);

  // Adiciona marcador para a localização do usuário
  L.marker([lat, lng]).addTo(map).bindPopup("Sua Localização").openPopup();

  // Refine a busca para hospitais próximos ao usuário dentro de um raio de 5 km
  const requestUrl = `https://nominatim.openstreetmap.org/search?format=json&lat=${lat}&lon=${lng}&radius=5000&addressdetails=1&q=hospital&limit=10`;

  fetch(requestUrl)
    .then((response) => response.json())
    .then((data) => {
      console.log("Unidades de saúde encontradas:", data); // Verifique os dados retornados pela API
      if (data.length > 0) {
        data.forEach((place) => {
          // Adiciona marcador para cada unidade encontrada
          L.marker([place.lat, place.lon]).addTo(map).bindPopup(place.display_name).openPopup();
        });
      } else {
        alert("Nenhuma unidade encontrada.");
      }
    })
    .catch((error) => {
      console.error("Erro ao buscar unidades de saúde:", error);
      alert("Não foi possível buscar unidades de saúde.");
    });
}
