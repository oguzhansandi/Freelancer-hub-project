'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;
var receiver = null;

function connect(event) {
    username = document.querySelector('#name').value.trim();
    var token = document.querySelector('#token').value.trim();
    receiver = document.querySelector('#receiver').value.trim(); // Birebir mesaj gönderilecek kişi

    if (username && token && receiver) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws?token=' + token);
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    } else {
        alert("Kullanıcı adı, token ve alıcı ismi gerekli.");
    }

    event.preventDefault();
}

function onConnected() {
    // Kendi mesaj kuyruğuna abone ol
    stompClient.subscribe("/user/queue/messages", onMessageReceived);

    connectingElement.classList.add('hidden');
}

function onError(error) {
    connectingElement.textContent = 'WebSocket bağlantısı kurulamadı. Sayfayı yenileyin.';
    connectingElement.style.color = 'red';
}

function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            receiver: receiver,
            content: messageContent
        };

        stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
        messageInput.value = '';

        // Mesajı ekranda göster (gönderen sensin)
        renderMessage(chatMessage, true);
    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    renderMessage(message, false); // Gelen mesaj
}

function renderMessage(message, isOwnMessage) {
    var messageElement = document.createElement('li');
    messageElement.classList.add('chat-message');
    if (isOwnMessage) {
        messageElement.classList.add('own-message');
    }

    var usernameElement = document.createElement('span');
    var usernameText = document.createTextNode(message.sender);
    usernameElement.appendChild(usernameText);

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(usernameElement);
    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

async function fetchUsers() {
    const response = await fetch("/rest/api/user/list");
    const users = await response.json();

    const userList = document.getElementById("userList");
    userList.innerHTML = ''; // temizle

    users.forEach(user => {
        const li = document.createElement('li');
        li.textContent = user;
        li.onclick = () => selectUser(user); // tıklanınca aktif kişi
        userList.appendChild(li);
    });
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)
